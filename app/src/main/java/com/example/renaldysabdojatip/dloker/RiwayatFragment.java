package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    public RiwayatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Riwayat");
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
                        String status = ds.child("Status").getValue(String.class);
                        riwayats.add(new Riwayat(title, perusahaan, lokasi, detail, idCompany, idLowongan, status));
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

}
