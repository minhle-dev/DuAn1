package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.DanhMucDao;
import com.example.milkteaapplication.View.Fragment.FragmentDanhMuc;
import com.example.milkteaapplication.Model.DanhMucSanPham;
import com.example.milkteaapplication.R;

import java.util.List;

public class DanhMucSanPhamAdapter extends RecyclerView.Adapter<DanhMucSanPhamAdapter.ViewHolder> {
    public Context context;
    public List<DanhMucSanPham> arrDanhMucSp;
    public LayoutInflater inflater;

    FragmentDanhMuc fr;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public DanhMucSanPhamAdapter(Context context, List<DanhMucSanPham> arrDanhMucSp, FragmentDanhMuc fr) {
        this.context = context;
        this.arrDanhMucSp = arrDanhMucSp;
        this.fr = fr;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_danh_muc_san_pham, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final DanhMucSanPham danhMucSanPham = arrDanhMucSp.get(position);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (Common.currentUser.getRole().equals("admin")){
                   // khởi tạo AlertDialog từ đối tượng Builder. tham số truyền vào ở đây là context.
                   AlertDialog.Builder builder = new AlertDialog.Builder(context);

                   // set Message là phương thức thiết lập câu thông báo
                   builder.setMessage("Bạn có muốn xóa danh mục "+danhMucSanPham.getTenDanhMuc()+" ?"
                           +"            Tất cả các món trong danh mục sẽ bị xóa.")
                           // positiveButton là nút thuận : đặt là OK
                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   DanhMucDao danhMucDao = new DanhMucDao(context, fr);
                                   danhMucDao.deleteDanhMuc(arrDanhMucSp.get(position));
                                   arrDanhMucSp.remove(position);
                                   notifyDataSetChanged();
                               }
                           })
                           // ngược lại negative là nút nghịch : đặt là cancel
                           .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                   // User cancelled the dialog
                               }
                           });
                   // tạo dialog và hiển thị
                   builder.create().show();
               }else {
                   Toast.makeText(context, "Bạn không có quyền xóa!", Toast.LENGTH_SHORT).show();
               }


            }
        });

        if (danhMucSanPham != null) {
            //set len list
            holder.tvMaDanhMuc.setText("Mã: " + danhMucSanPham.maDanhMuc);
            holder.tvTenDanhMuc.setText("Tên : " + danhMucSanPham.tenDanhMuc);

        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    //lay ve so luong ite,
    @Override
    public int getItemCount() {
        return arrDanhMucSp.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon, ivDelete;
        TextView tvMaDanhMuc;
        TextView tvTenDanhMuc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvMaDanhMuc = itemView.findViewById(R.id.tvMaDanhMuc);
            this.tvTenDanhMuc = itemView.findViewById(R.id.tvTenDanhMuc);

            this.ivDelete = itemView.findViewById(R.id.ivDelete);

            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null

            menu.add(Menu.NONE, R.id.context_edit_danh_muc,
                    Menu.NONE, "Sửa thông tin");
        }
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }


}
