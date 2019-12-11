package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.milkteaapplication.Adapter.PagerChonMonAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.Fragment.FragmentChonMon;
import com.example.milkteaapplication.View.Fragment.FragmentDaChon;
import com.example.milkteaapplication.View.Fragment.FragmentDanhMuc;
import com.example.milkteaapplication.View.Fragment.FragmentSanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;

import java.util.Objects;

public class ChonMonActivity extends AppCompatActivity {
    RecyclerView lvFood, lvOrdered;
    TextView tvNumber, tvIdNumber, tvTotal;
    public static String  getNumber;
    long tongtien;
    private long price;
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
        Common.TABLE = getNumber;


        tvNumber.setText("Bàn số: "+getNumber);

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
        tvTotal = findViewById(R.id.tv_total);
        btnHome = findViewById(R.id.btn_home);
    }

    //  add Fragment
    public void addControl() {
        Bundle choose = new Bundle();

        choose.putString("tenBan", getNumber);
        FragmentChonMon fragchonmon = new FragmentChonMon();
        fragchonmon.setArguments(choose);

        Bundle ordered = new Bundle();
        ordered.putString("tenBan", getNumber);
        FragmentDaChon frgdachon = new FragmentDaChon();
        frgdachon.setArguments(ordered);

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
