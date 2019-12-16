package com.example.milkteaapplication.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.Model.NonUI;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.View.BanAnActivity;
import com.example.milkteaapplication.View.Fragment.FragmentChonMon;
import com.example.milkteaapplication.View.Fragment.FragmentSanPham;
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

public class SanPhamDAO {

    NonUI nonUI;
    private DatabaseReference reference;
    Context context;
    String sanPhamId;
    List<SanPham> mSanPham;
    AdapterView.OnItemSelectedListener onItemSelectedListener;
    FragmentSanPham fr;
    FragmentChonMon frChonMon;


    public SanPhamDAO(Context context) {
        this.reference = FirebaseDatabase.getInstance().getReference("SanPham");
        this.context = context;
        this.nonUI = new NonUI(context);
    }
    public SanPhamDAO(Context context, FragmentChonMon frChonMon) {
        this.reference = FirebaseDatabase.getInstance().getReference("SanPham");
        this.context = context;
        this.nonUI = new NonUI(context);
        this.frChonMon = frChonMon;
    }
    public SanPhamDAO(Context context, FragmentSanPham fr) {

        this.reference = FirebaseDatabase.getInstance().getReference("SanPham").child(Common.TABLE);
        this.context = context;
        this.nonUI = new NonUI(context);
        this.fr = fr;

    }


    public SanPhamDAO(Context context, AdapterView.OnItemSelectedListener onItemSelectedListener) {
    }


    public List<SanPham> getAllSanPham() {

        final List<SanPham> list = new ArrayList<SanPham>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get HD object and use the values to update the UI
                list.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    SanPham item = data.getValue(SanPham.class);

                    list.add(item);
                }
                frChonMon.capnhatRV();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                nonUI.toast("Không thể kết nối cơ sở dữ liệu!");
            }
        };
        reference.addValueEventListener(listener);

        return list;
    }

    public List<SanPham> getSanPham() {

        final List<SanPham> list = new ArrayList<SanPham>();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Sach object and use the values to update the UI
                list.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    SanPham s = data.getValue(SanPham.class);
                    list.add(s);
                }


                ((BanAnActivity)context).capnhatLV();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addValueEventListener(listener);

        return list;
    }


    //Read all san pham to recyclerview order by category on Spinner
    public List<SanPham> readAllSanPhamOrderByDanhMuc(final String idDanhMuc) {
        mSanPham = new ArrayList<>();

        Query query =FirebaseDatabase.getInstance().getReference("SanPham").orderByChild("maDanhMuc").startAt(idDanhMuc).endAt(idDanhMuc + "\uf8ff");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mSanPham.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    SanPham sanPham = data.getValue(SanPham.class);
                    mSanPham.add(sanPham);

                }

                fr.updateRecyclerView();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        query.addValueEventListener(listener);

        return mSanPham;
    }



    public void insertSanPham(SanPham sanPham) {
        sanPhamId = reference.push().getKey();
        sanPham.setMaSanPham(sanPhamId);
        reference.child(sanPham.getMaSanPham()).setValue(sanPham).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                nonUI.toast("Thêm thành công");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                nonUI.toast("Thêm thất bại");

            }
        });


    }

    public void delete(final SanPham item) {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maSanPham").getValue(String.class).equalsIgnoreCase(item.getMaSanPham())) {
                        sanPhamId = data.getKey();

                        Log.d("getKey", "onCreate: key :" + sanPhamId);

                        reference.child(sanPhamId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Xóa sản phẩm thành công");
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


    public void updateSanPham(final SanPham item) {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maSanPham").getValue(String.class).equals(item.getMaSanPham())) {

                        sanPhamId = data.getKey();


                        reference.child(sanPhamId).setValue(item)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        nonUI.toast("Sửa sản phẩm thành công!");
                                        Log.d("update", "update Thanh cong");


                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        nonUI.toast("Sửa thất bại!");
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

}
