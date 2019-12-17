package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Model.HoaDon;
import com.example.milkteaapplication.Model.HoaDonChiTiet;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.HoaDonChiTietActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoaDonChiTietAdapter extends RecyclerView.Adapter<HoaDonChiTietAdapter.HoaDonChiTietHolder> {

    private Context mContext;
    private List<HoaDonChiTiet> mList;

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onOptionItem(int position);


    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public HoaDonChiTietAdapter(Context mContext, List<HoaDonChiTiet> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HoaDonChiTietAdapter.HoaDonChiTietHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_da_chon, parent, false);

        return new HoaDonChiTietAdapter.HoaDonChiTietHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonChiTietAdapter.HoaDonChiTietHolder holder, final int position) {

        HoaDonChiTiet hoaDonChiTiet = mList.get(position);
        DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatPrice.applyPattern("###,###,###");

        holder.tenSanPham.setText(" "+hoaDonChiTiet.getTenMon());
        holder.tvSoLuong.setText(hoaDonChiTiet.getSoLuong());
        holder.tvTongTienMon.setText(formatPrice.format(Long.valueOf(hoaDonChiTiet.getTongTien()))+ " vnđ");


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HoaDonChiTietHolder extends RecyclerView.ViewHolder {
        TextView tenSanPham,  tvSoLuong, tvTongTienMon;


        public HoaDonChiTietHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tenSanPham = itemView.findViewById(R.id.tvSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvTongTienMon = itemView.findViewById(R.id.tvTongTienMOn);

        }
    }
}
