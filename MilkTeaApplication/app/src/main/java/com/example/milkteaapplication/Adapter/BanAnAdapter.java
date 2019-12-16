package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.ChonMonActivity;

import java.util.List;

public class BanAnAdapter extends RecyclerView.Adapter<BanAnAdapter.ViewHolder> {
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

    public BanAnAdapter(Context context, List<BanAn> arrBan) {
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
            holder.tvNumber.setText(banAn.getTenBan());

            if (banAn.trangThai.equals("0")) {
                holder.ivStatus.setImageResource(R.drawable.rounded_food);
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
                String maBan = String.valueOf(arrBan.get(position).getMaBan());
                Intent i = new Intent(context, ChonMonActivity.class);
                i.putExtra("tenBan", tenBan);
                i.putExtra("maBan",maBan);
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
        private TextView tvNumber;
        private ImageView ivStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvNumber = itemView.findViewById(R.id.tv_number);
            this.ivStatus = itemView.findViewById(R.id.iv_status);
            itemView.setOnCreateContextMenuListener(this);





        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.context_xoa_ban,
                    Menu.NONE, "Xóa bàn");
        }


    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }


}
