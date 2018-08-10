package com.example.renaldysabdojatip.dloker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>  {

    FirebaseAuth mAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private Context mCtx;
    public CardView cardView;

    public List<Bookmark> bookmarks;

    public BookmarkAdapter(Context mCtx, List<Bookmark> bookmarks) {
        this.mCtx = mCtx;
        this.bookmarks = bookmarks;
    }


    public class BookmarkViewHolder extends RecyclerView.ViewHolder {
        public CardView card;
        public TextView tvTitle, tvPerusahaan, tvLokasi;
        public Button btn_del;
        public ImageView img;

        public BookmarkViewHolder(View itemView) {
            super(itemView);
            img = (ImageView)itemView.findViewById(R.id.imageView_bookmark);
            card = (CardView) itemView.findViewById(R.id.cardview_bookmark);
            tvTitle = (TextView) itemView.findViewById(R.id.textViewTitle_bookmark);
            tvLokasi = (TextView) itemView.findViewById(R.id.textViewLokasi_bookmark);
            tvPerusahaan = (TextView) itemView.findViewById(R.id.textViewPerusahaan_bookmark);

            btn_del = (Button)itemView.findViewById(R.id.btn_delete_bookmark);

        }

    }

    public void remove(int position){
        bookmarks.get(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bookmarks.size());
    }


    @NonNull
    @Override
    public BookmarkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bookmark, parent, false);
        return new BookmarkViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkViewHolder holder, final int position) {
        final Bookmark bm = bookmarks.get(position);

        mAuth = FirebaseAuth.getInstance();
        final String sUid = mAuth.getUid();
        final DatabaseReference mRef = mDatabase.child("Bookmark").child(sUid).child(bm.getTitle());

        holder.tvTitle.setText(bm.getTitle());
        holder.tvPerusahaan.setText(bm.getPerusahaan());
        holder.tvLokasi.setText(bm.getLokasi());
        Glide.with(mCtx)
                .load(bm.getPict())
                .into(holder.img);

        final String title = bm.getTitle();
        final String perusahaan = bm.getPerusahaan();
        final String lokasi = bm.getLokasi();
        final String detail = bm.getDetail();
        final String company = bm.getIdCompany();
        final String lowongan = bm.getIdLowongan();
        final String status = bm.getStatus();
        final String pict = bm.getPict();

        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
                remove(position);
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
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
