package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.View.Fragment.FragmentQuanLyHangHoa;

import com.example.milkteaapplication.Model.HoaDonNhapHang;
import com.example.milkteaapplication.Model.NonUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HoaDonNhapDAO {
    private DatabaseReference mDatabase;

    NonUI nonUI;
    Context context;
    FragmentQuanLyHangHoa fr;
    String key;


    public HoaDonNhapDAO(Context context, FragmentQuanLyHangHoa fr) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("HoaDonNhapHang");
        this.context = context;
        this.nonUI = new NonUI(context);
        this.fr = fr;

    }

    public List<HoaDonNhapHang> getAllHDNH() {

        final List<HoaDonNhapHang> list = new ArrayList<HoaDonNhapHang>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get HD object and use the values to update the UI
                list.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    HoaDonNhapHang item = data.getValue(HoaDonNhapHang.class);

                    list.add(item);
                }

                fr.capnhatLV();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                nonUI.toast("Không thể kết nối cơ sở dữ liệu!");
            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }

    public void insertHDNH(HoaDonNhapHang item) {

        key = mDatabase.push().getKey();

        mDatabase.child(key).setValue(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        nonUI.toast("Thêm thành công !");
                        Log.d("insert", "insert Thanh cong");


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                nonUI.toast("Thêm thất bại !");
                Log.d("insert", "insert That bai");
            }
        });


    }

    public void updateHDNH(final HoaDonNhapHang item) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maHoaDonNhap").getValue(String.class).equals(item.maHoaDonNhap)) {

                        key = data.getKey();

                        mDatabase.child(key).setValue(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Sửa thành công !!");
                                        Log.d("update", "update Thanh cong");

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("Sửa thất bại");
                                        Log.d("update", "update That bai");
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

    public void deleteHDNH(final HoaDonNhapHang item) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maHoaDonNhap").getValue(String.class).equals(item.maHoaDonNhap)) {
                        key = data.getKey();

                        Log.d("getKey", "onCreate: key :" + key);


                        mDatabase.child(key).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Xóa thành công !");
                                        Log.d("delete", "delete Thanh cong");


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("Xóa thất bại !");
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

    public void ThongKeTongTien(final HoaDonNhapHang item) {

        //thong ke tong tien nhap



    }


}
