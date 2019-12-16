package com.example.milkteaapplication.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.milkteaapplication.DAO.HoaDonDao;
import com.example.milkteaapplication.Model.HoaDon;
import com.example.milkteaapplication.Model.HoaDonChiTiet;
import com.example.milkteaapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvDoanhThu, tvChiPhi, tvNgayHoaDon, tvTongTienNgay;
    Button btndate, btnThongKeNgay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        anhxa();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thống Kê");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TongDoanhThu();
        ThongKeTienNhapHang();


        btndate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseDate();
            }
        });

        btnThongKeNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HoaDonDao hoaDonDao = new HoaDonDao(ThongKeActivity.this);
                hoaDonDao.getTongTienTheoNgay(tvNgayHoaDon.getText().toString(), tvTongTienNgay);
            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbar);
        tvDoanhThu = findViewById(R.id.tvDoanhThu);
        tvChiPhi = findViewById(R.id.tvChiPhi);
        tvNgayHoaDon = findViewById(R.id.txtdate);
        tvTongTienNgay = findViewById(R.id.tvTongTienNgay);
        btndate = findViewById(R.id.btndate);
        btnThongKeNgay = findViewById(R.id.btnThongKeNgay);

    }

    //thong ke doanh thu
    //thong ke chi phi
    private void TongDoanhThu() {
        DatabaseReference db_hd = FirebaseDatabase.getInstance().getReference().child("HoaDon");
        db_hd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long tongThu = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()
                ) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object doanhThu = map.get("thanhTien");
                    long pValue = Long.parseLong(((String.valueOf(doanhThu))));
                    tongThu += pValue;
                }
                tvDoanhThu.setText("  Tổng Tiền Thu:  " + NumberFormat.getNumberInstance(Locale.US).format(tongThu) + " VNĐ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //thong ke chi phi
    private void ThongKeTienNhapHang() {
        DatabaseReference db_hdNhap = FirebaseDatabase.getInstance().getReference().child("HoaDonNhapHang");
        db_hdNhap.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long tongChi = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()
                ) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object giaTienNhap = map.get("soTienNhap");
                    long pValue = Long.parseLong(((String.valueOf(giaTienNhap))));
                    tongChi += pValue;
                }
                tvChiPhi.setText("  Tổng Tiền Chi:  " + NumberFormat.getNumberInstance(Locale.US).format(tongChi) + " VNĐ");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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


    public void ChooseDate() {
        final Calendar calendar = Calendar.getInstance();
        //Date
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        int Month = calendar.get(Calendar.MONTH);
        int Year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(ThongKeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                tvNgayHoaDon.setText(sdf.format(calendar.getTime()));
            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }
}
