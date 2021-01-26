package com.example.myrestaurant.FetrchLoader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myrestaurant.model.DBGioHang;
import com.example.myrestaurant.model.MonAnDaChon;
import com.example.myrestaurant.model.NetworkUtils;

public class FetchCTHDLoader extends AsyncTaskLoader<String> {
    private long id;
    private DBGioHang monAnDaChon;
    public FetchCTHDLoader(@NonNull Context context, long id) {
        super(context);
        this.id = id;
        this.monAnDaChon = new DBGioHang(context);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
     return NetworkUtils.ThemHoaDon(id,monAnDaChon);
    }

}
