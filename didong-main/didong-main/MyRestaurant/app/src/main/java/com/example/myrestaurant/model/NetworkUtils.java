package com.example.myrestaurant.model;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NetworkUtils {
    public static final String http="http://10.0.2.2:8000/";
    public static final String LoaiMonAnAPI=http+"api/LoaiMonan";
    public static final String POSTAPI=http+"api/hoadon";
    public static final String POSTcthoadonAPI=http+"api/cthoadon";
    public static final String DangNhap=http+"api/DangNhap";
    public static final String DanhSachBan = http+"api/Ban";
    public static final String TimKiem = http+"api/timkiem";

    public static final String Anh =http+"assets-2/img/";
    public static String getMonAn() {
        Uri builtURI = Uri.parse(LoaiMonAnAPI).buildUpon().build();
        try {
                URL requestURL = new URL(builtURI.toString());
                String a =callAPI(requestURL, "GET");
                return a;
        } catch (MalformedURLException e) {
                return null;
        }
    }

    public static String ThemHoaDon(long id,DBGioHang db){
        String result=null;
        for(MonAnDaChon monAnDaChon: db.layDanhSach()){
            result = themctHd(id,monAnDaChon);
            Log.d("HIHI",result);
        }
        return result;
    }

    public static String themctHd (long idhd,MonAnDaChon monAnDaChon) {
        Uri builtURI = Uri.parse(POSTcthoadonAPI).buildUpon()
                .appendQueryParameter("idhd",idhd+"")
                .appendQueryParameter("id_mon_an",monAnDaChon.getMonAn().getId()+"")
                .appendQueryParameter("so_luong",monAnDaChon.getSoLuong()+"")
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            String hoadon =callAPI(requestURL, "POST");
            Log.e("cthd",hoadon);
            return hoadon;
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String LayidHd (int idban) {
        Uri builtURI = Uri.parse(POSTAPI).buildUpon()
                .appendQueryParameter("id_ban",idban+"")
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            String hoadon =callAPI(requestURL, "POST");
            //Log.d("HIHI",hoadon);
            return hoadon;
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String DangNhap(String taikhoan,String matkhau) {
        Uri builtURI = Uri.parse(DangNhap).buildUpon()
                .appendQueryParameter("tai_khoan", taikhoan)
                .appendQueryParameter("password", matkhau)
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            String a =callAPI(requestURL, "POST");
            return a;
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String LayDanhSachBan(){
        Uri builtURI = Uri.parse(DanhSachBan).buildUpon()
                .build();
        try {
            URL requestURL = new URL(builtURI.toString());
            String a =callAPI(requestURL, "GET");
            return a;
        } catch (MalformedURLException e) {
            return null;
        }
    }
    public static LinkedList<MonAn> timMonAn(String key) {
        Uri built =Uri.parse(TimKiem).buildUpon().appendQueryParameter("key",key).build();
        LinkedList<MonAn> monAns=new LinkedList<>();
        try {
            URL request=new URL(built.toString());
            String kq=callAPI(request,"GET");
            JSONObject oject= new JSONObject(kq);
            JSONArray dulieu = oject.getJSONArray("dulieu");
            for(int i=0;i<dulieu.length();i++){
                JSONObject MonAN = dulieu.getJSONObject(i);
                MonAn monAn = new MonAn((int)MonAN.get("id"),
                        (String)MonAN.get("ten_mon_an"),
                        (int)MonAN.get("gia"),
                        (String)MonAN.get("mo_ta"));
                monAn.setHinhAnh((String)MonAN.get("hinh_anh"));
                monAns.add(monAn);
            }
        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDsBan() {
        return null;
    }


    public static String callAPI(URL requestURL, String method) {
        HttpURLConnection urlConnection = null;
        String result = "";
        try {
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.connect();
            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            result = convertISToString(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result;
    }
    public static String convertISToString(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
                if (builder.length() == 0) {
                    return null;
                }
            }
        } catch (IOException e) {
            return null;
        }
        return builder.toString();
    }
}
