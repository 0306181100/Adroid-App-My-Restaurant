package com.example.myrestaurant.model;

import java.util.LinkedList;

public class MonAnDaChon {
    private MonAn monAn;
    private int SoLuong;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int TrangThai;

    public int TrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }
    public static final String COL_ID = "ID";
    public static final String COL_IDMONAN = "IDMONAN";
    public static final String COL_SOLUONG = "SOLUONG";
    public static final String COL_TENMONAN = "TENMONAN";
    public static final String COL_HINHANH = "HINHANH";
    public static final String COL_GIA = "GIA";
    public static final String COL_MOTA = "MOTA";
    public static final String COL_TRANGTHAI = "TRANGTHAI";
    public static final String TABLE_NAME = "giohang";

    public static final String CREATE_TABLE = "CREATE table "+ TABLE_NAME + " ( "
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_IDMONAN + " INTEGER , "
            + COL_TENMONAN + " TEXT, "
            + COL_HINHANH + " TEXT, "
            + COL_GIA + " INTEGER, "
            + COL_MOTA + " TEXT, "
            + COL_TRANGTHAI + " BIT default(1), "
            + COL_SOLUONG + " INTEGER)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public MonAnDaChon(){}
    public MonAnDaChon(MonAn monAn) {
        this.monAn=monAn;
        this.id=0;
        SoLuong=1;
        this.TrangThai = 1;
    }
    public MonAnDaChon(MonAn monAn,int soluong) {
        this.monAn=monAn;
        this.SoLuong=soluong;
        this.TrangThai = 1;
        this.id=0;
    }
    public MonAnDaChon(MonAn monAn,int soluong,int trangThai) {
        this.monAn=monAn;
        this.id=0;
        this.SoLuong=soluong;
        this.TrangThai = trangThai;
    }
    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }
}
