package com.example.myrestaurant.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.FetrchLoader.FetchCTHDLoader;
import com.example.myrestaurant.FetrchLoader.FetchHDLoader;
import com.example.myrestaurant.R;
import com.example.myrestaurant.adapter.MonAnDaChonAdapter;
import com.example.myrestaurant.model.Ban;
import com.example.myrestaurant.model.DBGioHang;
import com.example.myrestaurant.model.MonAnDaChon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>{
    Ban ban;
    static final int ID_HD = 1;
    RecyclerView rv;
    MonAnDaChonAdapter adapter;
    DBGioHang dbGioHang;
    Loader<String> loader;
    LoaderManager loaderManager;
    Button btnXacNhan,btnThanhToan;
    public long hd=0;
    SharedPreferences data;
    private int DaTa1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ban=new Ban();
        dbGioHang = new DBGioHang(this.getContext());
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final ArrayList<MonAnDaChon> mlist = dbGioHang.layDanhSach();
        ban.setDanhSachMonAnDaChons(mlist);
        rv=root.findViewById(R.id.rvDsMonAnDaChon);
        loaderManager = LoaderManager.getInstance(this);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter=new MonAnDaChonAdapter(ban);
        rv.setAdapter(adapter);
        btnXacNhan=root.findViewById(R.id.btn_XacNhan);
        btnThanhToan = root.findViewById(R.id.btn_ThanhToan);
        btnXacNhan.setEnabled(dbGioHang.layDanhSach().size()>0);;
        btnThanhToan.setEnabled(false);
        data=getContext().getSharedPreferences("com.example.myresstaurant.ui", Context.MODE_PRIVATE);
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbGioHang.updatetrangthai(ban.getDanhSachMonAnDaChons());
                ban.setDanhSachMonAnDaChons(dbGioHang.layDanhSach());
                MonAnDaChonAdapter.rest(getContext());
                adapter=new MonAnDaChonAdapter(ban);
                rv.setAdapter(adapter);
                btnXacNhan.setEnabled(false);
                btnThanhToan.setEnabled(true);
                TaoHoaDon();
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Tong Tien:"+ TongTien, Toast.LENGTH_SHORT).show();
                dbGioHang.Drop();
                SharedPreferences.Editor editor=data.edit();
                editor.putLong("hoadon",0);
                editor.apply();
                adapter=new MonAnDaChonAdapter(new Ban());
                rv.setAdapter(adapter);
                btnThanhToan.setEnabled(false);
            }
        });
        data = getContext().getSharedPreferences("ABC", 0);
        DaTa1 = data.getInt("Hoang1", 1);
        return root;
    }


    long TongTien;

    public void TaoHoaDon(){
        hd=data.getLong("hoadon",0);
        if(hd==0) {
            if (loaderManager.getLoader(ID_HD) == null) {
                loaderManager.initLoader(ID_HD, null, SlideshowFragment.this);
            } else {
                loaderManager.restartLoader(ID_HD, null, SlideshowFragment.this);
            }
        }
        else {
            loaderManager.restartLoader(ID_HD, null, SlideshowFragment.this);
        }
    }
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        if(hd == 0 ) {
            loader = new FetchHDLoader(this.getContext(),DaTa1);
        }
        else{
            loader = new FetchCTHDLoader(this.getContext(),hd);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            if(hd==0){
                hd = jsonObject.getLong("result");
                SharedPreferences.Editor editor=this.data.edit();
                editor.putLong("hoadon",hd);
                editor.apply();
                loaderManager.restartLoader(ID_HD, null, SlideshowFragment.this);
            }
            else{
                TongTien= jsonObject.getLong("Total");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}