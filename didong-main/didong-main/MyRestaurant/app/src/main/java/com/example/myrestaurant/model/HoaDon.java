package com.example.myrestaurant.model;

import java.util.Date;
import java.util.LinkedList;

public class HoaDon {
    private int Id;
    private Date ngayTao;
    private LinkedList<ChiTietHoaDon> chiTietHoaĐon;
    public LinkedList<ChiTietHoaDon> getChiTietHoaĐon() {
        return chiTietHoaĐon;
    }
    public void setChiTietHoaĐon(LinkedList<ChiTietHoaDon> newChiTietHoaĐon) {
        chiTietHoaĐon=newChiTietHoaĐon;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }
}
