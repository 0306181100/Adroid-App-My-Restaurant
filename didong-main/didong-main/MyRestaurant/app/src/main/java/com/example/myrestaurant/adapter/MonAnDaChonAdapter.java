package com.example.myrestaurant.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrestaurant.R;
import com.example.myrestaurant.model.Ban;
import com.example.myrestaurant.model.DBGioHang;
import com.example.myrestaurant.model.MonAnDaChon;
import com.example.myrestaurant.model.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MonAnDaChonAdapter extends RecyclerView.Adapter<MonAnDaChonAdapter.MonAnDaChonViewHolder> {
    Ban ban;

    private Context context;
    DBGioHang dbGioHang;
    public MonAnDaChonAdapter(Ban ban) {
        this.ban = ban;
    }

    @NonNull
    @Override
    public MonAnDaChonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        dbGioHang = new DBGioHang(context);
        View View = inflater.inflate(R.layout.item_mon_da_chon, parent, false);
        return new MonAnDaChonViewHolder(View);
    }

    @Override
    public void onBindViewHolder(@NonNull final MonAnDaChonViewHolder holder, final int position) {
        final MonAnDaChon monAn=ban.getDanhSachMonAnDaChons().get(position);

        Picasso.with(context)
                .load(NetworkUtils.Anh+monAn.getMonAn().getHinhAnh())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.anh);
        holder.txtTen.setText(monAn.getMonAn().getTenMonAn());
        holder.txtGia.setText(monAn.getMonAn().getDonGia()+"");
        holder.txtSoLuong.setText(monAn.getSoLuong()+"");
        holder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbGioHang = new DBGioHang(context);
                dbGioHang.ThemSLMonAn(monAn,1);
                monAn.setSoLuong(dbGioHang.timMonAn(monAn.getMonAn().getId()).getSoLuong());
                holder.txtSoLuong.setText(monAn.getSoLuong()+"");
                holder.btntru.setEnabled(true);
            }
        });
        holder.btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });
        holder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbGioHang.ThemSLMonAn(monAn,-1);
                monAn.setSoLuong(monAn.getSoLuong()-1);
                holder.txtSoLuong.setText(monAn.getSoLuong()+"");
                if(monAn.getSoLuong() < 1)
                {
                    dbGioHang.xoaMonan(ban.getDanhSachMonAnDaChons().get(position).getMonAn().getId());
                    holder.btntru.setEnabled(false);
                }
            }
        });
        Log.d("demo",monAn.TrangThai()+"");
        if(monAn.TrangThai()==0)
        {

            holder.btncong.setEnabled(false);
            holder.btnDel.setEnabled(false);
            holder.btntru.setEnabled(false);
        }

    }

    public void capnhat()
    {

    }
    public static void rest(Context context)
    {


    }
    private void removeItem(int position) {
        int kq = dbGioHang.xoaMonan(ban.getDanhSachMonAnDaChons().get(position).getId());
        if(kq>0)
        {
            Toast.makeText(context, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(context, "Xóa món ăn thất bại", Toast.LENGTH_SHORT).show();
        ban.getDanhSachMonAnDaChons().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, ban.getDanhSachMonAnDaChons().size());

    }
    @Override
    public int getItemCount() {
        return ban.getDanhSachMonAnDaChons().size();
    }

    class MonAnDaChonViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTen;
        private TextView txtGia;
        private TextView txtSoLuong;
        private Button btntru,btncong,btnDel;
        private ImageView anh;
        public MonAnDaChonViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGia=itemView.findViewById(R.id.txtDonGia);
            txtSoLuong=itemView.findViewById(R.id.txtSoLuong);
            txtTen=itemView.findViewById(R.id.txtTenMon2);
            anh = itemView.findViewById(R.id.imganh);
            btntru = itemView.findViewById(R.id.btn_tru);
            btncong = itemView.findViewById(R.id.btn_cong);
            btnDel=itemView.findViewById(R.id.btn_xoa);
        }
    }
}
