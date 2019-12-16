package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.milkteaapplication.View.Fragment.FragmentDanhMuc;
import com.example.milkteaapplication.View.Fragment.FragmentSanPham;
import com.example.milkteaapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menu");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener );

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,
                new FragmentSanPham()).commit();
    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.navbot_san_pham:
                            selectedFragment = new FragmentSanPham();
                            break;
                        case R.id.navBot_danh_muc:
                            selectedFragment= new FragmentDanhMuc();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container1,selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onResume() {
        super.onResume();
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
