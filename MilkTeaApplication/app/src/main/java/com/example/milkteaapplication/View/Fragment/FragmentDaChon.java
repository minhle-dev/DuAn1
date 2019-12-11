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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Adapter.DanhMucSanPhamAdapter;
import com.example.milkteaapplication.DAO.DanhMucDao;
import com.example.milkteaapplication.Model.DanhMucSanPham;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.DatMonActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class FragmentDaChon extends Fragment {
    private FragmentActivity myContext;


    public FragmentDaChon() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_chon, container, false);


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


}
