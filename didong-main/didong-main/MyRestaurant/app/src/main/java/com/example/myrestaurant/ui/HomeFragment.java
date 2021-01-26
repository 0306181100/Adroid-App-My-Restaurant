package com.example.myrestaurant.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.FetrchLoader.FetchMonAnLoader;
import com.example.myrestaurant.OnClickDataForActivity;
import com.example.myrestaurant.adapter.MonAnAdapter;
import com.example.myrestaurant.R;
import com.example.myrestaurant.model.Ban;
import com.example.myrestaurant.model.LoaiMonAn;
import com.example.myrestaurant.model.MonAn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {
    private RecyclerView rv;
    private MonAnAdapter menuAdapter;
    OnClickDataForActivity onClickDataForActivity;
    LinkedList<LoaiMonAn> loaiMonAns;
    LoaiMonAn loaiMonAn;
    static final int LIST_MA = 1000; //danh sach mon an
    List<String> tenLoai=new ArrayList<>();
    LoaderManager loaderManager;
    Spinner s;
    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onClickDataForActivity=(OnClickDataForActivity) context;
        }catch (Exception e){

        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        rv=root.findViewById(R.id.rv_mon_an);
        s = root.findViewById(R.id.spinner);

        loaderManager = LoaderManager.getInstance(this);

        if (loaderManager.getLoader(LIST_MA) == null) {
            loaderManager.initLoader(LIST_MA,null, this);
        } else {
            loaderManager.restartLoader(LIST_MA, null, this);
        }


        return root;
    }

    private LinkedList<LoaiMonAn> KhoiTao(List<String> arr) {
        LinkedList<LoaiMonAn> loaiMonAns=new LinkedList<>();
        loaiMonAns.add(new LoaiMonAn(0,"Món chính"));
        loaiMonAns.add(new LoaiMonAn(1,"Món phụ"));
        loaiMonAns.add(new LoaiMonAn(2,"Thức uống"));

        for (int i=0;i<3;i++) {
            LinkedList<MonAn> monAns=new LinkedList<>();
            arr.add(loaiMonAns.get(i).getTenLoai());
            for (int j = 0; j < 10; j++) {
                MonAn monAn = new MonAn(j, loaiMonAns.get(i).getTenLoai()+" " + j, 20000, "");
                monAns.add(monAn);
            }
            loaiMonAns.get(i).setMonAn(monAns);
        }
        return loaiMonAns;
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new FetchMonAnLoader(this.getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            loaiMonAns=new LinkedList<>();
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("dulieu");

            Log.d("Json","1");
            for (int i = 0;i < itemsArray.length();i++) {
                JSONObject LoaiMonAn = itemsArray.getJSONObject(i);
                JSONArray MonAns = LoaiMonAn.getJSONArray("MonAns");
               try {
                    loaiMonAn=new LoaiMonAn(Integer.parseInt(LoaiMonAn.get("id").toString()),LoaiMonAn.getString("ten_loai"));
                    LinkedList<MonAn> monAns = new LinkedList<MonAn>();

                    for (int j = 0;j < MonAns.length();j++) {
                        try {
                            JSONObject MonAN = MonAns.getJSONObject(j);
                            MonAn monAn = new MonAn((int)MonAN.get("id"),
                                    (String)MonAN.get("ten_mon_an"),
                                    (int)MonAN.get("gia"),
                                    (String)MonAN.get("mo_ta"));
                            monAn.setHinhAnh((String)MonAN.get("hinh_anh"));
                            monAns.add(monAn);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    loaiMonAn.setMonAn(monAns);
                    tenLoai.add(loaiMonAn.getTenLoai());
                    loaiMonAns.add(loaiMonAn);
               }catch (Exception e){
                    Log.e("e",e.toString());
                }
            }
            ArrayAdapter adapter = new ArrayAdapter(this.getContext(),R.layout.support_simple_spinner_dropdown_item,tenLoai);

            rv.setLayoutManager(new GridLayoutManager(this.getContext(),3,GridLayoutManager.VERTICAL,false));
            s.setAdapter(adapter);
            s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    menuAdapter=new MonAnAdapter(loaiMonAns.get(position),new Ban(),onClickDataForActivity);
                    rv.setAdapter(menuAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    menuAdapter=new MonAnAdapter(loaiMonAns.get(0),new Ban(),onClickDataForActivity);
                    rv.setAdapter(menuAdapter);
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}