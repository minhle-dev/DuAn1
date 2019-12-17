package com.example.milkteaapplication.Model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class HoaDonNhapHang {
    public String maHoaDonNhap;
    public String tenHoaDonNhap;
    public String ngayNhap;
    public long soLuongNhap;
    public long soTienNhap;

    public HoaDonNhapHang() {
    }

    public HoaDonNhapHang(String maHoaDonNhap, String tenHoaDonNhap, String ngayNhap,long soLuongNhap, long soTienNhap) {
        this.maHoaDonNhap = maHoaDonNhap;
        this.tenHoaDonNhap = tenHoaDonNhap;
        this.ngayNhap = ngayNhap;
        this.soLuongNhap = soLuongNhap;
        this.soTienNhap = soTienNhap;
    }
}
