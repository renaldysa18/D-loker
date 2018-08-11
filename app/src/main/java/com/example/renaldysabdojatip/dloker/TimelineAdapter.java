package com.example.renaldysabdojatip.dloker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder> {

    private Context mCtx;
    public CardView cardView;
    List<Timeline> timelines;


    public TimelineAdapter(Context mCtx, List<Timeline> timelines) {
        this.mCtx = mCtx;
        this.timelines = timelines;
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_timeline, parent, false);

        return new TimelineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, final int position) {
        Timeline tm = timelines.get(position);
        holder.tvtitle.setText(tm.getTitle());
        holder.tvperusahaan.setText(tm.getPerusahaan());
        holder.tvlokasi.setText(tm.getLokasi());
        Glide.with(mCtx)
                .load(tm.getPict())
                .into(holder.img);

        final String title = tm.getTitle();
        final String perusahaan = tm.getPerusahaan();
        final String lokasi = tm.getLokasi();
        final String detail = tm.getDetail();
        final String company = tm.getIdCompany();
        final String lowongan = tm.getIdLowongan();
        final String status = tm.getStatus();
        final String pict = tm.getPict();

        //nama, alamat, email perusahaan
        final String nama = tm.getNama();
        final String email = tm.getEmail();
        final String alamat = tm.getAlmat();

        holder.mCardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimelineDetail.class);
                intent.putExtra("Title", title);
                intent.putExtra("Perusahaan", perusahaan);
                intent.putExtra("Lokasi", lokasi);
                intent.putExtra("DetailPekerjaan", detail);
                intent.putExtra("idCompany", company);
                intent.putExtra("idLowongan", lowongan);
                intent.putExtra("Status", status);
                intent.putExtra("Pict", pict);

                //nama, alamat, email
                intent.putExtra("Nama", nama);
                intent.putExtra("Email", email);
                intent.putExtra("Alamat" , alamat);

                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return timelines.size();
    }

    public static class TimelineViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardview;
        public TextView tvtitle, tvperusahaan, tvlokasi;
        public ImageView img;


        public TimelineViewHolder(View itemView) {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.imageView_timeline);
            mCardview = (CardView)itemView.findViewById(R.id.cardview_timeline);
            tvlokasi = (TextView)itemView.findViewById(R.id.textViewLokasi_timeline);
            tvperusahaan = (TextView)itemView.findViewById(R.id.textViewPerusahaan_timeline);
            tvtitle = (TextView)itemView.findViewById(R.id.textViewTitle_timeline);
        }
    }
}
