package com.example.milkteaapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ListView;

import com.example.milkteaapplication.Adapter.ThemMonAdapter;
import com.example.milkteaapplication.DAO.SanPhamDAO;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;

import java.util.ArrayList;

public class ThemMonActivity extends AppCompatActivity {

    public ThemMonAdapter adapter;

    RecyclerView recyclerView_them_mon;
    RecyclerView.LayoutManager layoutManager;


    ArrayList<SanPham> sanPhams;

    SanPhamDAO sanPhamDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon);

        recyclerView_them_mon = findViewById(R.id.recycler_them_mon);
        recyclerView_them_mon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView_them_mon.setLayoutManager(layoutManager);

        //Chuan bi data

        sanPhamDAO = new SanPhamDAO(this);


        sanPhams=(ArrayList<SanPham>)  sanPhamDAO.getSanPham();

        // gan adapter

        //adapter = new ThemMonAdapter(this, sanPhams,this);

        // dua len listView

        recyclerView_them_mon.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();

        capnhatLV();
    }

    public void capnhatLV(){

        adapter.notifyDataSetChanged();
    }
}
