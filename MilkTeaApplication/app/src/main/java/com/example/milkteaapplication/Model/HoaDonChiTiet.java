package com.example.milkteaapplication.Model;

public class HoaDonChiTiet {
    String tenMon, soLuong;
    String tongTien;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String tenMon, String soLuong, String tongTien) {
        this.tenMon = tenMon;
        this.soLuong = soLuong;
        this.tongTien = tongTien;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }
}
