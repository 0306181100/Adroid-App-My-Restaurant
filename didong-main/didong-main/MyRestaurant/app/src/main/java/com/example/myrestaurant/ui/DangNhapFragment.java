package com.example.myrestaurant.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.navigation.Navigation;

import com.example.myrestaurant.FetrchLoader.FetchDangNhapLoader;
import com.example.myrestaurant.R;

import org.json.JSONException;
import org.json.JSONObject;

public class DangNhapFragment extends Fragment implements LoaderManager.LoaderCallbacks<String>{
    LoaderManager loaderManager;
    static final int WEATHER_LOADER_ID = 1000;
    EditText editText_taikhoan,editText_matkhau;
    Button button_dangnhap;
    String taikhoan,matkhau;
    Loader<String> loader;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_dangnhap, container, false);
        this.editText_matkhau = root.findViewById(R.id.edit_password);
        this.editText_taikhoan = root.findViewById(R.id.edit_taikhoan);
        this.button_dangnhap = root.findViewById(R.id.button_dangnhap);
        loaderManager = LoaderManager.getInstance(this);
        button_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taikhoan = editText_taikhoan.getText().toString();
                matkhau = editText_matkhau.getText().toString();
                if (loaderManager.getLoader(WEATHER_LOADER_ID) == null) {
                    loaderManager.initLoader(WEATHER_LOADER_ID,null, DangNhapFragment.this);
                } else {
                    loaderManager.restartLoader(WEATHER_LOADER_ID, null, DangNhapFragment.this);
                }
            }
        });

        return root;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        loader = new FetchDangNhapLoader(this.getContext(),taikhoan,matkhau);
        return loader;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            Log.d("HiHi",data);
            JSONObject jsonObject = new JSONObject(data);
            boolean result = jsonObject.getBoolean("result");
            Log.d("HiHi",String.valueOf(result));
            if(result == true ){
                Chuyen(root);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void Chuyen(View view){
        Navigation.findNavController(view).navigate(R.id.action_nav_dangnhap_to_chonBanFragment);
    }

}