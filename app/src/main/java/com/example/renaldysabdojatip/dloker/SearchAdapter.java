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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    public Context mCtx;
    public CardView cardView;
    public List<Search> searches;

    public SearchAdapter(Context mCtx, List<Search> searches) {
        this.mCtx = mCtx;
        this.searches = searches;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search, parent, false);
        return new SearchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Search sr = searches.get(position);

        holder.title.setText(sr.getTitle());
        holder.kategori.setText(sr.getKategori());
        holder.lokasi.setText(sr.getLokasi());

        final String title = sr.getTitle();
        final String lokasi = sr.getLokasi();
        final String perusahaan = sr.getKategori();
        final String detail = sr.getDetail();
        final String company = sr.getIdCompany();
        final String lowongan = sr.getIdLowongan();
        final String status = sr.getStatus();

        holder.mCard.setOnClickListener(new View.OnClickListener() {
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
                v.getContext().startActivity(intent);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return searches.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder{

        TextView title, lokasi, kategori;
        CardView mCard;

        public SearchViewHolder(View itemView) {
            super(itemView);
            mCard = (CardView)itemView.findViewById(R.id.cardview_search);
            title = (TextView)itemView.findViewById(R.id.textViewTitle_search);
            lokasi = (TextView)itemView.findViewById(R.id.textViewLokasi_search);
            kategori = (TextView)itemView.findViewById(R.id.textViewKategori_search);
        }
    }
}
