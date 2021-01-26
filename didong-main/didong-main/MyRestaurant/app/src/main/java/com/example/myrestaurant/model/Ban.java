package com.example.myrestaurant.model;

import java.util.ArrayList;
import java.util.List;

public class Ban {
    private int Id;
    private int SoBan;

    private List<MonAnDaChon> danhSachMonAnDaChons;

    public int getId() {
        return Id;
    }

    public int getSoBan() {
        return SoBan;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setSoBan(int soBan) {
        SoBan = soBan;
    }

   public Ban(){
        danhSachMonAnDaChons=new ArrayList<>();
   }
    public List<MonAnDaChon> getDanhSachMonAnDaChons() {
        return danhSachMonAnDaChons;
    }

    public void setDanhSachMonAnDaChons(List<MonAnDaChon> danhSachMonAnDaChons) {
        this.danhSachMonAnDaChons = danhSachMonAnDaChons;
    }

    public void addMonAnDaChon(MonAn monAn) {
        this.danhSachMonAnDaChons.add(new MonAnDaChon(monAn));
    }
}
