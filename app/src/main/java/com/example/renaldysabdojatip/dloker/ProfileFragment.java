package com.example.renaldysabdojatip.dloker;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    ImageView pictProfil;

    TextView nama, email, alamat, ttl, gender, notelp, bidang_kerja, disabilitas, judul_cv;

    Button btn_unggah, btn_pilih;

    FloatingActionButton fab_edit;

    Boolean isOpen = false;

    //private EditProfileFragment ep;

    private EditProfile editProfile;

    //firebase

    FirebaseAuth mAuth;

    FirebaseUser mUser;

    DatabaseReference mRef, dataUser;

    //firebase storage
    FirebaseStorage storage;
    StorageReference storageReference;

    Uri imgUri, filepath;
    String namaCV;

    private static int SELECT_FILE = 1;

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
        judul_cv = (TextView)rootView.findViewById(R.id.judul_cv);

        //button
        btn_pilih = (Button)rootView.findViewById(R.id.btn_pilih_Cv);
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
                uploadFile();
                mRef.child("namaCV").setValue(namaCV).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Mengunggah CV", Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(getActivity(), "Mengunggah CV", Toast.LENGTH_SHORT).show();
            }
        });

        //btn lihat
        btn_pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihCV(1);
                //Toast.makeText(getActivity(), "Lihat clicked", Toast.LENGTH_SHORT).show();
            }
        });

        //mref
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataNama, dataEmail, dataNotelp, dataAlamat,
                        dataBidang, dataDisabilitas, dataGender, dataTTL, dataCV;

                dataNama = dataSnapshot.child("Nama").getValue().toString();
                dataEmail = dataSnapshot.child("Email").getValue().toString();
                dataAlamat = dataSnapshot.child("Alamat").getValue().toString();
                dataBidang = dataSnapshot.child("BidangKerja").getValue().toString();
                dataNotelp = dataSnapshot.child("NoTelp").getValue().toString();
                dataGender = dataSnapshot.child("Gender").getValue().toString();
                dataTTL = dataSnapshot.child("TempatTanggalLahir").getValue().toString();
                dataDisabilitas = dataSnapshot.child("Disabilitas").getValue().toString();
                dataCV = dataSnapshot.child("namaCV").getValue().toString();

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
                judul_cv.setText(dataCV);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("Error pada : " , databaseError.getMessage());

            }
        });
        return rootView;
    }

    private void pilihCV(int pick) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(intent, SELECT_FILE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == SELECT_FILE){
                String s = data.getDataString();
                Uri uri = Uri.parse(s);
                this.filepath = uri;
                String path = data.getData().getPath();
                String name = path.substring(path.lastIndexOf("/")+1);
                this.namaCV = name;
                judul_cv.setText(name);
                if(!this.namaCV.equalsIgnoreCase("CV Belum Tersedia")){
                    btn_unggah.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    private void uploadFile() {
        if (filepath != null) {
            String uid = mUser.getUid();
            final StorageReference ref = storageReference.child("cv/" + uid);
            ref.putFile(filepath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri fileUri = uri;
                                        String Suri = fileUri.toString();
                                        mRef.child("CV").setValue(Suri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity().getApplicationContext(), "Upload Cv", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }

                        }
                    });

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).setActionBarTitle("Profile");
    }

}
