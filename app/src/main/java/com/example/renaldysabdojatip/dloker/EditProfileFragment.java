package com.example.renaldysabdojatip.dloker;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.net.ssl.SNIMatcher;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {

    private EditText nama, email, ttl, alamat, bidangkerja, notelp;

    private Spinner disabilitas, gender;

    private Button btn_save_edit;

    private ImageView pict;

    private FirebaseUser mUser;

    private FirebaseAuth mAuth;

    private DatabaseReference mRef;

    private ProfileFragment pf;

    private ProgressBar progressBar;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        pf = new ProfileFragment();

        //progressbar
        progressBar = (ProgressBar)rootView.findViewById(R.id.pb_edit);

        //spinner
        disabilitas = (Spinner)rootView.findViewById(R.id.edit_profile_disabilitas);
        gender = (Spinner)rootView.findViewById(R.id.edit_profile_gender);


        //edit
        nama = (EditText)rootView.findViewById(R.id.edit_profile_nama);
        email = (EditText)rootView.findViewById(R.id.edit_profile_email);
        ttl = (EditText)rootView.findViewById(R.id.edit_profile_ttl);
        alamat = (EditText)rootView.findViewById(R.id.edit_profile_alamat);
        bidangkerja = (EditText)rootView.findViewById(R.id.edit_profile_bidang_kerja);
        notelp = (EditText)rootView.findViewById(R.id.edit_profile_notelp);

        //image
        pict = (ImageView)rootView.findViewById(R.id.edit_profile_pict);

        //spinner array

        String [] gender_array = {"Pria", "Wanita"};

        String [] disabilitas_array = {"Tuna Rungu", "Tuna Wicara"};

        //gender
        ArrayAdapter<String> LTRgender = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item,gender_array);
        LTRgender.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gender.setAdapter(LTRgender);

        //disabilitas
        ArrayAdapter<String> LTRdisabilitas = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, disabilitas_array);
        LTRdisabilitas.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        disabilitas.setAdapter(LTRdisabilitas);


        //firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mRef = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        //btn
        btn_save_edit = (Button)rootView.findViewById(R.id.btn_edit_profile);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String dataNama, dataEmail, dataNotelp, dataAlamat, dataBidang, dataDisabilitas, dataGender, dataTTL;

                dataNama = dataSnapshot.child("Nama").getValue().toString();
                dataEmail = dataSnapshot.child("Email").getValue().toString();
                dataAlamat = dataSnapshot.child("Alamat").getValue().toString();
                dataNotelp = dataSnapshot.child("NoTelp").getValue().toString();
                dataTTL = dataSnapshot.child("TempatTanggalLahir").getValue().toString();
                dataBidang = dataSnapshot.child("BidangKerja").getValue().toString();

                nama.setText(dataNama);
                email.setText(dataEmail);
                alamat.setText(dataAlamat);
                ttl.setText(dataTTL);
                notelp.setText(dataNotelp);
                bidangkerja.setText(dataBidang);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("error : ", databaseError.getMessage());

            }
        });



        //btn clicked
        btn_save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Snama , Semail, Snotelp, Sbidang, Salamat, Sttl;

                //edit value
                Snama = nama.getText().toString().trim();
                Semail = email.getText().toString().trim();
                Snotelp = notelp.getText().toString().trim();
                Sbidang = bidangkerja.getText().toString().trim();
                Salamat = alamat.getText().toString().trim();
                Sttl = ttl.getText().toString().trim();

                //spinner value
                final String Sgender, Sdisabilitas;

                Sgender = gender.getSelectedItem().toString();
                Sdisabilitas = disabilitas.getSelectedItem().toString();

                //nama
                if(Snama.isEmpty()){
                    nama.setError("Nama Tidak Boleh Kosong");
                    nama.requestFocus();
                    return;
                }

                //email
                if(Semail.isEmpty()){
                    email.setError("Email Tidak Boleh Kosong");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(Semail).matches()){
                    email.setError("Email Tidak Benar");
                    email.requestFocus();
                    return;
                }

                //notelp
                if(Snotelp.isEmpty()){
                    notelp.setError("Nomor Telepon Tidak Boleh Kosong");
                    notelp.requestFocus();
                    return;
                }

                //alamat
                if(Salamat.isEmpty()){
                    alamat.setError("Alamat Tidak Boleh Kosong");
                    alamat.requestFocus();
                    return;
                }

                //ttl
                if(Sttl.isEmpty()){
                    ttl.setError("Tempat Tanggal Lahir Tidak Boleh Kosong");
                    ttl.requestFocus();
                    return;
                }

                //bidang
                if(Sbidang.isEmpty()){
                    bidangkerja.setError("Bidang Kerja Tidak Boleh Kosong");
                    bidangkerja.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mRef.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {



                        dataSnapshot.getRef().child("Nama").setValue(Snama);
                        dataSnapshot.getRef().child("Email").setValue(Semail);
                        dataSnapshot.getRef().child("NoTelp").setValue(Snotelp);
                        dataSnapshot.getRef().child("Alamat").setValue(Salamat);
                        dataSnapshot.getRef().child("BidangKerja").setValue(Sbidang);
                        dataSnapshot.getRef().child("TempatTanggalLahir").setValue(Sttl);

                        dataSnapshot.getRef().child("Gender").setValue(Sgender);
                        dataSnapshot.getRef().child("Disabilitas").setValue(Sdisabilitas);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Error : ", databaseError.getMessage());
                    }
                });

                mUser.updateEmail(Semail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){

                            android.support.v4.app.FragmentManager fm = getFragmentManager();
                            fm.beginTransaction().replace(R.id.main_frame, pf).commit();

                            Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).setActionBarTitle("Edit Profile");
    }
}
