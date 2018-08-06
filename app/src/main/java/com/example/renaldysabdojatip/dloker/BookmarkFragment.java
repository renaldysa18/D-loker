package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkFragment extends Fragment {

    final static String TAG = "BookmarkFragment";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    RecyclerView recyclerView;
    List<Bookmark> bookmarks = new ArrayList<>();
    BookmarkAdapter adapter;


    public BookmarkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bookmark, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_bookmark);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        final String sUid = mAuth.getUid();
        final DatabaseReference mRef = mDatabase.child("Bookmark").child(sUid);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookmarks.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //if (uid == sUid) {
                        String title = ds.child("Title").getValue(String.class);
                        String perusahaan = ds.child("Perusahaan").getValue(String.class);
                        String lokasi = ds.child("Lokasi").getValue(String.class);
                        String detail = ds.child("DetailPekerjaan").getValue(String.class);



                        bookmarks.add(new Bookmark(title, perusahaan, lokasi, detail));
                    //Toast.makeText(getActivity(), ds.child("UID").getValue(String.class),Toast.LENGTH_SHORT).show();
                    //}
                }
                adapter = new BookmarkAdapter(getContext(), bookmarks);
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
        ((MainActivity)getActivity()).setActionBarTitle("Bookmark");
    }
}
