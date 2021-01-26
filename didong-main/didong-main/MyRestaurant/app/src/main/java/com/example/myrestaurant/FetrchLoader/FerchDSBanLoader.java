package com.example.myrestaurant.FetrchLoader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myrestaurant.model.NetworkUtils;

public class FerchDSBanLoader extends AsyncTaskLoader<String> {
    public FerchDSBanLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.LayDanhSachBan();
    }
}
