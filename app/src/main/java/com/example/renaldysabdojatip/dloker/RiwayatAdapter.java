package com.example.renaldysabdojatip.dloker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder> {

    CardView cardView;
    Context mCtx;
    List<Riwayat> riwayats;

    public RiwayatAdapter(Context mCtx, List<Riwayat> riwayats) {
        this.mCtx = mCtx;
        this.riwayats = riwayats;
    }

    public class RiwayatViewHolder extends RecyclerView.ViewHolder{
       public  CardView mCard;
       public TextView title, lokasi, kategori;

        public RiwayatViewHolder(View itemView) {
            super(itemView);

            mCard = (CardView)itemView.findViewById(R.id.cardview_riwayat);
            title = (TextView)itemView.findViewById(R.id.textViewTitle_riwayat);
            lokasi = (TextView)itemView.findViewById(R.id.textViewLokasi_riwayat);
            kategori = (TextView)itemView.findViewById(R.id.textViewPerusahaan_riwayat);
        }
    }

    @NonNull
    @Override
    public RiwayatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_riwayat, parent, false);
        return new RiwayatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatViewHolder holder, int position) {

        Riwayat ry = riwayats.get(position);

        holder.title.setText(ry.getTitle());
        holder.lokasi.setText(ry.getLokasi());
        holder.kategori.setText(ry.getKategori());

        final String stitle = ry.getTitle();
        final String slokasi =  ry.getLokasi();
        final String skategori = ry.getKategori();
        final String sdetail = ry.getDetail();

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimelineDetail.class);
                intent.putExtra("Title", stitle);
                intent.putExtra("Perusahaan", skategori);
                intent.putExtra("Lokasi", slokasi);
                intent.putExtra("DetailPekerjaan", sdetail);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return riwayats.size();
    }


}
