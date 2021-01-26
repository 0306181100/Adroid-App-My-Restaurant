package com.example.myrestaurant.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.OnClickDataForActivity;
import com.example.myrestaurant.R;
import com.example.myrestaurant.model.Ban;
import com.example.myrestaurant.model.LoaiMonAn;
import com.example.myrestaurant.model.MonAn;
import com.example.myrestaurant.model.NetworkUtils;
import com.example.myrestaurant.onClickItem;
import com.squareup.picasso.Picasso;

public class MonAnAdapter extends RecyclerView.Adapter<MonAnAdapter.MonAnViewHoler> {
    private LoaiMonAn loaiMonAN;
    private Context context;
    private Ban ban;
    private OnClickDataForActivity onclick;
    public MonAnAdapter(LoaiMonAn loaiMonAN, Ban ban, OnClickDataForActivity onclick) {
        this.loaiMonAN = loaiMonAN;
        this.ban=ban;
        this.onclick=onclick;
    }

    @NonNull
    @Override
    public MonAnViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View View = inflater.inflate(R.layout.monan_item, parent, false);
        return new MonAnViewHoler(View);
    }

    @Override
    public void onBindViewHolder(@NonNull final MonAnViewHoler holder, int position) {
        MonAn monAn=loaiMonAN.getMonAn().get(position);
        holder.Ten.setText(monAn.getTenMonAn());
        Integer gia=monAn.getDonGia();
        holder.Gia.setText(gia.toString());

        Picasso.with(context)
                .load(NetworkUtils.Anh+monAn.getHinhAnh())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.Anh);

        holder.setOnClickItem(new onClickItem() {
            @Override
            public void onClickListenerItem(View view, int position, boolean isLongClick) {
                if (!isLongClick){
                    MonAn monAn=loaiMonAN.getMonAn().get(position);
                    Toast.makeText(context,monAn.getTenMonAn(),Toast.LENGTH_LONG).show();
                    ban.addMonAnDaChon(monAn);
                    onclick.Data(putMonAn(monAn));
                }
            }
        });
    }

    Bundle putMonAn(MonAn monAn){
        Bundle bundle=new Bundle();
        bundle.putString("TenMonAn",monAn.getTenMonAn());
        bundle.putString("HinhAnh",monAn.getHinhAnh());
        bundle.putString("MoTa",monAn.getMoTa());
        bundle.putInt("DonGia",monAn.getDonGia());
        bundle.putInt("Id",monAn.getId());
        return bundle;
    }

    @Override
    public int getItemCount() {
        return loaiMonAN.getMonAn().size();
    }

    class MonAnViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private ImageView Anh;
        private TextView Ten;
        private TextView Gia;
        private onClickItem onClickItem;
        public MonAnViewHoler(@NonNull View itemView) {
            super(itemView);
            Anh=itemView.findViewById(R.id.Anh);
            Ten=itemView.findViewById(R.id.Ten);
            Gia=itemView.findViewById(R.id.Gia);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setOnClickItem(com.example.myrestaurant.onClickItem onClickItem) {
            this.onClickItem = onClickItem;
        }

        @Override
        public void onClick(View v) {
            onClickItem.onClickListenerItem(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            onClickItem.onClickListenerItem(v,getAdapterPosition(),true);
            return true;
        }
    }
}
