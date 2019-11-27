package com.example.milkteaapplication.Model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HoaDonNhapHang {
    public String maHoaDonNhap;
    public String tenHoaDonNhap;
    public String ngayNhap;
    public String soLuongNhap;
    public String soTienNhap;

    public HoaDonNhapHang() {
    }

    public HoaDonNhapHang(String maHoaDonNhap, String tenHoaDonNhap, String ngayNhap,String soLuongNhap, String soTienNhap) {
        this.maHoaDonNhap = maHoaDonNhap;
        this.tenHoaDonNhap = tenHoaDonNhap;
        this.ngayNhap = ngayNhap;
        this.soLuongNhap = soLuongNhap;
        this.soTienNhap = soTienNhap;
    }
}
