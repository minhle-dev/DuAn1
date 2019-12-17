package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milkteaapplication.Adapter.DaChonAdapter;
import com.example.milkteaapplication.Adapter.PhaCheAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.DatMonDAO;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.HoaDonChiTiet;
import com.example.milkteaapplication.Model.ThanhTien;
import com.example.milkteaapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PhaCheActivity extends AppCompatActivity {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public PhaCheAdapter adapter;

    RecyclerView recyclerView_da_chon;
    RecyclerView.LayoutManager layoutManager;
    String getNumber, getIdBan;
    List<DatMon> datMonList = new ArrayList<DatMon>();
    Button btnHoanThanh;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pha_che);

        Intent intent = getIntent();
        getNumber = intent.getExtras().getString("tenBan1");
        getIdBan = intent.getExtras().getString("maBan1");
        Common.BEPTABLE = getNumber;
        Common.BEPTABLEID = getIdBan;
        ///
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nhà Bếp");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ///
        recyclerView_da_chon = findViewById(R.id.recycler_da_chon);
        btnHoanThanh = findViewById(R.id.btn_hoan_thanh);
        recyclerView_da_chon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView_da_chon.setLayoutManager(layoutManager);

        //Chuan bi data
        DatMonDAO datMonDAO = new DatMonDAO(this);
        datMonList = datMonDAO.getDatMonsPhaChe();
        // gan adapter
        adapter = new PhaCheAdapter(getApplicationContext(), datMonList);
        recyclerView_da_chon.setAdapter(adapter);

        CapNhatRV();

        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("BanAn").child(Common.BEPTABLEID).child("hoanThanh").setValue("Đã pha chế");
                Toast.makeText(PhaCheActivity.this, "Đã pha chế xong", Toast.LENGTH_SHORT).show();
            }
        });
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

    public void CapNhatRV() {
        adapter.notifyItemInserted(datMonList.size());
        adapter.notifyDataSetChanged();
    }


}
