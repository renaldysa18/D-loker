package com.example.renaldysabdojatip.dloker;


import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RiwayatFragment extends Fragment {

    RecyclerView recyclerView;
    List<Riwayat> riwayats = new ArrayList<>();
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    RiwayatAdapter adapter;
    FirebaseAuth mAuth;
    public MainActivity activity;
    String CHANNEL_ID = "com.example.renaldy.notif";
    public int check = 0;
    public RiwayatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Riwayat");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_riwayat, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_riwayat);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        mAuth = FirebaseAuth.getInstance();

        final String sUid = mAuth.getUid();
        //sementara
        final DatabaseReference mRef = mDatabase.child("Lamaran");


        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                    riwayats.clear();
                //String uid = ds.child("UID").getValue(String.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String uid = ds.child("UID").getValue(String.class);
                    if (sUid.equalsIgnoreCase(uid)) {
                        String title = ds.child("Title").getValue(String.class);
                        String perusahaan = ds.child("Perusahaan").getValue(String.class);
                        String lokasi = ds.child("Lokasi").getValue(String.class);
                        String detail = ds.child("DetailPekerjaan").getValue(String.class);
                        String idCompany = ds.child("idCompany").getValue(String.class);
                        String idLowongan = ds.child("idLowongan").getValue(String.class);
                        String status = ds.child("statusLmr").getValue(String.class);
                        String pict = ds.child("PictComp").getValue(String.class);
                        String nama = ds.child("Nama").getValue(String.class);
                        String email = ds.child("Email").getValue(String.class);
                        String alamat = ds.child("Alamat").getValue(String.class);
                        String ntfUser = ds.child("ntfUser").getValue(String.class);
                        String alertUser = ds.child("AlertUser").getValue(String.class);
                        final String idLamaran = ds.getKey().toString();

                        //Bundle bundle = this.getArguments();
                       // String nt = getArguments().getString("cuk");
//                        if (nt == null) {
//
//                        }
//                        else {

                        if (alertUser.equalsIgnoreCase("true")) {

                            if (status.equalsIgnoreCase("accepted")) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                                dialog.setTitle("Selamat Lamaran Anda Diterima")
                                        .setMessage("Silakan Menunggu Email dari " + email + " untuk info lebih lanjut")
                                        .setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mRef.child(idLamaran).child("AlertUser").setValue("false");
//                                                mRef.addValueEventListener(new ValueEventListener() {
//                                                    @Override
//                                                    public void onDataChange(DataSnapshot dataSnapshot) {
//                                                        dataSnapshot.child(idLamaran).set
//                                                    }
//
//                                                    @Override
//                                                    public void onCancelled(DatabaseError databaseError) {
//
//                                                    }
//                                                })
                                                check++;
                                                dialog.dismiss();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_info_black_24dp)
                                        .show();

                            } else if (status.equalsIgnoreCase("refused")) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                                dialog.setTitle("Lamaran Anda Tidak Diterima")
                                        .setMessage("Mohon Maaf Lamaran Anda Pada " + nama + " Tidak Diterima")
                                        .setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                mRef.child(idLamaran).child("AlertUser").setValue("false");
                                                dialog.dismiss();
                                            }
                                        })
                                        .setIcon(R.drawable.ic_info_black_24dp)
                                        .show();

                            }

                        }

                            //nt = "t";
                            //setArguments();
                       // }



                        riwayats.add(new Riwayat(title, perusahaan, lokasi, detail, idCompany, idLowongan, status, pict, nama, email, alamat));
                    }
                }

                adapter = new RiwayatAdapter(getContext(), riwayats);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
