package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.Adapter.SpinnerDanhMucAdapter;
import com.example.milkteaapplication.Adapter.ViewPagerAdapter;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.View.Fragment.FragmentDanhMuc;
import com.example.milkteaapplication.Model.DanhMucSanPham;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.View.Fragment.FragmentSanPham;
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
    private DatabaseReference mDatabase, mDatabaseSanPham;
    NonUI nonUI;
    Context context;
    String key, idSP;
    FragmentDanhMuc fr;
    List<DanhMucSanPham> mDanhMuc;
    List<SanPham> mSamPham;

    public DanhMucDao(Context context) {
        this.mDatabaseSanPham = FirebaseDatabase.getInstance().getReference("SanPham");
        this.mDatabase = FirebaseDatabase.getInstance().getReference("DanhMucSanPham");
        this.context = context;
        this.nonUI = new NonUI(context);


    }

    public DanhMucDao(Context context, FragmentDanhMuc fr) {
        this.mDatabaseSanPham = FirebaseDatabase.getInstance().getReference("SanPham");
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

    public List<DanhMucSanPham> getDanhMuc() {

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

                //fr.capnhatLV();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

                nonUI.toast("Khong the ket noi database");
            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }


    public List<DanhMucSanPham> readAllCategoryToSpinner(final Spinner spinner) {
        mDanhMuc = new ArrayList<>();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Category object and use the values to update the UI
                mDanhMuc.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DanhMucSanPham danhMucSanPham = data.getValue(DanhMucSanPham.class);
                    mDanhMuc.add(danhMucSanPham);
                }
                SpinnerDanhMucAdapter spinnerAdapter = new SpinnerDanhMucAdapter(context, mDanhMuc);

                // Apply the adapter to the spinner
                spinner.setAdapter(spinnerAdapter);
                spinnerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mDatabase.addValueEventListener(listener);


        return mDanhMuc;
    }


    public void insertDanhMucSP(DanhMucSanPham item) {

        key = mDatabase.push().getKey();
        item.setMaDanhMuc(key);
        mDatabase.child(item.getMaDanhMuc()).setValue(item)
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

                    if (data.child("maDanhMuc").getValue(String.class).equals(item.getMaDanhMuc())) {

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

                    if (data.child("maDanhMuc").getValue(String.class).equalsIgnoreCase(item.getMaDanhMuc())) {
                        key = data.getKey();

                        Log.d("getKey", "onCreate: key :" + key);


                        mDatabase.child(key).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Xóa thành công !");

                                        //xoa san pham
                                        mDatabaseSanPham.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                                    if (data.child("maDanhMuc").getValue(String.class).equalsIgnoreCase(item.getMaDanhMuc())) {

                                                        idSP = data.getKey();

                                                        Log.d("getKey", "onCreate: key :" + idSP);

                                                        mDatabaseSanPham.child(idSP).removeValue()
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {

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
