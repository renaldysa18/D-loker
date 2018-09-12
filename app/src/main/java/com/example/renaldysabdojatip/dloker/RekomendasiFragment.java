package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RekomendasiFragment extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth;
    private List<Rekomendasi> rekomendasis = new ArrayList<>();
    private RekomendasiAdapter adapter;
    private RecyclerView recyclerView;

    public String bidangKerja;
    public HashMap<String, String> usr;

    public RekomendasiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_rekomendasi, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recycler_rekomendasi);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getUid();

        DatabaseReference user = mDatabase.child("Users").child(uid);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bidangKerja = dataSnapshot.child("BidangKerja").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userPict();

        DatabaseReference mRef = mDatabase.child("Lowongan");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rekomendasis.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String kategori = ds.child("Kategori").getValue(String.class);

                    if(bidangKerja.equals(kategori)){
                        String title = ds.child("Judul").getValue(String.class);
                        String lokasi = ds.child("Lokasi").getValue(String.class);
                        String detail = ds.child("Desc").getValue(String.class);
                        String idCompany = ds.child("idCompany").getValue(String.class);
                        String idLowongan = ds.getKey().toString();
                        String status = ds.child("Status").getValue(String.class);
                        String pict = usr.get(idCompany);
                        //alamat, email, nama perushaan
                        String alamat = ds.child("Alamat").getValue(String.class);
                        String nama = ds.child("Nama").getValue(String.class);
                        String email = ds.child("Email").getValue(String.class);
                        rekomendasis.add(new Rekomendasi(title,kategori, lokasi, detail, idCompany, idLowongan, status, pict, nama,alamat, email));
                    }
                }

                //Toast.makeText(getActivity(), bidangKerja, Toast.LENGTH_SHORT).show();

                adapter = new RekomendasiAdapter(getContext(), rekomendasis);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

    private void userPict() {
        usr = new HashMap<String, String>();
        final DatabaseReference user = mDatabase.child("Users");
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    //usr.put(ds.getKey(), ds.child("UidCompany").getValue(String.class));
                    String pict = ds.child("Pict").getValue(String.class);
                    Log.d("getKey", ds.getKey());
                    usr.put(ds.getKey(), pict);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).setActionBarTitle("Rekomendasi");
    }
}
