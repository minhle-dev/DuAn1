package com.example.milkteaapplication.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.milkteaapplication.View.Fragment.FragmentChonMon;
import com.example.milkteaapplication.View.Fragment.FragmentDaChon;


public class PagerChonMonAdapter extends FragmentStatePagerAdapter {
    Context context;

    public PagerChonMonAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.context = context;
    }
    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag = new FragmentChonMon();
                break;
            case 1:
                frag = new FragmentDaChon();

                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";
        switch (position){
            case 0:
                title = "Chọn món";
                break;
            case 1:
                title = "Đã chọn";

                break;
        }
        return title;
    }
}