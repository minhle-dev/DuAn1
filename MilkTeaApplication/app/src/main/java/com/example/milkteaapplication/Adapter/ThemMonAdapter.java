package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.Fragment.FragmentChonMon;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ThemMonAdapter extends RecyclerView.Adapter<ThemMonAdapter.ThemMonHolder> {

    private Context mContext;
    private List<SanPham> mList;
    public static List<DatMon> datMonList =  new ArrayList<>();;
    private OnItemClickListener mListener;
    FragmentChonMon fragmentChonMon;
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onOptionItemClick(int position);

    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public ThemMonAdapter(Context mContext, List<SanPham> mList, FragmentChonMon fragmentChonMon) {
        this.mContext = mContext;
        this.mList = mList;
        this.fragmentChonMon = fragmentChonMon;

    }


    @NonNull

    @Override
    public ThemMonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_them_mon, parent, false);

        return new ThemMonAdapter.ThemMonHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThemMonHolder holder, int position) {
        final SanPham sanPham = mList.get(position);
        DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatPrice.applyPattern("###,###,###");

        holder.tenSanPham.setText(sanPham.getTenSanPham());

        holder.giaSanPham.setText(formatPrice.format(Integer.valueOf((int) sanPham.giaTienSanPham))+" vnđ");

        holder.tvSoLuong.setTag(0);

        holder.ivTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.tvSoLuong.getTag().toString());
                dem++;
                holder.tvSoLuong.setText(dem + "");
                holder.tvSoLuong.setTag(dem);

                DatMon datMonTag = (DatMon) holder.ivGiam.getTag();
                if(datMonTag != null){
                    ThemMonAdapter.datMonList.remove(datMonTag);
                }

                DatMon datMon = new DatMon();
                datMon.setSoLuong(dem);
                datMon.setTenMon(sanPham.getTenSanPham());
                datMon.setGiaTien(sanPham.getGiaTienSanPham());
                holder.ivGiam.setTag(datMon);

                ThemMonAdapter.datMonList.add(datMon);


            }
        });

        holder.ivGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.tvSoLuong.getTag().toString());
                if(dem != 0){
                    dem--;
                    if(dem == 0){
                        DatMon datMon = (DatMon) view.getTag();
                        ThemMonAdapter.datMonList.remove(datMon);
                    }
                }

                holder.tvSoLuong.setText(dem+"");
                holder.tvSoLuong.setTag(dem);

            }
        });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ThemMonHolder extends RecyclerView.ViewHolder {
        TextView tenSanPham, giaSanPham, tvSoLuong;
        ImageView ivTang, ivGiam;

        public ThemMonHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tenSanPham = itemView.findViewById(R.id.tvSanPham);
            tvSoLuong = itemView.findViewById(R.id.tv_so_luong);
            giaSanPham = itemView.findViewById(R.id.tvGiaSanPham);
            ivGiam = itemView.findViewById(R.id.img_giam_so_luong);
            ivTang = itemView.findViewById(R.id.img_tang_so_luong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


        }
    }


}
