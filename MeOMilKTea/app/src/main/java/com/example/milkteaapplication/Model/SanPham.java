package com.example.milkteaapplication.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class SanPham {
    public String maSanPham;
    public String maDanhMuc;
    public String tenDanhMuc;
    public String tenSanPham;
    public long giaTienSanPham;
    public String thumbUrl;

    public SanPham() {
    }

    public SanPham(String maSanPham, String maDanhMuc, String tenDanhMuc, String tenSanPham, long giaTienSanPham, String thumbUrl) {
        this.maSanPham = maSanPham;
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.tenSanPham = tenSanPham;
        this.giaTienSanPham = giaTienSanPham;
        this.thumbUrl = thumbUrl;
    }

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
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

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public long getGiaTienSanPham() {
        return giaTienSanPham;
    }

    public void setGiaTienSanPham(long giaTienSanPham) {
        this.giaTienSanPham = giaTienSanPham;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }


    @Override
    public String toString() {
        return "SanPham{" +
                "maSanPham='" + maSanPham + '\'' +
                ", maDanhMuc='" + maDanhMuc + '\'' +
                ", tenDanhMuc='" + tenDanhMuc + '\'' +
                ", tenSanPham='" + tenSanPham + '\'' +
                ", giaTienSanPham=" + giaTienSanPham +
                ", thumbUrl='" + thumbUrl + '\'' +
                '}';
    }
}


