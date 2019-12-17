package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamHolder> {

    private Context mContext;
    private List<SanPham> mList;
    private OnItemClickListener mListener;
    private int position = -1;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onOptionItemClick(int position);

    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public SanPhamAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull

    @Override
    public SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);

        return new SanPhamAdapter.SanPhamHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SanPhamHolder holder, int position) {
        final SanPham sanPham = mList.get(position);
        DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatPrice.applyPattern("###,###,###");

        holder.tenSanPham.setText(sanPham.getTenSanPham());
        holder.tvGiaTien.setText(formatPrice.format(Long.valueOf(sanPham.getGiaTienSanPham()))+" vnđ");
        Picasso.get().load(sanPham.getThumbUrl()).error(R.drawable.tea).into(holder.thumbnail);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getAdapterPosition());
                return false;
            }
        });
    }



    @Override
    public int getItemCount() {
        if(mList != null){
            return mList.size();
        }
        return 0;
    }

    public class SanPhamHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView tenSanPham, tvGiaTien;
        ImageView thumbnail;

        public SanPhamHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tenSanPham = itemView.findViewById(R.id.tv_ten_sanpham);
            tvGiaTien = itemView.findViewById(R.id.tv_giatien);
            thumbnail = itemView.findViewById(R.id.iv_sanpham_img);
            itemView.setOnCreateContextMenuListener( this);

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

        @Override
        public void onCreateContextMenu(
                ContextMenu menu, View v,
                ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.add_pic,
                    Menu.NONE, "Thêm ảnh");

            menu.add(Menu.NONE, R.id.edit_sp,
                    Menu.NONE,"Sửa sản phẩm");

            menu.add(Menu.NONE, R.id.del_sp,
                    Menu.NONE,"Xóa sản phẩm");
        }
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public void setListSanPham(List<SanPham>mList){
        if (mList!=null){
            this.mList = mList;
            notifyDataSetChanged();
        }
    }

}
