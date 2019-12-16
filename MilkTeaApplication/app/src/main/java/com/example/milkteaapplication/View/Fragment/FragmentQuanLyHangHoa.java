package com.example.milkteaapplication.View.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.HoaDonNhapHangAdapter;
import com.example.milkteaapplication.DAO.HoaDonNhapDAO;
import com.example.milkteaapplication.Model.HoaDonNhapHang;
import com.example.milkteaapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.ContentValues.TAG;


public class FragmentQuanLyHangHoa extends Fragment {


    private FragmentActivity myContext;
    FragmentQuanLyHangHoa fr;
    public HoaDonNhapHangAdapter donNhapHangAdapter;
    HoaDonNhapDAO donNhapDAO;
    ArrayList<HoaDonNhapHang> donNhapHangs;
    FloatingActionButton fabHdNhap;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_HDNHap;
    MaterialEditText edtMaHDNhap, edtTenHDNhap, edtSoLuongNhap, edtGiaNhap;
    TextView tvNgayNhap, tvTongTienNhap;


    public FragmentQuanLyHangHoa() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_hang_hoa, container, false);

        recyclerView_HDNHap = view.findViewById(R.id.recycler_hdNhap);
        recyclerView_HDNHap.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_HDNHap.setLayoutManager(layoutManager);
        fabHdNhap = view.findViewById(R.id.fabHDNhap);
        tvTongTienNhap = view.findViewById(R.id.tvTongTienNhap);



        //Chuan bi data

        donNhapDAO = new HoaDonNhapDAO(getActivity(), this);

        donNhapHangs = (ArrayList<HoaDonNhapHang>) donNhapDAO.getAllHDNH();

        // gan adapter

        donNhapHangAdapter = new HoaDonNhapHangAdapter(getActivity(), donNhapHangs, this);

        // dua len listView

        recyclerView_HDNHap.setAdapter(donNhapHangAdapter);
        donNhapHangAdapter.notifyDataSetChanged();

        fabHdNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemHoaDonNhapHang();
            }
        });

        capnhatLV();

        return view;
    }

    private void ThemHoaDonNhapHang() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Thêm hóa đơn nhập hàng");
        builder.setMessage("Vui lòng nhập đủ thông tin!");

        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Xin chờ...");
        mDialog.show();

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_them_hd_nhap_hang, null);
        edtMaHDNhap = itemView.findViewById(R.id.edtMaHDNhap);
        edtTenHDNhap = itemView.findViewById(R.id.edtTenHDNhap);
        edtSoLuongNhap = itemView.findViewById(R.id.edtSoLuongNhap);
        edtGiaNhap = itemView.findViewById(R.id.edtSoTienNhap);
        tvNgayNhap = itemView.findViewById(R.id.tvNgayNhap);

        tvNgayNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDate();
            }
        });
        //set
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        builder.setView(itemView);
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
            }
        });
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
                String maHDNhap = edtMaHDNhap.getText().toString();
                String tenHDNhap = edtTenHDNhap.getText().toString();
                long soLuongNhap = Long.parseLong(edtSoLuongNhap.getText().toString());
                long giaNhap = Long.parseLong(edtGiaNhap.getText().toString());
                String ngayNhap = tvNgayNhap.getText().toString();
                //validate

                if (TextUtils.isEmpty(maHDNhap)) {
                    Toast.makeText(myContext, "Nhập vào mã hóa đơn !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(tenHDNhap)) {
                    Toast.makeText(myContext, "Nhập vào tên hóa đơn !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edtSoLuongNhap.getText().toString())) {
                    Toast.makeText(myContext, "Nhập vào số lương", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edtGiaNhap.getText().toString())) {
                    Toast.makeText(myContext, "Nhập vào giá", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ngayNhap.equals("Chọn Ngày Nhập Hàng")) {
                    Toast.makeText(myContext, "Nhập vào ngày nhập ", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    for (int j = 0; j < donNhapHangs.size(); j++) {
                        if (donNhapHangs.get(j).maHoaDonNhap.equals(maHDNhap)) {
                            Toast.makeText(getContext(), "Không được nhập trùng mã hóa đơn!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                HoaDonNhapHang hdNhap = new HoaDonNhapHang(maHDNhap, tenHDNhap, ngayNhap, soLuongNhap, giaNhap);
                donNhapDAO.insertHDNH(hdNhap);
            }
        });
        builder.setView(itemView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    //////Sua Hoa DOn//////////
    private void SuaHoaDonNhap(int position) {
        final HoaDonNhapHang hang = donNhapHangs.get(position);
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Sửa hóa đơn nhập hàng");
        builder.setMessage("Vui lòng nhập đủ thông tin!");

        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Xin chờ...");
        mDialog.show();

        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_sua_hd_nhap_hang, null);
        edtMaHDNhap = itemView.findViewById(R.id.edtMaHDNhap);
        edtTenHDNhap = itemView.findViewById(R.id.edtTenHDNhap);
        edtSoLuongNhap = itemView.findViewById(R.id.edtSoLuongNhap);
        edtGiaNhap = itemView.findViewById(R.id.edtSoTienNhap);
        tvNgayNhap = itemView.findViewById(R.id.tvNgayNhap);

        tvNgayNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDate();
            }
        });
        //set text len dialog để sửa
        edtMaHDNhap.setText(hang.maHoaDonNhap);
        edtGiaNhap.setText(hang.soTienNhap+"");
        edtTenHDNhap.setText(hang.tenHoaDonNhap);
        edtSoLuongNhap.setText(hang.soLuongNhap+"");
        tvNgayNhap.setText(hang.ngayNhap);
        //set
        builder.setView(itemView);
        builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
            }
        });
        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mDialog.dismiss();
                String maHDNhap = edtMaHDNhap.getText().toString();
                String tenHDNhap = edtTenHDNhap.getText().toString();
              long soLuongNhap = Long.parseLong(edtSoLuongNhap.getText().toString());
               long giaNhap = Long.parseLong(edtGiaNhap.getText().toString());
                String ngayNhap = tvNgayNhap.getText().toString();


                if (TextUtils.isEmpty(maHDNhap)) {
                    Toast.makeText(myContext, "Nhập vào mã hóa đơn !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(tenHDNhap)) {
                    Toast.makeText(myContext, "Nhập vào tên hóa đơn !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edtSoLuongNhap.getText().toString())) {
                    Toast.makeText(myContext, "Nhập vào số lương", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(edtGiaNhap.getText().toString())) {
                    Toast.makeText(myContext, "Nhập vào giá", Toast.LENGTH_SHORT).show();
                    return;
                } else if (ngayNhap.equals("Chọn Ngày Nhập Hàng")) {
                    Toast.makeText(myContext, "Nhập vào ngày nhập", Toast.LENGTH_SHORT).show();
                    return;}

                HoaDonNhapHang hdNhap = new HoaDonNhapHang(maHDNhap, tenHDNhap, ngayNhap, soLuongNhap, giaNhap);
                donNhapDAO.updateHDNH(hdNhap);
            }
        });
        builder.setView(itemView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ThongKeTienNhapHang() {
        DatabaseReference db_hdNhap = FirebaseDatabase.getInstance().getReference().child("HoaDonNhapHang");
        db_hdNhap.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double sum = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()
                ) {
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object giaTienNhap = map.get("soTienNhap");
                    double pValue = Integer.parseInt(String.valueOf(giaTienNhap));
                    sum += pValue;

                    tvTongTienNhap.setText("  Tổng Tiền Chi:  " + NumberFormat.getNumberInstance(Locale.US).format(sum) + " VNĐ");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    ////////////////////click card view//////////////
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((HoaDonNhapHangAdapter) recyclerView_HDNHap.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_edit_hoa_don_nhap:
                SuaHoaDonNhap(position);
                break;
        }
        return super.onContextItemSelected(item);
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

    public void capnhatLV() {
        donNhapHangAdapter.notifyDataSetChanged();
        ThongKeTienNhapHang();
    }


    public void ChooseDate() {
        final Calendar calendar = Calendar.getInstance();
        //Date
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        int Month = calendar.get(Calendar.MONTH);
        int Year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                tvNgayNhap.setText(sdf.format(calendar.getTime()));
            }
        }, Year, Month, Day);
        datePickerDialog.show();
    }

    public int checkMaTrung(List<HoaDonNhapHang> lsHD, String maHoaDonNhap) {
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++) {
            HoaDonNhapHang hd = lsHD.get(i);
            if (hd.maHoaDonNhap.equalsIgnoreCase(maHoaDonNhap)) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
