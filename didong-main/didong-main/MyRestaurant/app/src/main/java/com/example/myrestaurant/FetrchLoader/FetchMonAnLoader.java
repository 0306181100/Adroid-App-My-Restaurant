package com.example.myrestaurant.FetrchLoader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import com.example.myrestaurant.model.NetworkUtils;

public class FetchMonAnLoader extends AsyncTaskLoader<String> {
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public FetchMonAnLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getMonAn();
    }
}
