package com.example.renaldysabdojatip.dloker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.TimelineVIewHolder> {


    @NonNull
    @Override
    public TimelineVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline, parent, false);
        return new TimelineVIewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineVIewHolder holder, int position) {
        ((TimelineVIewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return TimelineData.title.length;
    }

    class TimelineVIewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title, perusahaan, lokasi;


        public TimelineVIewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_timeline);
            title = itemView.findViewById(R.id.textViewTitle_timeline);
            perusahaan = itemView.findViewById(R.id.textViewPerusahaan_timeline);
            lokasi = itemView.findViewById(R.id.textViewLokasi_timeline);

        }

        public void bindView(int position) {
            title.setText(TimelineData.title[position]);
            perusahaan.setText(TimelineData.perusahaan[position]);
            lokasi.setText(TimelineData.lokasi[position]);

            imageView.setImageResource(TimelineData.img[position]);
        }
    }

}
