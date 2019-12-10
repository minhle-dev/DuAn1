package com.example.milkteaapplication.Model;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class BanAn {
     public String tenBan;
     public String trangThai;
     public String tongTien;

    public BanAn() {
    }

    public BanAn(String tenBan, String trangThai, String tongTien) {
        this.tenBan = tenBan;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
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

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }
}
