package com.example.renaldysabdojatip.dloker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull TimelineViewHolder holder, int position) {
        Timeline tm = timelines.get(position);
        holder.tvtitle.setText(tm.getTitle());
        holder.tvperusahaan.setText(tm.getPerusahaan());
        holder.tvlokasi.setText(tm.getLokasi());
    }

    @Override
    public int getItemCount() {
        return timelines.size();
    }

    public static class TimelineViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardview;
        public TextView tvtitle, tvperusahaan, tvlokasi;
        public TimelineViewHolder(View itemView) {
            super(itemView);
            mCardview = (CardView)itemView.findViewById(R.id.cardview_timeline);
            tvlokasi = (TextView)itemView.findViewById(R.id.textViewLokasi_timeline);
            tvperusahaan = (TextView)itemView.findViewById(R.id.textViewPerusahaan_timeline);
            tvtitle = (TextView)itemView.findViewById(R.id.textViewTitle_timeline);
        }
    }
}
