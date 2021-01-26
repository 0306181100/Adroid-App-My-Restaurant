package com.example.myrestaurant.model;

public class ThongTinBan {
    private int IdBan;
    private String TenBan;

    public int getIdBan() {
        return IdBan;
    }

    public void setIdBan(int idBan) {
        IdBan = idBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }

    public ThongTinBan(int id, String ten){
        IdBan = id;
        TenBan = ten;
    }
}
