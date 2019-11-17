package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.View.Fragment.FragmentDanhMuc;
import com.example.milkteaapplication.Model.DanhMucSanPham;
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

public class DanhMucDao {
    private DatabaseReference mDatabase;

    NonUI nonUI;
    Context context;
    String key;
    FragmentDanhMuc fr;


    public DanhMucDao(Context context) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("DanhMucSanPham");
        this.context = context;
        this.nonUI = new NonUI(context);


    }

    public DanhMucDao(Context context, FragmentDanhMuc fr) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("DanhMucSanPham");
        this.context = context;
        this.nonUI = new NonUI(context);
        this.fr = fr;

    }

    public List<DanhMucSanPham> getAllDanhMuc() {

        final List<DanhMucSanPham> list = new ArrayList<DanhMucSanPham>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get danh muc object and use the values to update the UI
                list.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DanhMucSanPham item = data.getValue(DanhMucSanPham.class);

                    list.add(item);
                }

                fr.capnhatLV();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

                nonUI.toast("Khong the ket noi database");
            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }

    public void insertDanhMucSP(DanhMucSanPham item) {


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
                nonUI.toast("insert That bai");
                Log.d("insert", "Thêm thất bại!");
            }
        });


    }

    public void updateDanhMuc(final DanhMucSanPham item) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maDanhMuc").getValue(String.class).equals(item.maDanhMuc)) {

                        key = data.getKey();


                        mDatabase.child(key).setValue(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Sủa thành công !");
                                        Log.d("update", "update Thanh cong");


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("update That bai");
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

    public void deleteDanhMuc(final DanhMucSanPham item) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maDanhMuc").getValue(String.class).equalsIgnoreCase(item.maDanhMuc)) {
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
                                        nonUI.toast("delete That bai");
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
