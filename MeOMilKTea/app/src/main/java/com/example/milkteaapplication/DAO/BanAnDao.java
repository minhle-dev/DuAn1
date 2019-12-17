package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.View.BanAnActivity;
import com.example.milkteaapplication.View.BanAnBepActivity;
import com.example.milkteaapplication.View.Fragment.FragmentDaChon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BanAnDao {


    private DatabaseReference mDatabase;

    NonUI nonUI;
    Context context;
    String banId;
    String key;
    FragmentDaChon fragmentDaChon;

    public BanAnDao(Context context) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("BanAn");
        this.context = context;
        this.nonUI = new NonUI(context);

    }
    public BanAnDao(Context context, FragmentDaChon fragmentDaChon) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("BanAn");
        this.context = context;
        this.nonUI = new NonUI(context);
        this.fragmentDaChon = fragmentDaChon;
    }


    public List<BanAn> getAllBanAn() {

        final List<BanAn> list = new ArrayList<BanAn>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get ban an object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    BanAn banAn = data.getValue(BanAn.class);
                    list.add(banAn);
                }
                ((BanAnActivity) context).capnhatLV();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }

    public List<BanAn> getAllBanAnBep() {

        final List<BanAn> list = new ArrayList<BanAn>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get ban an object and use the values to update the UI
                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    BanAn banAn = data.getValue(BanAn.class);
                    list.add(banAn);
                }
                ((BanAnBepActivity) context).capnhatLV();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(listener);

        return list;
    }

    public void insertBan(BanAn banAn) {


        banId = mDatabase.push().getKey();
        banAn.setMaBan(banId);
        mDatabase.child(banId).setValue(banAn).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                nonUI.toast("Thêm bàn thành công");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                nonUI.toast("insert That bai");
            }
        });


    }

    public void updateBan(final BanAn item) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maBan").getValue(String.class).equals(item.getMaBan())) {

                        key = data.getKey();

                        mDatabase.child(key).setValue(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("update", "doi trang thai ban");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
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



    public void deleteBan(final BanAn banAn) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maBan").getValue(String.class).equals(banAn.getMaBan())) {
                        banId = data.getKey();

                        mDatabase.child(banId).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Xóa bàn thành công");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("delete That bai");

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


