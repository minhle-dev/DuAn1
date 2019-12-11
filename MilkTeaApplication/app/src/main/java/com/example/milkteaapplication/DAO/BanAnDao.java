package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.View.DatMonActivity;
import com.example.milkteaapplication.View.MainActivity;
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


    public BanAnDao(Context context) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("BanAn");
        this.context = context;
        this.nonUI = new NonUI(context);

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
                ((DatMonActivity) context).capnhatLV();
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

        mDatabase.child(banId).setValue(banAn).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                nonUI.toast("Thêm bàn thành công");
                Log.d("insert", "insert Thanh cong");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                nonUI.toast("insert That bai");
                Log.d("insert", "insert That bai");
            }
        });


    }

//    public void update(final Sach s) {
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//
//                    if (data.child("maSach").getValue(String.class).equalsIgnoreCase(s.maSach)) {
//                        sachId = data.getKey();
//                        Log.d("getKey", "onCreate: key :" + sachId);
//
//
//                        mDatabase.child(sachId).setValue(s)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        nonUI.toast("update Thanh cong");
//                                        Log.d("update", "update Thanh cong");
//
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        nonUI.toast("update That bai");
//                                        Log.d("update", "update That bai");
//                                    }
//                                });
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    public void deleteBan(final BanAn banAn) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("tenBan").getValue(String.class).equals(banAn.tenBan)) {
                        banId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + banId);


                        mDatabase.child(banId).setValue(null)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Xóa bàn thành công");
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


