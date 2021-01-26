package com.example.myrestaurant.FetrchLoader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myrestaurant.model.NetworkUtils;

public class FetchDangNhapLoader extends AsyncTaskLoader<String> {
    String taikhoan,matkhau;
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public FetchDangNhapLoader(@NonNull Context context,String taikhoan,String matkhau) {
        super(context);
        this.taikhoan =taikhoan;
        this.matkhau = matkhau;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.DangNhap(this.taikhoan,this.matkhau);
    }
}
