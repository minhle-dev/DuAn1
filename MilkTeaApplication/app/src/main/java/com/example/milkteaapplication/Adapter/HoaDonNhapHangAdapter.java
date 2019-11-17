package com.example.milkteaapplication.Adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.DAO.HoaDonNhapDAO;
import com.example.milkteaapplication.View.Fragment.FragmentQuanLyHangHoa;
import com.example.milkteaapplication.Model.HoaDonNhapHang;
import com.example.milkteaapplication.R;

import java.util.List;

public class HoaDonNhapHangAdapter extends RecyclerView.Adapter<HoaDonNhapHangAdapter.ViewHolder> {
    public Context context;
    public List<HoaDonNhapHang> arrHoaDonNhap;
    public LayoutInflater inflater;
    public HoaDonNhapDAO hoaDonNhapDAO;
    FragmentQuanLyHangHoa fr;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public HoaDonNhapHangAdapter(Context context, List<HoaDonNhapHang> arrHoaDonNhap, FragmentQuanLyHangHoa fr) {
        this.context = context;
        this.arrHoaDonNhap = arrHoaDonNhap;
        this.fr = fr;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    //tao view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_hoa_don_nhap_hang, null);
        return new ViewHolder(view);
    }

    //lay du lieu
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoaDonNhapDAO hoaDonNhapDAO = new HoaDonNhapDAO(context, fr);
                hoaDonNhapDAO.deleteHDNH(arrHoaDonNhap.get(position));
                arrHoaDonNhap.remove(position);
                notifyDataSetChanged();
            }
        });
        final HoaDonNhapHang hoaDonNhapHang = arrHoaDonNhap.get(position);
        if (hoaDonNhapHang != null) {
            //set len list
            holder.tvMaHoaDonNhap.setText("Mã: " + hoaDonNhapHang.maHoaDonNhap);
            holder.tvTenHoaDonNhap.setText("Tên HĐ: " + hoaDonNhapHang.tenHoaDonNhap);
            holder.tvNgayNhap.setText("Ngày: " + hoaDonNhapHang.ngayNhap);
            holder.tvSoLuongNhap.setText("Số Lượng: " + hoaDonNhapHang.soLuongNhap);
            holder.tvGiaNhap.setText("Giá: " + hoaDonNhapHang.soTienNhap+" VNĐ");
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
        return arrHoaDonNhap.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView ivIcon;
        TextView tvMaHoaDonNhap;
        TextView tvTenHoaDonNhap;
        ImageView ivDelete;
        TextView tvNgayNhap;
        TextView tvSoLuongNhap;
        TextView tvGiaNhap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);
            this.tvMaHoaDonNhap = itemView.findViewById(R.id.tvMaHDNhap);
            this.tvTenHoaDonNhap = itemView.findViewById(R.id.tvTenHDNhap);
            this.tvNgayNhap = itemView.findViewById(R.id.tvNgayNhap);
            this.ivDelete = itemView.findViewById(R.id.ivDelete);
            this.tvSoLuongNhap = itemView.findViewById(R.id.tvSoLuongNhap);
            this.tvGiaNhap = itemView.findViewById(R.id.tvGiaTienNhap);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null

            menu.add(Menu.NONE, R.id.context_edit_hoa_don_nhap,
                    Menu.NONE, "Sửa thông tin");
        }
    }

    @Override
    public long getItemId(int position) {

        return super.getItemId(position);
    }


}
