package com.example.milkteaapplication.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.DatMonAdapter;
import com.example.milkteaapplication.DAO.SanPhamDAO;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.ChonMonActivity;

import java.util.ArrayList;


public class FragmentChonMon extends Fragment {



    private FragmentActivity myContext;
    public DatMonAdapter adapter;

    RecyclerView recyclerView_them_mon;
    RecyclerView.LayoutManager layoutManager;
    String getNumber, getIdBan;
    Button addData;
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

        addData = view.findViewById(R.id.add);

        ChonMonActivity chonMonActivity = new ChonMonActivity();

        getNumber   = chonMonActivity.getNumber;
        getIdBan   = chonMonActivity.getIdBan;


        //Chuan bi data

        sanPhamDAO = new SanPhamDAO(getContext(), this);


        sanPhams=(ArrayList<SanPham>)  sanPhamDAO.getAllSanPham();

        // gan adapter

        adapter = new DatMonAdapter(getContext(), sanPhams, this);


        recyclerView_them_mon.setAdapter(adapter);
        capnhatRV();



        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void capnhatRV() {
        adapter.notifyItemInserted(sanPhams.size());
        adapter.notifyDataSetChanged();
    }


}
