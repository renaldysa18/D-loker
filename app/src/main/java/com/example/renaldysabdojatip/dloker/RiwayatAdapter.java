package com.example.renaldysabdojatip.dloker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    CardView cardView;
    Context mCtx;
    List<Riwayat> riwayats;

    public RiwayatAdapter(Context mCtx, List<Riwayat> riwayats) {
        this.mCtx = mCtx;
        this.riwayats = riwayats;
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder {
        public CardView mCard;
        public TextView title, lokasi, kategori, status;
        public ImageView img;

        public RiwayatViewHolder(View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.imageView_riwayat);
            mCard = (CardView) itemView.findViewById(R.id.cardview_riwayat);
            title = (TextView) itemView.findViewById(R.id.textViewTitle_riwayat);
            lokasi = (TextView) itemView.findViewById(R.id.textViewLokasi_riwayat);
            kategori = (TextView) itemView.findViewById(R.id.textViewPerusahaan_riwayat);
            status = (TextView) itemView.findViewById(R.id.status_riwayat);

        }
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat, parent, false);
        return new RiwayatViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {

        Riwayat ry = riwayats.get(position);


        holder.title.setText(ry.getTitle());
        holder.lokasi.setText(ry.getLokasi());
        holder.kategori.setText(ry.getKategori());
        if (ry.getStatus().equalsIgnoreCase("Menunggu")) {
            holder.status.setText(ry.getStatus());
            holder.status.setTextColor(R.color.blue);
        } else if (ry.getStatus().equalsIgnoreCase("Diterima")) {
            holder.status.setText(ry.getStatus());
            holder.status.setTextColor(R.color.green);
        } else if (ry.getStatus().equalsIgnoreCase("DiTolak")) {
            holder.status.setText(ry.getStatus());
            holder.status.setTextColor(R.color.red);
        }

        Glide.with(mCtx)
                .load(ry.getPict())
                .into(holder.img);

        final String stitle = ry.getTitle();
        final String slokasi = ry.getLokasi();
        final String skategori = ry.getKategori();
        final String sdetail = ry.getDetail();
        final String company = ry.getIdCompany();
        final String lowongan = ry.getIdLowongan();
        final String status = ry.getStatus();

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimelineDetail.class);
                intent.putExtra("Title", stitle);
                intent.putExtra("Perusahaan", skategori);
                intent.putExtra("Lokasi", slokasi);
                intent.putExtra("DetailPekerjaan", sdetail);
                intent.putExtra("idCompany", company);
                intent.putExtra("idLowongan", lowongan);
                intent.putExtra("Status", status);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return riwayats.size();
    }


}
