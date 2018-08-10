package com.example.renaldysabdojatip.dloker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.util.Map;

public class TimelineDetail extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    List<Timeline> timelines = new ArrayList<>();
    TimelineAdapter adapter;

    TextView tvTitle, tvPerusahaan, tvLokasi, tvDetail;
    public String sTitle, sPerusahaan, sLokasi, sDetail, sCompany, sLowongan, sStatus, idLamaran, statusLmr, cv, namaCV;
    DatabaseReference mRef, lamaran,user;
    FirebaseDatabase mData;
    FirebaseAuth mAuth;

    Button btn_bookmark;
    Button btn_lamaran;

    public TimelineDetail() {
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_detail);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toobar_detail);
        toolbar.setTitle(getString(R.string.Detail_pekerjaan));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            sTitle = extras.getString("Title");
            sPerusahaan = extras.getString("Perusahaan");
            sLokasi = extras.getString("Lokasi");
            sDetail = extras.getString("DetailPekerjaan");
            sCompany = extras.getString("idCompany");
            sLowongan = extras.getString("idLowongan");
            sStatus = extras.getString("Status");
        }
        tvTitle = (TextView)findViewById(R.id.textViewTitle_timeline);
        tvLokasi = (TextView)findViewById(R.id.textViewLokasi_timeline_detail);
        tvPerusahaan = (TextView)findViewById(R.id.textViewPerusahaan_timeline);
        tvDetail = (TextView)findViewById(R.id.deksripsi_detail);

        tvTitle.setText(sTitle);
        tvPerusahaan.setText(sPerusahaan);
        tvLokasi.setText(sLokasi);
        tvDetail.setText(sDetail);

        //bookmark

        mData = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final String uid = mAuth.getUid();

        mRef = mData.getReference().child("Bookmark").child(uid).child(sTitle);

        btn_bookmark = (Button)findViewById(R.id.btn_tmbh_bookmark);

        btn_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Map post = new HashMap();
                post.put("Title", sTitle);
                post.put("Perusahaan", sPerusahaan);
                post.put("Lokasi", sLokasi);
                post.put("UID",uid);
                post.put("DetailPekerjaan", sDetail);
                post.put("idCompany", sCompany);
                post.put("idLowongan", sLowongan);
                post.put("Status", sStatus);
                mRef.setValue(post);

                Toast.makeText(getApplicationContext(), "Ditambahkan", Toast.LENGTH_SHORT).show();

            }
        });

        user = mData.getReference().child("Users").child(uid);

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cv = dataSnapshot.child("CV").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        idLamaran = mData.getReference().push().getKey().toString();
        lamaran = mData.getReference().child("Lamaran").child(idLamaran);

        btn_lamaran = (Button)findViewById(R.id.btn_kirim_lamaran);

        btn_lamaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //default
                statusLmr = "wait";

                Map post = new HashMap();
                post.put("Title", sTitle);
                post.put("Perusahaan", sPerusahaan);
                post.put("Lokasi", sLokasi);
                post.put("UID",uid);
                post.put("DetailPekerjaan", sDetail);
                post.put("idCompany", sCompany);
                post.put("idLowongan", sLowongan);
                post.put("Status", sStatus);
                post.put("idLamaran", idLamaran);
                post.put("statusLmr", statusLmr);
                post.put("CV", cv);


                lamaran.setValue(post);
                Toast.makeText(getApplicationContext(), "Mengirim Lamaran", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
