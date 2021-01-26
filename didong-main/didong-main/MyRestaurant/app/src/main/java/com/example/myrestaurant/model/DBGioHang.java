package com.example.myrestaurant.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBGioHang extends SQLiteOpenHelper {
    private static final String DB = "GioHang.db";

    public DBGioHang(@Nullable Context context) {
        super(context, DB, null, 7);
    }

    public void Drop(){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("Delete from "+ MonAnDaChon.TABLE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MonAnDaChon.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MonAnDaChon.DROP_TABLE);
        onCreate(db);
    }

    public ArrayList<MonAnDaChon> layDanhSach()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                MonAnDaChon.COL_ID,
                MonAnDaChon.COL_IDMONAN,
                MonAnDaChon.COL_TENMONAN,
                MonAnDaChon.COL_GIA,
                MonAnDaChon.COL_HINHANH,
                MonAnDaChon.COL_MOTA,
                MonAnDaChon.COL_SOLUONG,
                MonAnDaChon.COL_TRANGTHAI
        };
        Cursor cursor = db.query(
                MonAnDaChon.TABLE_NAME,
                projection,
                null,
                null,
                null,null,null
        );
        ArrayList<MonAnDaChon> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            MonAn monAn=new MonAn();

                monAn.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_IDMONAN)));
                monAn.setTenMonAn(cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_TENMONAN)));
                monAn.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_HINHANH)));
                monAn.setDonGia(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_GIA)));
                monAn.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_MOTA)));
                MonAnDaChon monAnDaChon = new MonAnDaChon(monAn);
                monAnDaChon.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_ID)));
                monAnDaChon.setTrangThai(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_TRANGTHAI)));
                monAnDaChon.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_SOLUONG)));
                list.add(monAnDaChon);

        }
        cursor.close();
        return list;
    }
    public int xoaMonan(int id) {

        String selection = MonAnDaChon.COL_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id)};
        // Thực thi truy vấn
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(
                MonAnDaChon.TABLE_NAME,  // Tên bảng
                selection,          // Mệnh đề WHERE
                selectionArgs       // Cac gia tri trong menh de WHERE
        );

        return deletedRows;
    }

    public void xoaMonanTrung() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MonAnDaChon.TABLE_NAME,"ID not in (select MIN(ID) from giohang group by IDMONAN)",null);
    }

    public MonAnDaChon timMonAn(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        // SELECT *
        String[] projection = {
                MonAnDaChon.COL_ID,
                MonAnDaChon.COL_IDMONAN,
                MonAnDaChon.COL_TENMONAN,
                MonAnDaChon.COL_HINHANH,
                MonAnDaChon.COL_MOTA,
                MonAnDaChon.COL_GIA,
                MonAnDaChon.COL_SOLUONG,
                MonAnDaChon.COL_TRANGTHAI
        };

        // WHERE
        String selection = MonAnDaChon.COL_IDMONAN + " = ? and " + MonAnDaChon.COL_TRANGTHAI +" =?";
        String[] selectionArgs = { id+"","1"};
        // Thực thi truy vấn
        Cursor cursor = db.query(
                MonAnDaChon.TABLE_NAME,   // FROM danh_ba
                projection,          // SELECT  cột (null -> *)
                selection,           // WHERE
                selectionArgs,       // giá trị trong WHERE
                null,        // GROUP BY
                null,         // HAVING
                null            // ORDER
        );
        MonAnDaChon monan = new MonAnDaChon();
        while (cursor.moveToNext())
        {
            int ID = cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_IDMONAN));
            String ten = cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_TENMONAN));
            String hinhanh = cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_HINHANH));
            String mota = cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_MOTA));
            int dongia = cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_GIA));
            int soluong = cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_SOLUONG));
            monan = new MonAnDaChon(new MonAn(ID,ten,dongia,mota),soluong);
        }
        return monan;
    }

    public  long ThemSLMonAn(MonAnDaChon monAnDaChon ,Integer soluong)
    {
        long update=0;
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        if(isExist(monAnDaChon.getMonAn().getId())) {
            values.put(MonAnDaChon.COL_SOLUONG, timMonAn(monAnDaChon.getMonAn().getId()).getSoLuong() + soluong);
            String selection = MonAnDaChon.COL_IDMONAN + " =? and "+ MonAnDaChon.COL_TRANGTHAI + " =?";
            String[] selectionArgs = {Long.toString(monAnDaChon.getMonAn().getId()),"1"};
            update = db.update(MonAnDaChon.TABLE_NAME, values, selection, selectionArgs);
            if(monAnDaChon.getSoLuong() + soluong < 1)
            {
                xoaMonan(monAnDaChon.getMonAn().getId());
            }
        }
        else
        {
            monAnDaChon.setSoLuong(soluong);
            update=addMonAn(monAnDaChon);
        }
        return update;
    }
    public long demsl(int IDMONAN)
    {
        long kq =0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(MonAnDaChon.TABLE_NAME,null,MonAnDaChon.COL_IDMONAN +"="+ IDMONAN,null,null,null,null);
        cursor.moveToFirst();
        do{
            kq += cursor.getLong(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_SOLUONG));
        }while (cursor.moveToNext());
        return kq;
    }
    public long updatetrangthai(List<MonAnDaChon> monAnDaChons)
    {
        long update=0;
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        values.put(MonAnDaChon.COL_TRANGTHAI,false);
        update = db.update(MonAnDaChon.TABLE_NAME, values, null, null);
        List<Integer> dsid = new ArrayList<Integer>();
        for (MonAnDaChon monAn : monAnDaChons) {
            if (!dsid.contains(monAn.getMonAn().getId())) {
                dsid.add(monAn.getMonAn().getId());
            }
        }
        values=new ContentValues();
        for(int i=0;i<dsid.size();i++) {
            values.put(MonAnDaChon.COL_SOLUONG, demsl(dsid.get(i))+"");
            update= db.update(MonAnDaChon.TABLE_NAME, values, MonAnDaChon.COL_IDMONAN+"=?", new String[]{dsid.get(i)+""});

        }
        xoaMonanTrung();
        return  update;
    }
    public long addMonAn(MonAnDaChon monAnDaChon)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long kq=0;
        ContentValues values = new ContentValues();
        if(isExist(monAnDaChon.getMonAn().getId())){
                kq = ThemSLMonAn(monAnDaChon,monAnDaChon.getSoLuong());
        }else {
            values.put(MonAnDaChon.COL_IDMONAN,monAnDaChon.getMonAn().getId());
            values.put(MonAnDaChon.COL_TENMONAN,monAnDaChon.getMonAn().getTenMonAn());
            values.put(MonAnDaChon.COL_GIA,monAnDaChon.getMonAn().getDonGia());
            values.put(MonAnDaChon.COL_HINHANH,monAnDaChon.getMonAn().getHinhAnh());
            values.put(MonAnDaChon.COL_MOTA,monAnDaChon.getMonAn().getMoTa());
            values.put(MonAnDaChon.COL_SOLUONG,monAnDaChon.getSoLuong());
            values.put(MonAnDaChon.COL_TRANGTHAI,monAnDaChon.TrangThai());
            kq = db.insert(MonAnDaChon.TABLE_NAME,null,values);
        }
        return kq;
    }
    public boolean isExist(int idMA){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                MonAnDaChon.COL_IDMONAN,
                MonAnDaChon.COL_TRANGTHAI
        };
        String selection = MonAnDaChon.COL_IDMONAN+" =? and "+ MonAnDaChon.COL_TRANGTHAI+" =?";
        Cursor cursor = db.query(
                MonAnDaChon.TABLE_NAME,
                projection,
                selection,
                new String[]{idMA+"","1"},
                null,
                null,null,null
        );
        return cursor.getCount()>0;
    }

    public  long CapNhatSoLuong(MonAnDaChon monAnDaChon ,Integer soluong)
    {
        long update=0;
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        if(isExist(monAnDaChon.getMonAn().getId())) {
            values.put(MonAnDaChon.COL_SOLUONG,soluong);
            String selection = MonAnDaChon.COL_IDMONAN + " =? and "+ MonAnDaChon.COL_TRANGTHAI + " =?";
            String[] selectionArgs = {Long.toString(monAnDaChon.getMonAn().getId()),"1"};
            update = db.update(MonAnDaChon.TABLE_NAME, values, selection, selectionArgs);
            if(monAnDaChon.getSoLuong() + soluong < 1)
            {
                xoaMonan(monAnDaChon.getMonAn().getId());
            }
        }
        else
        {
            monAnDaChon.setSoLuong(soluong);
            update=addMonAn(monAnDaChon);
        }
        return update;
    }
    public ArrayList<MonAnDaChon> layDanhSachChuaThanhToan(){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                MonAnDaChon.COL_ID,
                MonAnDaChon.COL_IDMONAN,
                MonAnDaChon.COL_TENMONAN,
                MonAnDaChon.COL_GIA,
                MonAnDaChon.COL_HINHANH,
                MonAnDaChon.COL_MOTA,
                MonAnDaChon.COL_SOLUONG,
                MonAnDaChon.COL_TRANGTHAI
        };
        Cursor cursor = db.query(
                MonAnDaChon.TABLE_NAME,
                projection,
                MonAnDaChon.COL_TRANGTHAI+" = 1 ",
                null,
                null,null,null
        );
        ArrayList<MonAnDaChon> list = new ArrayList<>();
        while (cursor.moveToNext())
        {
            MonAn monAn=new MonAn();

            monAn.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_IDMONAN)));
            monAn.setTenMonAn(cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_TENMONAN)));
            monAn.setHinhAnh(cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_HINHANH)));
            monAn.setDonGia(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_GIA)));
            monAn.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_MOTA)));
            MonAnDaChon monAnDaChon = new MonAnDaChon(monAn);
            monAnDaChon.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_ID)));
            monAnDaChon.setTrangThai(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_TRANGTHAI)));
            monAnDaChon.setSoLuong(cursor.getInt(cursor.getColumnIndexOrThrow(MonAnDaChon.COL_SOLUONG)));
            list.add(monAnDaChon);
        }
        cursor.close();
        return list;
    }
}