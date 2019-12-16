package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.View.Fragment.FragmentDaChon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatMonDAO {

    NonUI nonUI;
    private DatabaseReference reference;
    Context context;
    String odId;
    List<DatMon> datMons;
    AdapterView.OnItemSelectedListener onItemSelectedListener;
    FragmentDaChon fr;


    public DatMonDAO(Context context) {
        this.reference = FirebaseDatabase.getInstance().getReference("MonDaChon");
        this.context = context;
        this.nonUI = new NonUI(context);
    }

    public DatMonDAO(Context context, FragmentDaChon fr) {
        this.reference = FirebaseDatabase.getInstance().getReference("MonDaChon").child(Common.TABLE);
        this.context = context;
        this.nonUI = new NonUI(context);
        this.fr = fr;
    }


    public List<DatMon> getDatMons() {
        datMons = new ArrayList<>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                datMons.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    DatMon datMon = data.getValue(DatMon.class);
                    datMons.add(datMon);
                }

                fr.capnhatRV();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        reference.addValueEventListener(listener);

        return datMons;
    }


    public void xoaMon(final DatMon datMon) {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("id").getValue(String.class).equalsIgnoreCase(datMon.getId())){
                        odId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + odId);
                        reference.child(odId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Đã xóa món khỏi đơn!", Toast.LENGTH_SHORT).show();
                                        Log.d("delete","delete Thanh cong");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Xóa thất bại !", Toast.LENGTH_SHORT).show();
                                        Log.d("delete","delete That bai");
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
