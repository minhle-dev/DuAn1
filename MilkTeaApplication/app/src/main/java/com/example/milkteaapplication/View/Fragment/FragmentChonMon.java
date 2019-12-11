package com.example.milkteaapplication.View.Fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.milkteaapplication.Adapter.ThemMonAdapter;
import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.DanhMucDao;
import com.example.milkteaapplication.DAO.SanPhamDAO;
import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.Model.DanhMucSanPham;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.ChonMonActivity;
import com.example.milkteaapplication.View.DatMonActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class FragmentChonMon extends Fragment {


    private FragmentActivity myContext;
    public ThemMonAdapter adapter;

    RecyclerView recyclerView_them_mon;
    RecyclerView.LayoutManager layoutManager;
    String getNumber;

    ArrayList<SanPham> sanPhams;

    SanPhamDAO sanPhamDAO;

    public FragmentChonMon() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chon_mon, container, false);

        recyclerView_them_mon = view.findViewById(R.id.recycler_them_mon);
        recyclerView_them_mon.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView_them_mon.setLayoutManager(layoutManager);


        ChonMonActivity chonMonActivity = new ChonMonActivity();

        getNumber   = chonMonActivity.getNumber;


        //Chuan bi data

        sanPhamDAO = new SanPhamDAO(getContext(), this);


        sanPhams=(ArrayList<SanPham>)  sanPhamDAO.getAllSanPham();

        // gan adapter

        adapter = new ThemMonAdapter(getContext(), sanPhams, this);

        // dua len listView

        recyclerView_them_mon.setAdapter(adapter);
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

    public void capnhatRV(){
        adapter.notifyItemInserted(sanPhams.size());
        adapter.notifyDataSetChanged();
    }

}
