package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.HoaDon;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.View.BanAnActivity;
import com.example.milkteaapplication.View.HoaDonActivity;
import com.example.milkteaapplication.View.HoaDonChiTietActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HoaDonDao {

    NonUI nonUI;
    private DatabaseReference reference;
    Context context;
    String billId;

    String detailId;

    List<HoaDon> mBill;


    public HoaDonDao(Context context) {
        this.reference = FirebaseDatabase.getInstance().getReference("HoaDon");
        this.context = context;
        this.nonUI = new NonUI(context);

    }


    //Read all hoa don to recyclerview
    public List<HoaDon> getAllHoaDon() {
        mBill = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mBill.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDon hoaDon = data.getValue(HoaDon.class);
                    mBill.add(hoaDon);
                }

                //Update listview
                ((HoaDonActivity) context).capnhatLV();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        reference.addValueEventListener(listener);


        return mBill;
    }


    public List<HoaDon> getTongTienTheoNgay(final String ngayHoaDon, final TextView tvTongNgay) {

        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDon hoaDon = data.getValue(HoaDon.class);
                    String ngay = hoaDon.getDate();
                    if (ngay.equals(ngayHoaDon)) {
                        Query query = FirebaseDatabase.getInstance().getReference("HoaDon").orderByChild("date").equalTo(ngayHoaDon);
                        query.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                long tongNgay = 0;
                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                    HoaDon hd = dataSnapshot1.getValue(HoaDon.class);
                                    tongNgay = tongNgay + hd.getThanhTien();

                                    NumberFormat numberFormat = new DecimalFormat("#,###,###");
                                    String ftongNgay = numberFormat.format(tongNgay);
                                    tvTongNgay.setText("Tổng tiền ngày: "+ftongNgay + " VNĐ");
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        tvTongNgay.setText("Ngày này chưa có hóa đơn");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        reference.addValueEventListener(listener);


        return mBill;
    }

    public List<HoaDon> getTongTienTheoThang(final String ngayHoaDon, final TextView tvTongThang) {
        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDon hoaDon = data.getValue(HoaDon.class);

                    Query query = FirebaseDatabase.getInstance().getReference("HoaDon").orderByChild("date");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long tongThang = 0;
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                HoaDon hd = dataSnapshot1.getValue(HoaDon.class);
                                if (getMonth(hd.getDate(), ngayHoaDon) != 0) {
                                    tongThang = tongThang + hd.getThanhTien();

                                    NumberFormat numberFormat = new DecimalFormat("#,###,###");
                                    String ftongNgay = numberFormat.format(tongThang);
                                    tvTongThang.setText("Tổng tiền tháng: " + ftongNgay + " VNĐ");

                                }
                            }
                            if (tongThang == 0) {
                                tvTongThang.setText("Tháng này chưa có hóa đơn");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        reference.addValueEventListener(listener);


        return mBill;
    }

    public List<HoaDon> getTongTienTheoNam(final String ngayHoaDon, final TextView tvTongNam) {


        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDon hoaDon = data.getValue(HoaDon.class);

                    Query query = FirebaseDatabase.getInstance().getReference("HoaDon").orderByChild("date");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            long tongNam = 0;
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                HoaDon hd = dataSnapshot1.getValue(HoaDon.class);
                                if (getYear(hd.getDate(), ngayHoaDon) != 0) {
                                    tongNam = tongNam + hd.getThanhTien();

                                    NumberFormat numberFormat = new DecimalFormat("#,###,###");
                                    String ftongNgay = numberFormat.format(tongNam);
                                    tvTongNam.setText("Tổng tiền năm: " + ftongNgay + " VNĐ");
                                }
                            }
                            if (tongNam == 0) {
                                tvTongNam.setText("Năm này không có hóa đơn");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        reference.addValueEventListener(listener);


        return mBill;
    }




    public int getMonth(String day1, String day2) {
        int month = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date date1 = format.parse(day1);
            Date date2 = format.parse(day2);
            if (date1.getMonth() == date2.getMonth() && date1.getYear() == date2.getYear()) {
                month = date1.getMonth() + 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return month;
    }

    public int getYear(String day1, String day2) {
        int year = 0;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date date1 = format.parse(day1);
            Date date2 = format.parse(day2);
            if (date1.getYear()==date2.getYear()){
                year = date1.getYear() +1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return year;
    }


    public void delete(final HoaDon item) {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maHoaDon").getValue(String.class).equalsIgnoreCase(item.getMaHoaDon())) {
                        billId = data.getKey();

                        Log.d("getKey", "onCreate: key :" + billId);

                        reference.child(billId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Xóa hóa đơn thành công");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("Xóa item thất bại");
                                        Log.d("delete", "delete That bai");
                                    }
                                });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
