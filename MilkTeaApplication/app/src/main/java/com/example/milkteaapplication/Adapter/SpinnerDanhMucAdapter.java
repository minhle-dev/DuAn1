package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.milkteaapplication.Model.DanhMucSanPham;
import com.example.milkteaapplication.R;

import java.util.List;

public class SpinnerDanhMucAdapter extends BaseAdapter {
    Context context;
    List<DanhMucSanPham> mList;

    public SpinnerDanhMucAdapter(Context context, List<DanhMucSanPham> mList) {
        this.context = context;
        this.mList = mList;
    }

    public SpinnerDanhMucAdapter() {
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_spinner_category, null);
        }

        //Ánh xạ
        TextView title = convertView.findViewById(R.id.sp_title);
        //Put data
        DanhMucSanPham danhMucSanPham = mList.get(position);

        title.setText(danhMucSanPham.getTenDanhMuc());

        return convertView;
    }
}
