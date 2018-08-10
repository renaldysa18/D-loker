package com.example.renaldysabdojatip.dloker;

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

public class RekomendasiAdapter extends RecyclerView.Adapter<RekomendasiAdapter.RekomendasiViewHolder> {

    public CardView cardView;
    public Context mCtx;
    public List<Rekomendasi> rekomendasis;

    public RekomendasiAdapter(Context mCtx, List<Rekomendasi> rekomendasis) {
        this.mCtx = mCtx;
        this.rekomendasis = rekomendasis;
    }

    @NonNull
    @Override
    public RekomendasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rekomendasi, parent, false);

        return new RekomendasiViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RekomendasiViewHolder holder, int position) {
        Rekomendasi rm = rekomendasis.get(position);

        holder.title.setText(rm.getTitle());
        holder.kategori.setText(rm.getKategori());
        holder.lokasi.setText(rm.getLokasi());
        Glide.with(mCtx)
                .load(rm.getPict())
                .into(holder.img);

        final String title = rm.getTitle();
        final String kategori = rm.getKategori();
        final String lokasi = rm.getLokasi();
        final String detail = rm.getDetail();
        final String company = rm.getIdCompany();
        final String lowongan = rm.getIdLowongan();
        final String status = rm.getStatus();
        final String pict = rm.getPict();

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimelineDetail.class);
                intent.putExtra("Title", title);
                intent.putExtra("Perusahaan",kategori);
                intent.putExtra("Lokasi", lokasi);
                intent.putExtra("DetailPekerjaan", detail);
                intent.putExtra("idCompany", company);
                intent.putExtra("idLowongan", lowongan);
                intent.putExtra("Status", status);
                intent.putExtra("Pict", pict);
                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rekomendasis.size();
    }

    public class RekomendasiViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView title, kategori, lokasi;
        ImageView img;

        public RekomendasiViewHolder(View itemView) {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.imageView_rekomendasi);
            card = (CardView) itemView.findViewById(R.id.cardview_rekomendasi);
            title = (TextView)itemView.findViewById(R.id.textViewTitle_rekomendasi);
            kategori = (TextView)itemView.findViewById(R.id.textViewPerusahaan_rekomendasi);
            lokasi = (TextView)itemView.findViewById(R.id.textViewLokasi_rekomendasi);
        }
    }
}
