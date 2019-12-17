package com.example.milkteaapplication.Model;

import java.util.List;

public class HoaDon {
    String  maHoaDon, date, time,  tenBan, tenUser;
    long thanhTien;
    List<DatMon> datMonList;

    public List<DatMon> getDatMonList() {
        return datMonList;
    }

    public void setDatMonList(List<DatMon> datMonList) {
        this.datMonList = datMonList;
    }

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String date, String time,long thanhTien, String tenBan, String tenUser) {
        this.maHoaDon = maHoaDon;
        this.date = date;
        this.time = time;
        this.tenBan = tenBan;
        this.thanhTien = thanhTien;
        this.tenUser = tenUser;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(long thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTenBan() {
        return tenBan;
    }

    public void setTenBan(String tenBan) {
        this.tenBan = tenBan;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }
}
