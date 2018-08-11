package com.example.renaldysabdojatip.dloker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SNIMatcher;

public class SignUp extends AppCompatActivity implements View.OnClickListener {


    EditText nama, email, notelp, katasandi, retype_katasandi;

    Button signup_btn, to_login;

    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    private FirebaseDatabase mDatabase;

    public String Spelamar = "Pelamar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nama = (EditText) findViewById(R.id.nama_signup);
        email = (EditText) findViewById(R.id.email_signup);
        notelp = (EditText) findViewById(R.id.notelp_signup);
        katasandi = (EditText) findViewById(R.id.password_signup);
        retype_katasandi = (EditText) findViewById(R.id.retype_pass_singup);

        progressBar = (ProgressBar) findViewById(R.id.pb_signup);

        to_login = (Button) findViewById(R.id.singup_to_login);

        to_login.setOnClickListener(this);

        signup_btn = (Button) findViewById(R.id.daftar_btn);

        signup_btn.setOnClickListener(this);


        //firebase auth
        mAuth = FirebaseAuth.getInstance();

        //firebase Database
        mDatabase = FirebaseDatabase.getInstance();

        if (mAuth.getCurrentUser() != null) {

            Intent intent = new Intent(SignUp.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.daftar_btn:
                signupCheck();

                break;

            case R.id.singup_to_login:
                keLogin();

                break;

        }

    }

    private void keLogin() {
        Intent intent = new Intent(SignUp.this, Login.class);
        intent.putExtra("accType", Spelamar);
        startActivity(intent);
    }

    private void signupCheck() {

        final String Snama, Semail, Snotelp, Spass, Sre_pass, Snull, Spict, Scv, Sstatus, namaCV;

        Snull = "-";

        namaCV = "CV Belum Tersedia";
        Sstatus = "Menunggu";
        Spict = "https://firebasestorage.googleapis.com/v0/b/dloker-aac16.appspot.com/o/images%2Favatar1.png?alt=media&token=5339f319-38c2-400d-9ef3-a40d0a891dd7";
        Scv = "CV Belum Tersedia";

        Snama = nama.getText().toString().trim();
        Semail = email.getText().toString().trim();
        Snotelp = notelp.getText().toString().trim();
        Spass = katasandi.getText().toString().trim();
        Sre_pass = retype_katasandi.getText().toString().trim();

        //nama
        if (Snama.isEmpty()) {
            nama.setError("Nama Tidak Boleh Kosong");

            nama.requestFocus();

            return;
        }

        //email
        if (Semail.isEmpty()) {

            email.setError("Email Tidak Boleh Kosong");

            email.requestFocus();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Semail).matches()) {

            email.setError("Email TIdak Benar");

            email.requestFocus();

            return;

        }

        //notelp

        if (Snotelp.isEmpty()) {
            notelp.setError("Nomor Telepon Tidak Boleh Kosong");

            notelp.requestFocus();

            return;
        }

        //katasandi
        if (Spass.isEmpty()) {
            katasandi.setError("Kata Sandi Tidak Boleh Kosong");

            katasandi.requestFocus();

            return;

        }

        if (Spass.length() < 6) {
            katasandi.setError("Kata Sandi Minimal 6 karakter");

            katasandi.requestFocus();

            return;
        }

        //retype_katasandi

        if (Sre_pass.isEmpty()) {

            retype_katasandi.setError("Kata Sandi Tidak Boleh Kosong");

            retype_katasandi.requestFocus();

            return;
        }

        if (!Sre_pass.equals(Spass)) {

            retype_katasandi.setError("Kata Sandi Tidak Sesuai");

            retype_katasandi.requestFocus();

            return;
        }

        //progressBar

        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(Semail, Spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    String uid = mAuth.getUid();

                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    Map newPost = new HashMap();

                    newPost.put("Email", Semail);
                    newPost.put("Nama", Snama);
                    newPost.put("NoTelp", Snotelp);

                    //try
                    newPost.put("Gender", Snull);
                    newPost.put("Alamat", Snull);
                    newPost.put("BidangKerja",Snull);
                    newPost.put("TempatTanggalLahir", Snull);
                    newPost.put("Disabilitas", Snull);
                    newPost.put("Pict", Spict);
                    newPost.put("CV", Scv);
                    newPost.put("Status", Sstatus);
                    newPost.put("accType", Spelamar);
                    newPost.put("namaCV", namaCV);

                    mRef.setValue(newPost);

                    Intent intent = new Intent(SignUp.this, MainActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intent);

                    finish();


                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                        Toast.makeText(getApplicationContext(), "Pengguna Telah Terdaftar", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
