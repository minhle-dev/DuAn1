package com.example.milkteaapplication.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.DanhMucSanPhamAdapter;
import com.example.milkteaapplication.Adapter.HoaDonNhapHangAdapter;
import com.example.milkteaapplication.DAO.DanhMucDao;
import com.example.milkteaapplication.DAO.HoaDonNhapDAO;
import com.example.milkteaapplication.Model.DanhMucSanPham;
import com.example.milkteaapplication.Model.HoaDonNhapHang;
import com.example.milkteaapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class FragmentDanhMuc extends Fragment {


    private FragmentActivity myContext;
    public DanhMucSanPhamAdapter danhMucSanPhamAdapter;
    DanhMucDao danhMucDao;
    ArrayList<DanhMucSanPham> danhMucSanPhams;
    FloatingActionButton fabDanhMucSp;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView_DanhMucSp;
    MaterialEditText edtMaDanhMuc, edtTenDanhMuc;


    public FragmentDanhMuc() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_danh_muc, container, false);
        recyclerView_DanhMucSp = view.findViewById(R.id.recycler_danh_muc);
        recyclerView_DanhMucSp.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_DanhMucSp.setLayoutManager(layoutManager);
        fabDanhMucSp = view.findViewById(R.id.fabDanhMuc);

        //Chuan bi data

        danhMucDao = new DanhMucDao(getActivity(), this);

        danhMucSanPhams = (ArrayList<DanhMucSanPham>) danhMucDao.getAllDanhMuc();

        // gan adapter

        danhMucSanPhamAdapter = new DanhMucSanPhamAdapter(getActivity(), danhMucSanPhams, this);

        // dua len listView

        recyclerView_DanhMucSp.setAdapter(danhMucSanPhamAdapter);
        danhMucSanPhamAdapter.notifyDataSetChanged();

        fabDanhMucSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemDanhMuc();
            }
        });




        return view;
    }

    private void ThemDanhMuc() {
        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Xin chờ...");
        mDialog.show();
        //
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Thêm hóa đơn nhập hàng");
        builder.setMessage("Vui lòng nhập đủ thông tin!");

        //init view
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_them_danh_muc, null);
        edtMaDanhMuc = itemView.findViewById(R.id.edtMaDanhMuc);
        edtTenDanhMuc = itemView.findViewById(R.id.edtTenDanhMuc);
        //set
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
                String maDanhMuc = edtMaDanhMuc.getText().toString();
                String tenDanhMuc = edtTenDanhMuc.getText().toString();


                DanhMucSanPham danhMucSanPham = new DanhMucSanPham(maDanhMuc,tenDanhMuc);
                danhMucDao.insertDanhMucSP(danhMucSanPham);
            }
        });
        builder.setView(itemView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    ///--Sua danh muc---////
    private void SuaDanhMuc(int position) {
        final DanhMucSanPham danhmuc = danhMucSanPhams.get(position);
        final ProgressDialog mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Xin chờ...");
        mDialog.show();
        //
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setTitle("Thêm hóa đơn nhập hàng");
        builder.setMessage("Vui lòng nhập đủ thông tin!");

        //init view
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_dialog_sua_danh_muc, null);
        edtMaDanhMuc = itemView.findViewById(R.id.edtMaDanhMuc);
        edtTenDanhMuc = itemView.findViewById(R.id.edtTenDanhMuc);
        //set tetx dialog sua
        edtMaDanhMuc.setText(danhmuc.maDanhMuc);
        edtTenDanhMuc.setText(danhmuc.tenDanhMuc);
        //
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
                String maDanhMuc = edtMaDanhMuc.getText().toString();
                String tenDanhMuc = edtTenDanhMuc.getText().toString();


                DanhMucSanPham danhMucSanPham = new DanhMucSanPham(maDanhMuc,tenDanhMuc);
                danhMucDao.updateDanhMuc(danhMucSanPham);
            }
        });
        builder.setView(itemView);
        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }


    ////////////////////click card view//////////////
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;

        try {
            position = ((DanhMucSanPhamAdapter) recyclerView_DanhMucSp.getAdapter()).getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {

            case R.id.context_edit_danh_muc:
                SuaDanhMuc(position);
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
        danhMucSanPhamAdapter.notifyDataSetChanged();
    }

}
