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

    }

    @Override
    public int getItemCount() {
        return rekomendasis.size();
    }

    public class RekomendasiViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView title, perusahaan, lokasi;

        public RekomendasiViewHolder(View itemView) {
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.cardview_rekomendasi);
            title = (TextView)itemView.findViewById(R.id.textViewTitle_rekomendasi);
            perusahaan = (TextView)itemView.findViewById(R.id.textViewPerusahaan_rekomendasi);
            lokasi = (TextView)itemView.findViewById(R.id.textViewLokasi_rekomendasi);
        }
    }
}
