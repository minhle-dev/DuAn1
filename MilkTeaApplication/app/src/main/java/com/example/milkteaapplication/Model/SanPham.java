package com.example.milkteaapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class SanPham {
    public String maSanPham;
    public String tenSanPham;
    public long giaTienSanPham;
    public String hinhSanPham;


    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, long giaTienSanPham, String hinhSanPham) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.giaTienSanPham = giaTienSanPham;
        this.hinhSanPham = hinhSanPham;
    }
}
