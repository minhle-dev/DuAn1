package com.example.milkteaapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.R;

import es.dmoral.toasty.Toasty;

public class HomeActivity extends AppCompatActivity {

    GridLayout mainGrid;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mainGrid = findViewById(R.id.mainGrid);

        //Set Event
        setSingleEvent(mainGrid);

    }


    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    role = Common.currentUser.getRole();
                    //0 = phục vự, 1= thu ngân, 2=pha chế
                    if (finalI == 0 && role.equals("admin")) {
                        Intent intentAdmin = new Intent(HomeActivity.this, AdminActivity.class);
                        startActivity(intentAdmin);
                    } else if (finalI == 1&& role.equals("admin") ||finalI == 1&& role.equals("0")||finalI == 1&& role.equals("1") ) {
                        Intent intentBanAn = new Intent(HomeActivity.this, BanAnActivity.class);
                        startActivity(intentBanAn);
                    } else if (finalI == 2 && role.equals("admin") || finalI == 2 && role.equals("0") || finalI == 2 && role.equals("1")) {
                        Intent intentHoaDon = new Intent(HomeActivity.this, HoaDonActivity.class);
                        startActivity(intentHoaDon);
                    } else if (finalI == 3 && role.equals("admin") || finalI == 3 && role.equals("1")) {
                        Intent intentThongKe = new Intent(HomeActivity.this, ThongKeActivity.class);
                        startActivity(intentThongKe);
                    } else if (finalI == 4 ) {
                        Intent intentMenu = new Intent(HomeActivity.this, MenuActivity.class);
                        startActivity(intentMenu);
                    } else if (finalI == 5 && role.equals("2") || finalI == 5 && role.equals("admin")) {
                        Intent intent = new Intent(HomeActivity.this, BanAnBepActivity.class);
                        startActivity(intent);
                    } else if (finalI == 6) {
                        finish();
                        System.exit(0);
                    } else {
                        Toasty.error(HomeActivity.this, "Bạn không có quyền truy cập!!", Toast.LENGTH_SHORT, true).show();
                    }

                }
            });
        }
    }
}
