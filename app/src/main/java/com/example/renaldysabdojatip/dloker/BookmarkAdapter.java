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

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder> {

    private Context mCtx;
    public CardView cardView;
    List<Bookmark> bookmarks;

    public BookmarkAdapter(Context mCtx, List<Bookmark> bookmarks) {
        this.mCtx = mCtx;
        this.bookmarks = bookmarks;
    }

    public static class BookmarkViewHolder extends RecyclerView.ViewHolder{
        public CardView card;
        public TextView tvTitle, tvPerusahaan, tvLokasi;

        public BookmarkViewHolder(View itemView) {
            super(itemView);
            card = (CardView)itemView.findViewById(R.id.cardview_bookmark);
            tvTitle = (TextView)itemView.findViewById(R.id.textViewTitle_bookmark);
            tvLokasi = (TextView)itemView.findViewById(R.id.textViewLokasi_bookmark);
            tvPerusahaan = (TextView)itemView.findViewById(R.id.textViewPerusahaan_bookmark);
        }
    }

    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bookmark, parent, false);
        return new BookmarkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, int position) {
        Bookmark bm = bookmarks.get(position);

        holder.tvTitle.setText(bm.getTitle());
        holder.tvPerusahaan.setText(bm.getPerusahaan());
        holder.tvLokasi.setText(bm.getLokasi());


        final String title = bm.getTitle();
        final String perusahaan = bm.getPerusahaan();
        final String lokasi = bm.getLokasi();
        final String detail = bm.getDetail();

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimelineDetail.class);
                intent.putExtra("Title", title);
                intent.putExtra("Perusahaan", perusahaan);
                intent.putExtra("Lokasi", lokasi);
                intent.putExtra("DetailPekerjaan", detail);
                v.getContext().startActivity(intent);

                //Toast.makeText(v.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }


}