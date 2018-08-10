package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
public class SearchFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    List<Search> searches = new ArrayList<>();
    SearchAdapter adapter;

    Spinner dasar;
    Button btn_cari;
    EditText text_cari;

    DatabaseReference mRef = mDatabase.child("Lowongan");

    public String sText;
    public String dropDasar;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_cari);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);




        //cari

        searches.clear();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String title = ds.child("Judul").getValue(String.class);
                    String kategori = ds.child("Kategori").getValue(String.class);
                    String lokasi = ds.child("Lokasi").getValue(String.class);
                    String detail = ds.child("Desc").getValue(String.class);
                    String idCompany = ds.child("idCompany").getValue(String.class);
                    String idLowongan = ds.getKey().toString();
                    String status = ds.child("Status").getValue(String.class);
                    searches.add(new Search(title, kategori, lokasi, detail, idCompany, idLowongan, status));
                }
                adapter = new SearchAdapter(getContext(), searches);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //dropdown
        dasar = (Spinner) v.findViewById(R.id.dropdown_cari);

        final String dasar_cari[] = {"Lokasi", "Kategori"};

        ArrayAdapter<String> LTRdasar = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, dasar_cari);
        LTRdasar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dasar.setAdapter(LTRdasar);


        //edit text
        text_cari = (EditText) v.findViewById(R.id.editText_search);



        //button

        btn_cari = (Button)v.findViewById(R.id.btn_cari);

        btn_cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sText = text_cari.getText().toString();
                dropDasar = dasar.getSelectedItem().toString();

                //Toast.makeText(v.getContext(), sText, Toast.LENGTH_SHORT).show();

                if(sText.isEmpty()){
                    text_cari.setError("Pencarian Tidak Boleh Kosong");
                    text_cari.requestFocus();
                    return;

                } else {
                    if(dropDasar.equals("Lokasi")){
                        searches.clear();
                        mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String slokasi = ds.child("Lokasi").getValue(String.class);
                                    if(sText.equalsIgnoreCase(slokasi)){
                                        String title = ds.child("Judul").getValue(String.class);
                                        String kategori = ds.child("Kategori").getValue(String.class);
                                        String detail = ds.child("Desc").getValue(String.class);
                                        String idCompany = ds.child("idCompany").getValue(String.class);
                                        String idLowongan = ds.getKey().toString();
                                        String status = ds.child("Status").getValue(String.class);
                                        searches.add(new Search(title, slokasi, kategori, detail, idCompany, idLowongan, status ));
                                    }
                                }
                                adapter = new SearchAdapter(getContext(), searches);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    if(dropDasar.equals("Kategori")){
                        searches.clear();
                        mRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()){
                                    String kategori = ds.child("Kategori").getValue(String.class);

                                    if(sText.equalsIgnoreCase(kategori)){
                                        String title = ds.child("Judul").getValue(String.class);
                                        String lokasi = ds.child("Lokasi").getValue(String.class);
                                        String detail = ds.child("Desc").getValue(String.class);
                                        String idCompany = ds.child("idCompany").getValue(String.class);
                                        String idLowongan = ds.getKey().toString();
                                        String status = ds.child("Status").getValue(String.class);
                                        searches.add(new Search(title,kategori, lokasi, detail, idCompany, idLowongan, status ));
                                    }
                                }
                                adapter = new SearchAdapter(getContext(), searches);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Search");
    }
}
