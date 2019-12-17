package com.example.milkteaapplication.Model;

public class DatMon {
    private String id, tenMon, soLuong,  giaTien, tongTien, tenBan;

    public DatMon() {
    }

    public DatMon(String id, String tenMon, String soLuong, String giaTien, String tongTien, String tenBan) {
        this.id = id;
        this.tenMon = tenMon;
        this.soLuong = soLuong;

        this.giaTien = giaTien;
        this.tongTien = tongTien;
        this.tenBan = tenBan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }
}
