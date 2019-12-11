package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.DAO.UserDAO;
import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.Model.User;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.ChonMonActivity;
import com.example.milkteaapplication.View.Fragment.FragmentQuanLyTaiKhoan;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BanAdapter extends RecyclerView.Adapter<BanAdapter.ViewHolder> {
    public Context context;
    public List<BanAn> arrBan;
    public LayoutInflater inflater;
    public int position;






    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BanAdapter(Context context, List<BanAn> arrBan) {
        this.context = context;
        this.arrBan = arrBan;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_table, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final BanAn banAn = arrBan.get(position);
        if (banAn != null) {
            //set len list
            holder.tvNumber.setText(banAn.tenBan);
            holder.tvTotal.setText(""+banAn.tongTien);

            String total = banAn.tongTien;
            if (total.isEmpty()) {
                holder.tvTotal.setText("");
            } else {
                DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                formatPrice.applyPattern("###,###,###");
                holder.tvTotal.setText(formatPrice.format(Integer.valueOf(total)));
            }

            if (banAn.trangThai.equals("0")) {
                holder.ivStatus.setImageResource(R.drawable.rounded_food);
                holder.tvTotal.setText("");
            } else if (banAn.trangThai.equals("1")) {
                holder.ivStatus.setImageResource(R.drawable.rounded_busy);
            }



        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenBan = String.valueOf(arrBan.get(position).getTenBan());
                String tongTien = arrBan.get(position).getTongTien();
                Intent i = new Intent(context, ChonMonActivity.class);
                i.putExtra("tenBan", tenBan);
                context.startActivity(i);

            }
        });


    }

    //lay ve so luong ite,
    @Override
    public int getItemCount() {
        return arrBan.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView tvNumber, tvTotal;
        private ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvNumber = itemView.findViewById(R.id.tv_number);
            this.tvTotal= itemView.findViewById(R.id.tv_total);
            this.ivStatus = itemView.findViewById(R.id.iv_status);
            itemView.setOnCreateContextMenuListener(this);





        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.context_tinh_tien,
                    Menu.NONE, "Tính tiền");
            menu.add(Menu.NONE, R.id.context_them_mon,
                    Menu.NONE, "Thêm món");
            menu.add(Menu.NONE, R.id.context_xoa_ban,
                    Menu.NONE, "Xóa bàn");
        }


    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }


}
