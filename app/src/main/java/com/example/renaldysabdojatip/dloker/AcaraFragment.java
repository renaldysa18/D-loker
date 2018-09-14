package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
public class AcaraFragment extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private RecyclerView recyclerView;
    private List<Acara> acaras = new ArrayList<>();
    private AcaraAdapter adapter;

    public AcaraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_acara, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_acara);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        DatabaseReference data = mDatabase.child("Acara");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                acaras.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String desc = ds.child("desc").getValue(String.class);
                    String idCompany = ds.child("idCompany").getValue(String.class);
                    String lokasi = ds.child("lokasi").getValue(String.class);
                    String namaAcara = ds.child("namaAcara").getValue(String.class);
                    String tanggal = ds.child("tanggal").getValue(String.class);
                    String img = ds.child("Pict").getValue(String.class);
                    String contact = ds.child("contact").getValue(String.class);
                    acaras.add(new Acara(desc, idCompany, lokasi, namaAcara, tanggal, img, contact));
                }
                adapter = new AcaraAdapter(getContext(), acaras);
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
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setActionBarTitle("Pelatihan");
    }
}
