package com.example.myrestaurant.model;

import java.util.LinkedList;

public class LoaiMonAn {
    private int id;
    private String tenLoai;
    private LinkedList<MonAn> monAn;

    public LoaiMonAn(int id, String tenLoai) {
        this.setId(id);
        this.setTenLoai(tenLoai);
        this.setMonAn(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public LinkedList<MonAn> getMonAn() {
        return monAn;
    }

    public void setMonAn(LinkedList<MonAn> monAn) {
        this.monAn = monAn;
    }
}
