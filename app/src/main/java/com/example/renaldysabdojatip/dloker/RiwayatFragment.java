package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public RiwayatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setActionBarTitle("Riwayat");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_riwayat, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_riwayat);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        //sementara
        final DatabaseReference mRef = mDatabase.child("Lowongan");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    String title  = ds.child("Judul").getValue(String.class);
                    String perusahaan = ds.child("Kategori").getValue(String.class);
                    String lokasi = ds.child("Lokasi").getValue(String.class);
                    String detail = ds.child("Desc").getValue(String.class);

                    riwayats.add(new Riwayat(title, perusahaan, lokasi, detail));

                }

                adapter = new RiwayatAdapter(getContext(), riwayats);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  v;
    }

}
