package com.example.renaldysabdojatip.dloker;

import android.app.FragmentManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity implements BottomSheetDialog.BottomSheetListener{

    private EditText nama, email, ttl, alamat, notelp;
    private Spinner disabilitas, gender, bidang;
    private Button btn_save_edit;
    private CircleImageView pict;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private ProfileFragment pf;
    private ProgressBar progressBar;
    private TextView judul_edit;
    private ImageButton btn_pict;

    //image
    //firebase store
    FirebaseStorage storage;
    StorageReference storageReference;

    //uri

    Uri filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        pf = new ProfileFragment();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toobar_edit);
        toolbar.setTitle(getString(R.string.edit_profile));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //init firebase store
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //progressbar
        progressBar = (ProgressBar)findViewById(R.id.pb_edit);

        //spinner
        disabilitas = (Spinner)findViewById(R.id.edit_profile_disabilitas);
        gender = (Spinner)findViewById(R.id.edit_profile_gender);
        bidang = (Spinner)findViewById(R.id.edit_profile_bidang_kerja);

        //edit
        nama = (EditText)findViewById(R.id.edit_profile_nama);
        email = (EditText)findViewById(R.id.edit_profile_email);
        ttl = (EditText)findViewById(R.id.edit_profile_ttl);
        alamat = (EditText)findViewById(R.id.edit_profile_alamat);
        notelp = (EditText)findViewById(R.id.edit_profile_notelp);


        //spinner array

        final String [] gender_array = {"Pria", "Wanita"};

        String [] disabilitas_array = {"Tuna Rungu", "Tuna Wicara"};

        final String [] bidang_kerja = {"Sosial", "Kuliner", "Teknologi", "Busana dan Tata Rias", "Keuangan"};

        //gender
        ArrayAdapter<String> LTRgender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,gender_array);
        LTRgender.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        gender.setAdapter(LTRgender);

        //disabilitas
        ArrayAdapter<String> LTRdisabilitas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, disabilitas_array);
        LTRdisabilitas.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        disabilitas.setAdapter(LTRdisabilitas);

        //bidang kerja
        ArrayAdapter<String> LTRBidang =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bidang_kerja);
        LTRBidang.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        bidang.setAdapter(LTRBidang);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mRef = FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        //btn
        btn_save_edit = (Button)findViewById(R.id.btn_edit_profile);

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

                //image
                String url;
                url = dataSnapshot.child("Pict").getValue().toString();

                Glide.with(EditProfile.this)
                        .load(url)
                        .into(pict);

                nama.setText(dataNama);
                email.setText(dataEmail);
                alamat.setText(dataAlamat);
                ttl.setText(dataTTL);
                notelp.setText(dataNotelp);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("error : ", databaseError.getMessage());

            }
        });

        btn_save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Snama , Semail, Snotelp, Sbidang, Salamat, Sttl;

                //edit value
                Snama = nama.getText().toString().trim();
                Semail = email.getText().toString().trim();
                Snotelp = notelp.getText().toString().trim();

                Salamat = alamat.getText().toString().trim();
                Sttl = ttl.getText().toString().trim();

                //spinner value
                final String Sgender, Sdisabilitas;

                Sbidang = bidang.getSelectedItem().toString();
                Sgender = gender.getSelectedItem().toString();
                Sdisabilitas = disabilitas.getSelectedItem().toString();

                //image
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

                        //coba
                        //dataSnapshot.getRef().child("Pict").setValue(downloadImg);
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


                            /*FragmentManager fm = getFragmentManager();
                            fm.beginTransaction().replace(R.id.main_frame, pf).commit();*/

                            Toast.makeText(EditProfile.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                uploadImage();
            }
        });


        //bottom sheet dialog
        judul_edit = (TextView)findViewById(R.id.judul_edit);

        //image
        pict = (CircleImageView) findViewById(R.id.edit_profile_pict);


        btn_pict = (ImageButton)findViewById(R.id.btn_edit_pict);

        btn_pict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bsd = new BottomSheetDialog();
                bsd.show(getSupportFragmentManager(), "bottomSheetDialog");
            }
        });
    }

    private void uploadImage() {
        if(filepath != null){
            String uid = mUser.getUid();
            final StorageReference ref = storageReference.child("images/"+ uid);
            ref.putFile(filepath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if(task.isSuccessful()){
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Uri downloadImg = uri;
                                        String Suri = downloadImg.toString();
                                        mRef.child("Pict").setValue(Suri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                });
                            }

                        }
                    });
                    /*.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });*/

        }
    }


    @Override
    public void onButtonClicked(Bitmap bitmap) {
        pict.setImageBitmap(bitmap);
    }

    @Override
    public void uri(Uri uri) {
        this.filepath = uri;
    }


}
