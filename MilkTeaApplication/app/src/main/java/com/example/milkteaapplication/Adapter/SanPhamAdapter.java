package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamHolder> {

    private Context mContext;
    private List<SanPham> mList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onOptionItemClick(int position);

    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public SanPhamAdapter(Context mContext, List<SanPham> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }


    @NonNull

    @Override
    public SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);

        return new SanPhamAdapter.SanPhamHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHolder holder, int position) {
        final SanPham sanPham = mList.get(position);

        holder.tenSanPham.setText(sanPham.getTenSanPham());
        Picasso.get().load(sanPham.getThumbUrl()).error(R.drawable.tea).into(holder.thumbnail);
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SanPhamHolder extends RecyclerView.ViewHolder {
        TextView tenSanPham;
        ImageView thumbnail, option_img;

        public SanPhamHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tenSanPham = itemView.findViewById(R.id.tv_ten_sanpham);
            thumbnail = itemView.findViewById(R.id.iv_sanpham_img);
            option_img = itemView.findViewById(R.id.iv_option_item);


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

            option_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onOptionItemClick(position);
                        }
                    }
                }
            });


        }
    }


}
