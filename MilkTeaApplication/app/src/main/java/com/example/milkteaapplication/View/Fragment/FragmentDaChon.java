package com.example.milkteaapplication.View.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.DaChonAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.BanAnDao;
import com.example.milkteaapplication.DAO.DatMonDAO;
import com.example.milkteaapplication.DAO.HoaDonDao;
import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.HoaDon;
import com.example.milkteaapplication.Model.HoaDonChiTiet;
import com.example.milkteaapplication.Model.ThanhTien;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.BanAnActivity;
import com.example.milkteaapplication.View.ChonMonActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class FragmentDaChon extends Fragment {
    private FragmentActivity myContext;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DaChonAdapter adapter;
    List<DatMon> hoaDonList;
    RecyclerView recyclerView_da_chon;
    RecyclerView.LayoutManager layoutManager;
    String getNumber, getIdBan;
    TextView tvTongTienAll;
    ArrayList<DatMon> datMons;
    Button btnThanhToan;
    DatMonDAO datMonDAO;
    final DatMon datMon = new DatMon();
    final ThanhTien thanhTien = new ThanhTien();
    final String tenBan = Common.TABLE;
    final String maBan = Common.TABLEID;


    public FragmentDaChon() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_chon, container, false);
        recyclerView_da_chon = view.findViewById(R.id.recycler_da_chon);
        tvTongTienAll = view.findViewById(R.id.tvTongTienAll);
        btnThanhToan = view.findViewById(R.id.btn_thanh_toan);
        recyclerView_da_chon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_da_chon.setLayoutManager(layoutManager);

        ChonMonActivity chonMonActivity = new ChonMonActivity();

        getNumber = chonMonActivity.getNumber;
        getIdBan = chonMonActivity.getIdBan;

        //Chuan bi data

        datMonDAO = new DatMonDAO(getContext(), this);


        datMons = (ArrayList<DatMon>) datMonDAO.getDatMons();

        // gan adapter

        adapter = new DaChonAdapter(getContext(), datMons, this);
        recyclerView_da_chon.setAdapter(adapter);


        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                final String time = new SimpleDateFormat("kk:mm", Locale.getDefault()).format(new Date());
                final long thanhToan = thanhTien.getThanhTien();
                final DatabaseReference ThanhToan = database.getReference("HoaDon");
                final DatabaseReference hoaDonChiTietthanhtoa = database.getReference("HoaDonChiTiet");
                final String key = ThanhToan.push().getKey();
                final String keyHDCT = hoaDonChiTietthanhtoa.push().getKey();
                // khởi tạo AlertDialog từ đối tượng Builder. tham số truyền vào ở đây là context.
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());

                builder.setMessage("Bạn muốn thanh toán bàn " + Common.TABLE + " ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //tao ra hoa don
                                HoaDon addHoaDon = new HoaDon();

                                addHoaDon.setMaHoaDon(key);
                                addHoaDon.setDate(date);
                                addHoaDon.setTime(time);
                                addHoaDon.setTenBan(tenBan);
                                addHoaDon.setThanhTien((thanhToan));
                                addHoaDon.setTenUser(Common.currentUser.getFullname());
                                addHoaDon.setDatMonList(datMons);
                                ThanhToan.child(key).setValue(addHoaDon);
                                Toast.makeText(getContext(), "Đã thanh toán bàn " + tenBan, Toast.LENGTH_SHORT).show();

                                //xoa list ban
                                DatabaseReference del = database.getReference("MonDaChon").child(Common.TABLE);
                                del.setValue(null);


                                //cap nhat trang thai cua ban an sang co nguoi
                                DatabaseReference doiTrangThai = database.getReference("BanAn").child(Common.TABLEID).child("trangThai");
                                doiTrangThai.setValue("0");


                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                builder.create().show();
            }
        });


        capnhatRV();


        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void TongTienBan() {
        DatabaseReference db_tongTienBan = FirebaseDatabase.getInstance().getReference().child("MonDaChon").child(Common.TABLE);
        db_tongTienBan.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long total = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()
                ) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object tongTien = null;
                    if (map != null) {
                        tongTien = map.get("tongTien");
                    }
                    long pValue = Integer.parseInt(String.valueOf(tongTien));
                    total += pValue;


                }
                tvTongTienAll.setText(" Bàn " + Common.TABLE + ": " + NumberFormat.getNumberInstance(Locale.US).format(total) + " VNĐ");
                thanhTien.setThanhTien(total);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    private void refreshFragment() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).commitNow();
            getFragmentManager().beginTransaction().attach(this).commitNow();

        } else {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public void capnhatRV() {
        adapter.notifyItemInserted(datMons.size());
        adapter.notifyDataSetChanged();

        TongTienBan();
    }

}
