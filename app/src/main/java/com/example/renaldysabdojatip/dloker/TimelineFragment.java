package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    RecyclerView recyclerView;
    View rootView;
    //ListView listView;
    FirebaseRecyclerAdapter<Timeline, TimelineViewHolder> adapter;
    DatabaseReference mDatabase;


    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_timeline, container, false);

        intialView();


        return rootView;
    }

    private void intialView() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Timeline");
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycleview_timeline);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setUpAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void setUpAdapter() {
        adapter = new FirebaseRecyclerAdapter<Timeline, TimelineViewHolder>
                (
                        Timeline.class,
                        R.layout.list_timeline,
                        TimelineViewHolder.class,
                        mDatabase)
        {
            @Override
            protected void populateViewHolder(TimelineViewHolder viewHolder, Timeline model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setLokasi(model.getLokasi());
                viewHolder.setPerusahan(model.getPerusahaan());
            }
        };
    }

    public static class TimelineViewHolder extends RecyclerView.ViewHolder {
        View v;

        public TimelineViewHolder(View itemView) {
            super(itemView);
            v = itemView;
        }

        public void setTitle(String title) {
            TextView tvTitle = (TextView) v.findViewById(R.id.textViewTitle_timeline);
            tvTitle.setText(title);
        }

        public void setPerusahan(String perusahan) {
            TextView tvPerusahaan = (TextView) v.findViewById(R.id.textViewPerusahaan_timeline);
            tvPerusahaan.setText(perusahan);
        }

        public void setLokasi(String lokasi) {
            TextView tvLokasi = (TextView) v.findViewById(R.id.textViewLokasi_timeline);
            tvLokasi.setText(lokasi);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("Timeline");
    }
}
