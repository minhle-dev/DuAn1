package com.example.milkteaapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.milkteaapplication.Adapter.PagerChonMonAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.Fragment.FragmentChonMon;
import com.example.milkteaapplication.View.Fragment.FragmentDaChon;
import com.google.android.material.tabs.TabLayout;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;

public class ChonMonActivity extends AppCompatActivity  {
    RecyclerView lvFood, lvOrdered;
    TextView tvNumber, tvIdNumber;
    public static String  getNumber, getIdBan;
    private ViewPager pager;
    private DachshundTabLayout tabLayout;
    Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon);
        Anhxa();


        Intent intent = getIntent();

        getNumber = intent.getExtras().getString("tenBan");
        getIdBan = intent.getExtras().getString("maBan");

        Common.TABLE = getNumber;
        Common.TABLEID = getIdBan;

        tvNumber.setText("Bàn số: "+getNumber);
        tvIdNumber.setText(""+getIdBan);
        addControl();


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa(){
        lvFood = findViewById(R.id.recycler_san_pham);
        lvOrdered = findViewById(R.id.recycler_them_mon);
        tvNumber = findViewById(R.id.tv_number);
        tvIdNumber = findViewById(R.id.tv_idnumber);
        btnHome = findViewById(R.id.btn_home);
    }

    //  add Fragment
    public void addControl() {
        Bundle choose = new Bundle();
        choose.putString("maBan", getIdBan);
        choose.putString("tenBan", getNumber);
        FragmentChonMon fragchoose = new FragmentChonMon();
        fragchoose.setArguments(choose);

        Bundle ordered = new Bundle();
        ordered.putString("maBan", getIdBan);
        ordered.putString("tenBan", getNumber);
        FragmentDaChon frgordered = new FragmentDaChon();
        frgordered.setArguments(ordered);

        pager =  findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        FragmentManager manager = getSupportFragmentManager();
        PagerChonMonAdapter adapter = new PagerChonMonAdapter(manager, ChonMonActivity.this);
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));

    }
    @Override
    public void onResume() {
        super.onResume();
    }


}
