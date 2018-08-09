package com.example.renaldysabdojatip.dloker;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    ImageView pictProfil;

    TextView nama, email, alamat, ttl, gender, notelp, bidang_kerja, disabilitas, judul;

    Button btn_unggah, btn_lihat;

    FloatingActionButton fab_edit;

    Boolean isOpen = false;

    //private EditProfileFragment ep;

    private EditProfile editProfile;

    //firebase

    FirebaseAuth mAuth;

    FirebaseUser mUser;

    DatabaseReference mRef;

    //firebase storage
    FirebaseStorage storage;
    StorageReference storageReference;

    Uri imgUri;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

        //init
        //ep = new EditProfileFragment();

        //storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        mRef = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        //image
        pictProfil = (ImageView)rootView.findViewById(R.id.profile_pict);

        //textview
        alamat = (TextView)rootView.findViewById(R.id.profile_alamat);
        nama = (TextView)rootView.findViewById(R.id.profile_nama);
        email = (TextView)rootView.findViewById(R.id.profile_email);
        ttl = (TextView)rootView.findViewById(R.id.profile_ttl);
        gender = (TextView)rootView.findViewById(R.id.profile_gender);
        notelp = (TextView)rootView.findViewById(R.id.profile_notelp);
        bidang_kerja = (TextView)rootView.findViewById(R.id.profile_bidang_kerja);
        disabilitas = (TextView)rootView.findViewById(R.id.profile_disabilitas);

        //judul
        judul = (TextView)rootView.findViewById(R.id.judul_profile);

        //button
        btn_lihat = (Button)rootView.findViewById(R.id.btn_lihat_Cv);
        btn_unggah = (Button)rootView.findViewById(R.id.btn_unggah_Cv);

        //fab
        fab_edit = (FloatingActionButton)rootView.findViewById(R.id.fab_edit_profile);

        fab_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(isOpen){
                    FragmentManager fm  = getFragmentManager();
                    fm.beginTransaction().remove(ep).commit();

                    isOpen = false;
                } else {*/
                    Intent intent = new Intent(getActivity(), EditProfile.class);
                    startActivity(intent);
                    /*FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.main_frame, ep).commit();*/
                    /*isOpen = true;
                }*/
            }
        });
        //btn unggah
        btn_unggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "unggah clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //btn lihat
        btn_lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Lihat clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //mref
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataNama, dataEmail, dataNotelp, dataAlamat, dataBidang, dataDisabilitas, dataGender, dataTTL;

                dataNama = dataSnapshot.child("Nama").getValue().toString();
                dataEmail = dataSnapshot.child("Email").getValue().toString();
                dataAlamat = dataSnapshot.child("Alamat").getValue().toString();
                dataBidang = dataSnapshot.child("BidangKerja").getValue().toString();
                dataNotelp = dataSnapshot.child("NoTelp").getValue().toString();
                dataGender = dataSnapshot.child("Gender").getValue().toString();
                dataTTL = dataSnapshot.child("TempatTanggalLahir").getValue().toString();
                dataDisabilitas = dataSnapshot.child("Disabilitas").getValue().toString();


                String url;
                url = dataSnapshot.child("Pict").getValue().toString();
                Glide.with(getActivity())
                        .load(url)
                        .into(pictProfil);



                nama.setText(dataNama);
                email.setText(dataEmail);
                alamat.setText(dataAlamat);
                bidang_kerja.setText(dataBidang);
                notelp.setText(dataNotelp);
                gender.setText(dataGender);
                ttl.setText(dataTTL);
                disabilitas.setText(dataDisabilitas);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("Error pada : " , databaseError.getMessage());

            }
        });






        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).setActionBarTitle("Profile");
    }

}
