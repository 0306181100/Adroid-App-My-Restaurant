package com.example.myrestaurant.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.model.ThongTinBan;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableHolder>{
    private LinkedList<ThongTinBan> DaTa;
    private Context context;
    public TableAdapter(LinkedList<ThongTinBan> data, Context context){
        DaTa = data;
        this.context = context;
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table,parent,false);
        return new TableHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, final int position) {
        ThongTinBan thongTinBan = DaTa.get(position);

        holder.TenBan.setText(thongTinBan.getTenBan());
        holder.IdBan.setText(String.valueOf(thongTinBan.getIdBan()));
    }

    @Override
    public int getItemCount() {
        return DaTa.size();
    }


    public class TableHolder extends RecyclerView.ViewHolder {
        private TextView TenBan,IdBan;
        private ConstraintLayout CTRL;
        public SharedPreferences mPreferences;
        public TableHolder(@NonNull View itemView) {
            super(itemView);
            TenBan = itemView.findViewById(R.id.txtTenBan);
            IdBan =itemView.findViewById(R.id.idBan);
            CTRL = itemView.findViewById(R.id.ctrl);

            mPreferences = context.getSharedPreferences("ABC",0);

            CTRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //chonban.DuLieuBan = TenBan.getText().toString();
                    //chonban.txtNhan.setText(TenBan.getText().toString());
                    //BDC = new BanDaChon(1,TenBan.getText().toString());
                    SharedPreferences.Editor editor = mPreferences.edit();
                    editor.putString("Hoang", TenBan.getText().toString());
                    editor.putString("TenBan", IdBan.getText().toString());
                    editor.putInt("Hoang1", Integer.parseInt(IdBan.getText().toString()));
                    editor.commit();
                    Navigation.findNavController(v).navigate(R.id.action_chonBanFragment_to_nav_home);
                    Snackbar.make(v,mPreferences.getString("Hoang","Không có"), Snackbar.LENGTH_LONG).show();
                }

            });
        }

    }
}
