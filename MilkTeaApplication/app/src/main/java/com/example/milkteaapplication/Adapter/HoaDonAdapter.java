package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.milkteaapplication.Model.HoaDon;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.ChonMonActivity;
import com.example.milkteaapplication.View.HoaDonChiTietActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.BillHolder> {

    private Context mContext;
    private List<HoaDon> mList;

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onOptionItem(int position);
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HoaDonAdapter(Context mContext, List<HoaDon> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HoaDonAdapter.BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_list, parent, false);

        return new HoaDonAdapter.BillHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonAdapter.BillHolder holder, final int position) {

        HoaDon hoaDon = mList.get(position);
        DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatPrice.applyPattern("###,###,###");

        holder.maHoaDon.setText(hoaDon.getMaHoaDon());
        holder.time.setText(hoaDon.getTime());
        holder.date.setText(hoaDon.getDate());
        holder.username.setText(hoaDon.getTenUser());
        holder.tongTien.setText("$ "+formatPrice.format(Long.valueOf(hoaDon.getThanhTien()))+" vnđ");
        holder.tenBan.setText("Bàn: "+hoaDon.getTenBan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maHoaDon = String.valueOf(mList.get(position).getMaHoaDon());

                Intent ihd = new Intent(mContext, HoaDonChiTietActivity.class);
                ihd.putExtra("maHoaDon", maHoaDon);
                mContext.startActivity(ihd);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BillHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView maHoaDon, date, time, username, tongTien, tenBan;


        public BillHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            maHoaDon = itemView.findViewById(R.id.tvMaHD);
            date = itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            username = itemView.findViewById(R.id.tvUsername);
            tongTien = itemView.findViewById(R.id.tvThanhTien);
            tenBan = itemView.findViewById(R.id.tvTenBan);



            itemView.setOnCreateContextMenuListener(this);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //menuInfo is null

        menu.add(Menu.NONE, R.id.context_xoa_hoa_don,
                Menu.NONE, "Xóa hóa đơn");
    }


    }
}
