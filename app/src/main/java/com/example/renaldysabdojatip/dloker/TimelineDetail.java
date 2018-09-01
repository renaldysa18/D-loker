package com.example.renaldysabdojatip.dloker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

    TextView tvTitle, tvPerusahaan, tvLokasi, tvDetail, tvNama, tvAlamat, tvEmail;
    public String sTitle, sPerusahaan, sLokasi, sDetail,
            sCompany, sLowongan, sStatus, idLamaran,
            statusLmr, cv, namaCV, pict, profileImage,
            nama, alamat, email;

    public String checkAlamat, checkBidangKerja, checkCV, checkDisabilitas, checkEmail, checkGender,
            checkNama, checkNotelp, checkPict, checkStatus, checkTTl, checkNamaCV;

    DatabaseReference mRef, lamaran, user, bookmark;
    FirebaseDatabase mData;
    FirebaseAuth mAuth;

    Button btn_bookmark;
    Button btn_lamaran;

    ImageView imageView;

    String img;

    public TimelineDetail() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_detail);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toobar_detail);
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
            pict = extras.getString("Pict");
            nama = extras.getString("Nama");
            email = extras.getString("Email");
            alamat = extras.getString("Alamat");
        }

        tvTitle = (TextView) findViewById(R.id.textViewTitle_timeline);
        tvLokasi = (TextView) findViewById(R.id.textViewLokasi_timeline_detail);
        tvPerusahaan = (TextView) findViewById(R.id.textViewPerusahaan_timeline);
        tvDetail = (TextView) findViewById(R.id.deksripsi_detail);
        tvEmail = (TextView) findViewById(R.id.email_perusahaan);
        tvNama = (TextView) findViewById(R.id.nama_perusahaan);
        tvAlamat = (TextView) findViewById(R.id.alamat_perusahaan);

        tvEmail.setText(email);
        tvAlamat.setText(alamat);
        tvNama.setText(nama);
        tvTitle.setText(sTitle);
        tvPerusahaan.setText(sPerusahaan);
        tvLokasi.setText(sLokasi);
        tvDetail.setText(sDetail);

        //img
        imageView = (ImageView) findViewById(R.id.imageView_detail_timeline);

        //Uri uri = Uri.parse(pict);
        Glide.with(getApplicationContext())
                .load(pict)
                .into(imageView);


        //img = pict;
        //bookmark

        mData = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        final String uid = mAuth.getUid();

        mRef = mData.getReference().child("Bookmark").child(uid).child(sTitle);

        btn_bookmark = (Button) findViewById(R.id.btn_tmbh_bookmark);

        btn_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map post = new HashMap();
                post.put("Title", sTitle);
                post.put("Perusahaan", sPerusahaan);
                post.put("Lokasi", sLokasi);
                post.put("UID", uid);
                post.put("DetailPekerjaan", sDetail);
                post.put("idCompany", sCompany);
                post.put("idLowongan", sLowongan);
                post.put("Status", sStatus);
                post.put("Pict", pict);
                post.put("Nama", nama);
                post.put("Email", email);
                post.put("Alamat", alamat);

                mRef.setValue(post);

                Toast.makeText(getApplicationContext(), "Ditambahkan", Toast.LENGTH_SHORT).show();

            }
        });

        user = mData.getReference().child("Users").child(uid);

        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*cv = dataSnapshot.child("CV").getValue(String.class);
                if(!cv.equalsIgnoreCase("CV Belum Tersedia")){
                    btn_lamaran.setVisibility(View.VISIBLE);
                }
                profileImage = dataSnapshot.child("Pict").getValue(String.class);*/

                checkNama = dataSnapshot.child("Nama").getValue().toString();
                checkEmail = dataSnapshot.child("Email").getValue().toString();
                checkAlamat = dataSnapshot.child("Alamat").getValue().toString();
                checkBidangKerja = dataSnapshot.child("BidangKerja").getValue().toString();
                checkNotelp = dataSnapshot.child("NoTelp").getValue().toString();
                checkGender = dataSnapshot.child("Gender").getValue().toString();
                checkTTl = dataSnapshot.child("TempatTanggalLahir").getValue().toString();
                checkDisabilitas = dataSnapshot.child("Disabilitas").getValue().toString();
                checkCV = dataSnapshot.child("namaCV").getValue().toString();

                if (!checkData(checkNama, checkEmail, checkAlamat, checkBidangKerja, checkNotelp,
                        checkGender, checkTTl, checkDisabilitas, checkCV
                )) {
                    btn_lamaran.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(TimelineDetail.this, "Harap Lengkapi Data Diri dan CV", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //checkButton Lamaran


        idLamaran = mData.getReference().push().getKey().toString();
        lamaran = mData.getReference().child("Lamaran").child(idLamaran);

        btn_lamaran = (Button) findViewById(R.id.btn_kirim_lamaran);

        btn_lamaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //default
                statusLmr = "wait";
                //profileImage = "https://firebasestorage.googleapis.com/v0/b/dloker-aac16.appspot.com/o/images%2Favatar1.png?alt=media&token=5339f319-38c2-400d-9ef3-a40d0a891dd7";


                Map post = new HashMap();
                post.put("Title", sTitle);
                post.put("Perusahaan", sPerusahaan);
                post.put("Lokasi", sLokasi);
                post.put("UID", uid);
                post.put("DetailPekerjaan", sDetail);
                post.put("idCompany", sCompany);
                post.put("idLowongan", sLowongan);
                post.put("idLamaran", idLamaran);
                post.put("statusLmr", statusLmr);
                post.put("CV", cv);
                post.put("PelamarPict", profileImage);
                post.put("PictComp", pict);
                post.put("Nama", nama);
                post.put("Email", email);
                post.put("Alamat", alamat);

                lamaran.setValue(post);
                Toast.makeText(getApplicationContext(), "Mengirim Lamaran", Toast.LENGTH_SHORT).show();

            }
        });

        //Toast.makeText(getApplicationContext(), cv, Toast.LENGTH_SHORT).show();
    }

    private boolean checkData(String checkNama, String checkEmail, String checkAlamat,
                              String checkBidangKerja, String checkNotelp, String checkGender,
                              String checkTTl, String checkDisabilitas, String checkCV) {

        if(checkNama.equalsIgnoreCase("-")){
            return false;
        }
        if(checkEmail.equalsIgnoreCase("-")){
            return false;
        }
        if(checkAlamat.equalsIgnoreCase("-")){
            return false;
        }
        if(checkBidangKerja.equalsIgnoreCase("-")){
            return false;
        }
        if(checkNotelp.equalsIgnoreCase("-")){
            return false;
        }
        if(checkGender.equalsIgnoreCase("-")){
            return false;
        }
        if(checkTTl.equalsIgnoreCase("-")){
            return false;
        }
        if(checkDisabilitas.equalsIgnoreCase("-")){
            return false;
        }
        if(checkCV.equalsIgnoreCase("-")){
            return false;
        }



        return true;
    }


}
