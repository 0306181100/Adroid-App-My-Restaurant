package com.example.myrestaurant.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.FetrchLoader.FerchDSBanLoader;
import com.example.myrestaurant.R;
import com.example.myrestaurant.adapter.TableAdapter;
import com.example.myrestaurant.model.ThongTinBan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChonBanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChonBanFragment extends Fragment implements LoaderManager.LoaderCallbacks<String> {

    RecyclerView recyclerView;
    TableAdapter tableAdapter;
    LinkedList<ThongTinBan> List;

    static final int WEATHER_BAN_ID = 1001; //thêm
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChonBanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rec);
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChonBanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChonBanFragment newInstance(String param1, String param2) {
        ChonBanFragment fragment = new ChonBanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LoaderManager loaderManager = LoaderManager.getInstance(this);

        if (loaderManager.getLoader(WEATHER_BAN_ID) == null) {
            loaderManager.initLoader(WEATHER_BAN_ID,null, this);
        } else {
            loaderManager.restartLoader(WEATHER_BAN_ID, null, this);
        }
        return inflater.inflate(R.layout.fragment_chon_ban, container, false);
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new FerchDSBanLoader(this.getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        List = new LinkedList<ThongTinBan>();
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray main = jsonObject.getJSONArray("dulieu");

            //Log.d("HAHA",jsonObject.toString());
            for(int i=0;i<main.length();i++) {
                ThongTinBan TT = new ThongTinBan(main.getJSONObject(i).getInt("id"),
                        String.valueOf(main.getJSONObject(i).getInt("so")));
                List.add(TT);
            }
            tableAdapter = new TableAdapter(this.List, this.getContext());
            recyclerView.setAdapter(tableAdapter);

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        } catch (JSONException e) {
            Log.i("HAHA",e+"");

        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

}