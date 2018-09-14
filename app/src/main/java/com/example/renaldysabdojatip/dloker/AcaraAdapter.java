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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AcaraAdapter extends RecyclerView.Adapter<AcaraAdapter.ViewHolder> {

    private Context context;
    public CardView cardView;
    List<Acara> acaras;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public AcaraAdapter(Context context, List<Acara> acaras) {
        this.context = context;
        this.acaras = acaras;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_acara, parent, false
        );
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Acara acr = acaras.get(position);

        holder.judul.setText(acr.getNamaAcara());
        holder.tanggal.setText(acr.getTanggal());
        holder.lokasi.setText(acr.getLokasi());

        Glide.with(context)
                .load(acr.getImg())
                .into(holder.img);

        final String judul = acr.getNamaAcara();
        final String tanggal = acr.getTanggal();
        final String contact = acr.getContact();
        final String desc = acr.getDesc();
        final String lokasi = acr.getLokasi();
        final String img = acr.getImg();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent intent = new Intent(v.getContext(), )
                //Toast.makeText(v.getContext(), idCompany, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), AcaraDetail.class);
                intent.putExtra("Img", img);
                intent.putExtra("Judul", judul);
                intent.putExtra("Tanggal", tanggal);
                intent.putExtra("Contact", contact);
                intent.putExtra("Desc", desc);
                intent.putExtra("Lokasi", lokasi);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return acaras.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView judul, lokasi, tanggal;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_acara);
            judul = itemView.findViewById(R.id.textViewTitle_acara);
            lokasi = itemView.findViewById(R.id.textViewLokasi_acara);
            tanggal = itemView.findViewById(R.id.textViewTanggal_acara
            );
            img = itemView.findViewById(R.id.imageView_acara);
        }
    }
}
