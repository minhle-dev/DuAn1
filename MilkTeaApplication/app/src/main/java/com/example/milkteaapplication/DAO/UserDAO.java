package com.example.milkteaapplication.DAO;

import com.example.milkteaapplication.View.Fragment.FragmentQuanLyTaiKhoan;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private DatabaseReference mDatabase;

    NonUI nonUI;
    Context context;
    FragmentQuanLyTaiKhoan fr;
    String key;



    public UserDAO(Context context, FragmentQuanLyTaiKhoan fr) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("User");
        this.context = context;
        this.nonUI = new NonUI(context);
        this.fr = fr;

    }



    public List<User> getAllUser() {

        final List<User> list = new ArrayList<User>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get user object and use the values to update the UI
                list.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User item = data.getValue(User.class);

                    list.add(item);
                }

                fr.capnhatLV();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addValueEventListener(valueEventListener);

        return list;
    }

    public void deleteUser(final User s) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("phone").getValue(String.class).equalsIgnoreCase(s.getPhone())){
                        key = data.getKey();
                        Log.d("getKey", "onCreate: key :" + key);


                        mDatabase.child(key).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Xóa tài khoản thành công !", Toast.LENGTH_SHORT).show();
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
    public void updateUser(final User item) {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("phone").getValue(String.class).equals(item.getPhone())){

                        key = data.getKey();


                        mDatabase.child(key).setValue(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Sửa thành công !!");
                                        Log.d("update","update Thanh cong");


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("Sủa thất bại !!");
                                        Log.d("update","update That bai");
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
