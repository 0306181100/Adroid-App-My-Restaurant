package com.example.myrestaurant.FetrchLoader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.myrestaurant.model.NetworkUtils;

public class FetchSearchLoader extends AsyncTaskLoader {
    private  String key;

    public FetchSearchLoader(@NonNull Context context,String key) {
        super(context);
        this.key=key;
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        return NetworkUtils.timMonAn(key);
    }
}
