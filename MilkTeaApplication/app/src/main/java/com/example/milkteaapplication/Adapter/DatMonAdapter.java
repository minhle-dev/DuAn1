package com.example.milkteaapplication.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkteaapplication.Common.Common;
import com.example.milkteaapplication.DAO.BanAnDao;
import com.example.milkteaapplication.Model.BanAn;
import com.example.milkteaapplication.Model.DatMon;
import com.example.milkteaapplication.Model.SanPham;
import com.example.milkteaapplication.R;
import com.example.milkteaapplication.View.Fragment.FragmentChonMon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DatMonAdapter extends RecyclerView.Adapter<DatMonAdapter.ThemMonHolder> {

    private DatabaseReference mDatabase;
    private Context mContext;
    private List<SanPham> mList;
    public static List<DatMon> datMonList = new ArrayList<>();
    private OnItemClickListener mListener;
    private long price;
    String Table, TableId;
    FragmentChonMon fragmentChonMon;

    public interface OnItemClickListener {
        void onItemClick(DatMon mon, int count);
    }

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }


    public DatMonAdapter(Context mContext, List<SanPham> mList, FragmentChonMon fragmentChonMon) {
        this.mContext = mContext;
        this.mList = mList;
        this.fragmentChonMon = fragmentChonMon;

    }


    @NonNull

    @Override
    public ThemMonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_them_mon, parent, false);

        return new DatMonAdapter.ThemMonHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThemMonHolder holder, final int i) {
        final SanPham sanPham = mList.get(i);
        DecimalFormat formatPrice = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatPrice.applyPattern("###,###,###");
        Table = Common.TABLE;


        holder.tenSanPham.setText(sanPham.getTenSanPham());

        holder.giaSanPham.setText(formatPrice.format(Integer.valueOf((int) sanPham.giaTienSanPham)) + " vnđ");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.item_dialog_add);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                final Button btnMinus = dialog.findViewById(R.id.btn_minus);
                final Button btnAdd = dialog.findViewById(R.id.btn_add);
                Button btnClose = dialog.findViewById(R.id.btn_close);
                Button btnConfirm = dialog.findViewById(R.id.btn_confirm);
                final RelativeLayout layoutAdd = dialog.findViewById(R.id.layout_add);
                btnConfirm.setText("Chọn");
                final TextView tvFood = dialog.findViewById(R.id.them);
                tvFood.setText(mList.get(i).getTenSanPham());
                final EditText edtAmount = dialog.findViewById(R.id.edt_amount);
                price = Integer.valueOf(String.valueOf(mList.get(i).getGiaTienSanPham()));
                dialog.show();
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnMinus.setVisibility(View.VISIBLE);
                        if (edtAmount.getText().toString().equals("")) {
                            edtAmount.setText("1");
                        } else if (Integer.valueOf(edtAmount.getText().toString()) == 999) {
                            edtAmount.setText("1000");
                            btnAdd.setVisibility(View.INVISIBLE);
                        } else {
                            edtAmount.setText(String.valueOf(Integer.valueOf(edtAmount.getText().toString()) + 1));
                        }
                    }
                });
                btnMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnAdd.setVisibility(View.VISIBLE);
                        String stramount = edtAmount.getText().toString();
                        if (stramount.isEmpty() || stramount.equals("2")) {
                            edtAmount.setText("1");
                            btnMinus.setVisibility(View.INVISIBLE);
                        } else {
                            long amount = Integer.valueOf(edtAmount.getText().toString());
                            edtAmount.setText(String.valueOf(amount - 1));
                        }

                    }
                });
                edtAmount.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String amout = edtAmount.getText().toString();
                        if (amout.isEmpty() || amout.equals("1") || amout.equals("0")) {
                            btnMinus.setVisibility(View.INVISIBLE);
                            btnAdd.setVisibility(View.VISIBLE);
                        } else if (Integer.valueOf(amout) > 1000) {
                            edtAmount.setText("1000");
                            edtAmount.setSelection(edtAmount.getText().length());
                            btnAdd.setVisibility(View.INVISIBLE);
                            btnMinus.setVisibility(View.VISIBLE);
                        } else {
                            btnMinus.setVisibility(View.VISIBLE);
                            btnAdd.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String amount = edtAmount.getText().toString();

                        if (edtAmount.getText().toString().equals("") || edtAmount.getText().toString().equals("0")) {
                            edtAmount.setError("Nhập số phần");
                            edtAmount.requestFocus();
                        } else {
                            tvFood.setText("Đang chọn món ...");
                            layoutAdd.setVisibility(View.GONE);
                            dialog.dismiss();
                            //them mon
                            addOrdered(i, amount, dialog);
                        }
                    }
                });
            }

        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ThemMonHolder extends RecyclerView.ViewHolder {
        TextView tenSanPham, giaSanPham;


        public ThemMonHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tenSanPham = itemView.findViewById(R.id.tvSanPham);
            giaSanPham = itemView.findViewById(R.id.tvGiaSanPham);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {

                        }

                    }
                }
            });


        }

    }

    private void addOrdered(final int i, final String amount, final Dialog dialog) {

        String date = new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("kk:mm", Locale.getDefault()).format(new Date());

        final String tenMon = mList.get(i).tenSanPham;
        final String tongTien = String.valueOf((Integer.valueOf(amount) * price));
        final String tenBan = Common.TABLE;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference DaChon = database.getReference("MonDaChon").child(Common.TABLE);
        String key = DaChon.push().getKey();


        DatMon datMon = new DatMon();
        datMon.setId(key);
        datMon.setTenMon(tenMon);
        datMon.setGiaTien(String.valueOf(price));
        datMon.setSoLuong(amount);
        datMon.setDate(date);
        datMon.setTime(time);
        datMon.setTenBan(tenBan);
        datMon.setTongTien(tongTien);

        DaChon.child(key).setValue(datMon);

        Toast.makeText(mContext, "Đã thêm " + amount + " phần " + tenMon, Toast.LENGTH_SHORT).show();

        //cap nhat trang thai cua ban an sang co nguoi
        DatabaseReference doiTrangThai = database.getReference("BanAn").child(Common.TABLEID).child("trangThai");

        doiTrangThai.setValue("1");
    }


}
