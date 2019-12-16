package com.example.milkteaapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.milkteaapplication.Adapter.DanhMucSanPhamAdapter;
import com.example.milkteaapplication.Adapter.HoaDonAdapter;
import com.example.milkteaapplication.Adapter.SanPhamAdapter;
import com.example.milkteaapplication.DAO.DanhMucDao;
import com.example.milkteaapplication.DAO.HoaDonDao;
import com.example.milkteaapplication.DAO.SanPhamDAO;
import com.example.milkteaapplication.Model.HoaDon;
import com.example.milkteaapplication.R;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static com.example.milkteaapplication.R.id.list_view_bill;
import static com.example.milkteaapplication.R.id.recycler_san_pham;

public class HoaDonActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    List<HoaDon> mBill;
    HoaDonDao hoaDonDao;
    HoaDonAdapter hoaDonAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        recyclerView = findViewById(list_view_bill);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hóa đơn");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        recyclerView.setHasFixedSize(true);
        mBill = new ArrayList<>();

        hoaDonDao = new HoaDonDao(this);
        mBill = hoaDonDao.getAllHoaDon();
        hoaDonAdapter = new HoaDonAdapter(this, mBill);
        recyclerView.setAdapter(hoaDonAdapter);
        //Gridlayout with 3 cols
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        capnhatLV();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    ////////////////////click card view//////////////
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((HoaDonAdapter) recyclerView.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_xoa_hoa_don:
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(HoaDonActivity.this);
                final int finalPosition = position;
                builder.setMessage("Bạn muốn xóa hóa đơn này ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                HoaDonDao hoaDonDao1 = new HoaDonDao(HoaDonActivity.this);
                                hoaDonDao1.delete(mBill.get(finalPosition));
                                capnhatLV();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    public void capnhatLV() {
        hoaDonAdapter.notifyItemInserted(mBill.size());

        hoaDonAdapter.notifyDataSetChanged();
    }
}
