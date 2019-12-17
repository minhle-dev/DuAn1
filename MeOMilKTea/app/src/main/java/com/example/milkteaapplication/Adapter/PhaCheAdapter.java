package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.DatMonDAO;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.Fragment.FragmentDaChon;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PhaCheAdapter extends RecyclerView.Adapter<PhaCheAdapter.DaChonHolder> {

    private DatabaseReference mDatabase;
    private Context mContext;
    private List<DatMon> datMonList;
    private OnItemClickListener mListener;

    String Table;


    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }




    public PhaCheAdapter(Context mContext, List<DatMon> datMonList) {
        this.mContext = mContext;
        this.datMonList = datMonList;

    }

    @NonNull

    @Override
    public DaChonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_da_chon, parent, false);

        return new PhaCheAdapter.DaChonHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DaChonHolder holder, final int i) {

        DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatPrice.applyPattern("###,###,###");
        Table = Common.TABLE;

        String tenMon = datMonList.get(i).getTenMon();
        String soLuong = datMonList.get(i).getSoLuong();
        String tongTien = datMonList.get(i).getTongTien();
        holder.tenSanPham.setText(" "+tenMon);
        holder.tvSoLuong.setText(soLuong);
        holder.tvTongTienMon.setText(" "+tongTien+" vnđ");



    }

    @Override
    public int getItemCount() {
        return datMonList.size();
    }

    public class DaChonHolder extends RecyclerView.ViewHolder {
        TextView tenSanPham,  tvSoLuong, tvTongTienMon;


        public DaChonHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tenSanPham = itemView.findViewById(R.id.tvSanPham);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvTongTienMon = itemView.findViewById(R.id.tvTongTienMOn);

        }

    }



}
