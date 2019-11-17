package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.milkteaapplication.View.Fragment.FragmentQuanLyHangHoa;
import com.example.milkteaapplication.View.Fragment.FragmentQuanLyTaiKhoan;
import com.example.milkteaapplication.R;
import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentQuanLyTaiKhoan()).commit();
            navigationView.setCheckedItem(R.id.nav_quan_ly_acc);
            toolbar.setTitle("Quản lý tài khoản");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_quan_ly_acc:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentQuanLyTaiKhoan()).commit();
                toolbar.setTitle("Quản lý tài khoản");
                break;
            case R.id.nav_quan_ly_hang:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FragmentQuanLyHangHoa()).commit();
                toolbar.setTitle("Quản lý hàng hóa");
                break;
            case R.id.nav_caidat:
                Toast.makeText(this,"Cài Đặt",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_thoat:
                finish();
                System.exit(0);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }}
}
