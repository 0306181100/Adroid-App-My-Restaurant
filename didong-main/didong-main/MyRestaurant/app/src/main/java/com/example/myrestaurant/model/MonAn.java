package com.example.myrestaurant.model;

public class MonAn {
    private int id;
    private String tenMonAn;
    private int donGia;
    private String moTa;
    private String hinhAnh;

    public MonAn(){

    }

    public MonAn(int id, String tenMonAn, int donGia, String moTa) {
        this.setId(id);
        this.setTenMonAn(tenMonAn);
        this.setDonGia(donGia);
        this.setMoTa(moTa);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
