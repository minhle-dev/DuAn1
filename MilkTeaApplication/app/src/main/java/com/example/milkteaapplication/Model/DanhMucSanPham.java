package com.example.milkteaapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class DanhMucSanPham {
    public String maDanhMuc;
    public String tenDanhMuc;



    public DanhMucSanPham() {
    }

    public DanhMucSanPham(String maDanhMuc, String tenDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
