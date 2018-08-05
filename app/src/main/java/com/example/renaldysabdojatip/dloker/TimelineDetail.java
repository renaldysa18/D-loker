package com.example.renaldysabdojatip.dloker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimelineDetail extends AppCompatActivity {

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    List<Timeline> timelines = new ArrayList<>();
    TimelineAdapter adapter;

    TextView tvTitle, tvPerusahaan, tvLokasi, tvDetail;
    String sTitle, sPerusahaan, sLokasi, sDetail;

    DatabaseReference mRef;
    FirebaseDatabase mData;
    FirebaseAuth mAuth;

    Button btn_bookmark;

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

                mRef.setValue(post);

                Toast.makeText(getApplicationContext(), "Ditambahkan", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
