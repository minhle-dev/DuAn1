package com.example.milkteaapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.milkteaapplication.Adapter.BanAdapter;
import com.example.milkteaapplication.Adapter.UsersAdapter;
import com.example.milkteaapplication.DAO.BanAnDao;
import com.example.milkteaapplication.DAO.UserDAO;
import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class DatMonActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText edtTenBan, edtTrangThai, edtTongTien;
    BanAnDao banAnDao;
    BanAdapter banAdapter;
    RecyclerView recyclerView_datBan;
    RecyclerView.LayoutManager layoutManager;
    List<BanAn> banAnList;
    Spinner spTrangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_mon);
        layoutManager = new LinearLayoutManager(DatMonActivity.this);
        recyclerView_datBan = findViewById(R.id.recycler_datMon);
        spTrangThai = findViewById(R.id.spTrangThai);
        recyclerView_datBan.setLayoutManager(new GridLayoutManager(DatMonActivity.this, 3));
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);





        //Chuan bi data

        banAnDao = new BanAnDao(DatMonActivity.this);

        banAnList = banAnDao.getAllBanAn();

        // gan adapter

        banAdapter = new BanAdapter(DatMonActivity.this, banAnList);

        // dua len listView

        recyclerView_datBan.setAdapter(banAdapter);
        banAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.them_ban, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.m_themBan:
                ThemBanAn();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((BanAdapter) recyclerView_datBan.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_tinh_tien:
                Toast.makeText(this, "Tính tiền", Toast.LENGTH_SHORT).show();
                break;
            case R.id.context_them_mon:
                Toast.makeText(this, "Thêm món", Toast.LENGTH_SHORT).show();
                break;
            case R.id.context_xoa_ban:
                BanAnDao banAnDao = new BanAnDao(DatMonActivity.this);
                banAnDao.deleteBan(banAnList.get(position));
                banAnList.remove(position);
                capnhatLV();
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void ThemBanAn() {
        final ProgressDialog mDialog = new ProgressDialog(DatMonActivity.this);
        mDialog.setMessage("Xin chờ...");
        mDialog.show();
        //
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(DatMonActivity.this);
        builder.setTitle("Thêm hóa đơn nhập hàng");
        builder.setMessage("Vui lòng nhập đủ thông tin!");

        //init view
        View itemView = LayoutInflater.from(DatMonActivity.this).inflate(R.layout.item_dialog_them_ban, null);
        edtTenBan = itemView.findViewById(R.id.edtTenBan);
        edtTongTien = itemView.findViewById(R.id.edtTongTien);
        spTrangThai = itemView.findViewById(R.id.spTrangThai);
        //set sp trang thai ban
        final List<String> listTrangThai= new ArrayList<>();

        listTrangThai.add("Bàn trống");
        listTrangThai.add("Có khách");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listTrangThai);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spTrangThai.setAdapter(adapter);

        //set
        builder.setView(itemView);
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
            }
        });
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
                String tenBan = edtTenBan.getText().toString();
                String trangThai = String.valueOf(spTrangThai.getSelectedItemPosition());
                String tongTien = edtTongTien.getText().toString();
                BanAn banAn = new BanAn(tenBan, trangThai, tongTien);
                banAnDao.insertBan(banAn);
                finish();
            }
        });
        builder.setView(itemView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void capnhatLV() {
        recyclerView_datBan.setAdapter(banAdapter);
        banAdapter.notifyDataSetChanged();

    }
}
