package com.example.renaldysabdojatip.dloker;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
public class TimelineFragment extends Fragment {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private List<Timeline> timelines = new ArrayList<>();
    private TimelineAdapter adapter;
    private RecyclerView recyclerView;

    public String SidCompany, pictCompany;

    FirebaseAuth mAuth;
    public String namaP, emailP, alamatP, pictP, keyP;

    //loading
    public ProgressBar loading;

    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_timeline, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_timeline);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        //loading
        loading = v.findViewById(R.id.loadingTimeline);

        DatabaseReference data = mDatabase.child("Lowongan");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timelines.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //loading.setVisibility(View.VISIBLE);

                    String title = ds.child("Judul").getValue(String.class);
                    String perusahaan = ds.child("Kategori").getValue(String.class);
                    String lokasi = ds.child("Lokasi").getValue(String.class);
                    String detail = ds.child("Desc").getValue(String.class);
                    String idCompany = ds.child("idCompany").getValue(String.class);
                    String idLowongan = ds.getKey().toString();
                    String status = ds.child("Status").getValue(String.class);
                    String pict = ds.child("Pict").getValue(String.class);

                    //alamat, email, nama perushaan
                    String alamat = ds.child("Alamat").getValue(String.class);
                    String nama = ds.child("Nama").getValue(String.class);
                    String email = ds.child("Email").getValue(String.class);

                    SidCompany = idCompany;

                    timelines.add(new Timeline(title, perusahaan,
                            lokasi, detail, idCompany, idLowongan, status, pict
                            , nama, alamat, email
                    ));
                }
                loading.setVisibility(View.GONE);
                adapter = new TimelineAdapter(getContext(), timelines);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                loading.setVisibility(View.GONE);
            }
        });
        //Toast.makeText(getActivity(), idComp,Toast.LENGTH_SHORT).show();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Timeline");
    }
}
