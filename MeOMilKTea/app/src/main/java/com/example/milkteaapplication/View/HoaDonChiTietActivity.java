package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.milkteaapplication.Adapter.DaChonAdapter;
import com.example.milkteaapplication.Adapter.HoaDonChiTietAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.DatMonDAO;
import com.example.milkteaapplication.DAO.HoaDonDao;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.HoaDon;
import com.example.milkteaapplication.Model.HoaDonChiTiet;
import com.example.milkteaapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HoaDonChiTietActivity extends AppCompatActivity {
    Toolbar toolbar;
    String getMaHoaDon;
    RecyclerView recyclerView_da_chon;
    HoaDonChiTietAdapter adapter;
    List<HoaDonChiTiet> hoaDonChiTiets = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hóa Đơn Chi Tiết");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intenthd = getIntent();

        getMaHoaDon = intenthd.getExtras().getString("maHoaDon");

        Common.MAHOADON = getMaHoaDon;


        recyclerView_da_chon = findViewById(R.id.recycler_da_chon);
        RecyclerView.LayoutManager layoutManager;
        recyclerView_da_chon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(HoaDonChiTietActivity.this);
        recyclerView_da_chon.setLayoutManager(layoutManager);
        //get list mon da chon theo maHoaDon
        FirebaseDatabase.getInstance().getReference("HoaDon").child(Common.MAHOADON).child("datMonList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               hoaDonChiTiets.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDonChiTiet hd = data.getValue(HoaDonChiTiet.class);
                    hoaDonChiTiets.add(hd);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // gan adapter

        adapter = new HoaDonChiTietAdapter(getApplicationContext(), hoaDonChiTiets);
        recyclerView_da_chon.setAdapter(adapter);
        adapter.notifyItemInserted(hoaDonChiTiets.size());
        adapter.notifyDataSetChanged();

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

}
