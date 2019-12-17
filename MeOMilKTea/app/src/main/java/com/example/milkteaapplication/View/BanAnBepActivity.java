package com.example.milkteaapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.milkteaapplication.Adapter.BanAnAdapter;
import com.example.milkteaapplication.Adapter.BanAnBepAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.BanAnDao;
import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.R;

import java.util.List;
import java.util.Objects;

public class BanAnBepActivity extends AppCompatActivity {
    Toolbar toolbar;
    String getNumber, getIdBan;
    BanAnDao banAnDao;
    BanAnBepAdapter banAnAdapter;
    RecyclerView recyclerView_datBan;
    RecyclerView.LayoutManager layoutManager;
    List<BanAn> banAnList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ban_an_bep);

        layoutManager = new LinearLayoutManager(BanAnBepActivity.this);
        recyclerView_datBan = findViewById(R.id.recycler_datMon);
        recyclerView_datBan.setLayoutManager(new GridLayoutManager(BanAnBepActivity.this, 3));
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nhà Bếp");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Common.BEPTABLE = getNumber;
        Common.BEPTABLEID = getIdBan;


        //Chuan bi data

        banAnDao = new BanAnDao(BanAnBepActivity.this);

        banAnList = banAnDao.getAllBanAnBep();

        // gan adapter

        banAnAdapter = new BanAnBepAdapter(BanAnBepActivity.this, banAnList);

        // dua len listView

        recyclerView_datBan.setAdapter(banAnAdapter);
        banAnAdapter.notifyDataSetChanged();
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

    public void capnhatLV() {
        recyclerView_datBan.setAdapter(banAnAdapter);
        banAnAdapter.notifyDataSetChanged();

    }
}
