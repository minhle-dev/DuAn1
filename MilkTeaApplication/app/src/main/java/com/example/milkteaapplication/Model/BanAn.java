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

}
