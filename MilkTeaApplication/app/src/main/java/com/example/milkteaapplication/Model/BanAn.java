package com.example.milkteaapplication.Model;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BanAn {
     public String tenBan;
     public String trangThai;
     public String maBan;

    public BanAn() {
    }

    public BanAn(String tenBan, String trangThai, String maBan) {
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.maBan = maBan;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaBan() {
        return maBan;
    }

    public void setMaBan(String maBan) {
        this.maBan = maBan;
    }
}
