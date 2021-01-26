package com.example.myrestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myrestaurant.model.DBGioHang;
import com.example.myrestaurant.model.MonAn;
import com.example.myrestaurant.model.MonAnDaChon;
import com.example.myrestaurant.model.NetworkUtils;
import com.squareup.picasso.Picasso;

public class ChonMonActivity extends AppCompatActivity {

    MonAn monAn;
    private TextView txtName;
    private TextView txtGia;
    private TextView txtMoTa;
    private EditText txtSoLuong;
    private ImageView imgAnh;
    DBGioHang dbGioHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon);
        txtGia=findViewById(R.id.txtGia);
        txtName=findViewById(R.id.txtTenMon2);
        txtMoTa=findViewById(R.id.txtMoTa);
        txtSoLuong=findViewById(R.id.editTextNumber);
        imgAnh=findViewById(R.id.imgAnh2);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        dbGioHang = new DBGioHang(this);
        if (bundle != null) {
            monAn=getMonAn(bundle);
            loadDuLieu(monAn);
        }else{

        }
    }

    private void loadDuLieu(MonAn monAn) {
        Picasso.with(this)
                .load(NetworkUtils.Anh+monAn.getHinhAnh())
                .placeholder(R.mipmap.ic_launcher)
                .into(imgAnh);
        txtName.setText("Tên món: "+monAn.getTenMonAn());
        txtGia.setText("Đơn giá: "+monAn.getDonGia());
        txtMoTa.setText("Mô tả: "+monAn.getMoTa());
    }

    MonAn getMonAn(Bundle bundle){
        MonAn monAn=new MonAn();
        monAn.setTenMonAn(bundle.getString("TenMonAn"));
        monAn.setHinhAnh(bundle.getString("HinhAnh"));
        monAn.setMoTa(bundle.getString("MoTa"));
        monAn.setDonGia(bundle.getInt("DonGia"));
        monAn.setId(bundle.getInt("Id"));
        return monAn;
    }

    public void Themmonan(View view) {
        try{
        MonAnDaChon monAnDaChon = new MonAnDaChon(monAn,Integer.parseInt(txtSoLuong.getText().toString()));
        long kq = dbGioHang.addMonAn(monAnDaChon);
        if(kq>0)
        {
            Toast.makeText(this, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
        }
        }catch (Exception e){Toast.makeText(this, "Thêm món ăn thất bại", Toast.LENGTH_SHORT).show();}
    }
}