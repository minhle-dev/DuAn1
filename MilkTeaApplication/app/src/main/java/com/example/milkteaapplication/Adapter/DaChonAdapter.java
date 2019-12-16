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

public class DaChonAdapter extends RecyclerView.Adapter<DaChonAdapter.DaChonHolder> {

    private DatabaseReference mDatabase;
    private Context mContext;
    private List<DatMon> datMonList;
    private OnItemClickListener mListener;

    String Table, TableId;
    FragmentDaChon fragmentDaChon;

    public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public DaChonAdapter(Context mContext, List<DatMon> datMonList, FragmentDaChon fragmentDaChon) {
        this.mContext = mContext;
        this.datMonList = datMonList;
        this.fragmentDaChon = fragmentDaChon;

    }


    @NonNull

    @Override
    public DaChonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_da_chon, parent, false);

        return new DaChonAdapter.DaChonHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DaChonHolder holder, final int i) {

        DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatPrice.applyPattern("###,###,###");
        Table = Common.TABLE;


        String tenMon = datMonList.get(i).getTenMon();
        String soLuong = datMonList.get(i).getSoLuong();
        String tongTienMon = datMonList.get(i).getTongTien();
        holder.tenSanPham.setText(" "+tenMon);
        holder.tvSoLuong.setText(soLuong);
        holder.tvTongTienMon.setText(formatPrice.format(Integer.valueOf(tongTienMon))+" vnđ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // khởi tạo AlertDialog từ đối tượng Builder. tham số truyền vào ở đây là context.
                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
                // set Message là phương thức thiết lập câu thông báo
                builder.setMessage("Bạn muốn xóa sản phẩm này ?")
                        // positiveButton là nút thuận : đặt là OK
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatMonDAO datMonDAO = new DatMonDAO(mContext, fragmentDaChon);
                                datMonDAO.xoaMon(datMonList.get(i));
                                fragmentDaChon.capnhatRV();
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
            }
        });

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
