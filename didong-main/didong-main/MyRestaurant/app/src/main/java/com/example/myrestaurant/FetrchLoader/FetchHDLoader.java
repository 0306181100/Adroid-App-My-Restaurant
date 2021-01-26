package com.example.myrestaurant.FetrchLoader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myrestaurant.model.NetworkUtils;

public class FetchHDLoader extends AsyncTaskLoader<String> {
    int idban;
    public FetchHDLoader(@NonNull Context context,int idban) {
        super(context);
        this.idban=idban;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.LayidHd(idban);
    }
}
