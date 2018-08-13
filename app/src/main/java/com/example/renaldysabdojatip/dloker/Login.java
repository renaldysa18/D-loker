package com.example.renaldysabdojatip.dloker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText email, pass;

    Button btn_login, to_signup, to_forget;

    ProgressBar progressBar;

    FirebaseDatabase db;

    FirebaseAuth mAuth;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.email_login);

        pass = (EditText) findViewById(R.id.password_login);

        progressBar = (ProgressBar) findViewById(R.id.pb_login);

        btn_login = (Button) findViewById(R.id.login_btn);

        btn_login.setOnClickListener(this);

        to_signup = (Button) findViewById(R.id.btn_to_singup);

        to_signup.setOnClickListener(this);

        to_forget = (Button) findViewById(R.id.btn_to_forget);

        to_forget.setOnClickListener(this);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        if (mAuth.getCurrentUser() != null) {

            Intent intent = new Intent(Login.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                loginCheck();
                break;

            case R.id.btn_to_singup:
                keSignup();
                break;

            case R.id.btn_to_forget:
                keForget();
                break;
        }
    }

    private void keForget() {
        Intent intent = new Intent(Login.this, ForgetPasword.class);
        startActivity(intent);
    }

    private void keSignup() {
        Intent intent = new Intent(Login.this, SignUp.class);
        startActivity(intent);
    }

    private void loginCheck() {
        String Semail, Spass;
        Semail = email.getText().toString().trim();
        Spass = pass.getText().toString().trim();

        //email

        if (Semail.isEmpty()) {
            email.setError("Email Tidak Boleh Kosong");

            email.requestFocus();

            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Semail).matches()) {
            email.setError("Email Tidak Benar");

            email.requestFocus();

            return;
        }

        //pass

        if (Spass.isEmpty()) {
            pass.setError("Kata Sandi Tidak Boleh Kosong");

            pass.requestFocus();

            return;
        }

        if (Spass.length() < 6) {
            pass.setError("Kata Sandi Kurang Dari 6 Karakter");

            pass.requestFocus();

            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        //String uid = mAuth.getUid();
        //String accType = "Pelamar";


       /* mRef.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cek = dataSnapshot.child("accType").getValue().toString();
                type = cek;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        Bundle extras = getIntent().getExtras();

        mAuth.signInWithEmailAndPassword(Semail, Spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){
                    db.getReference("Users").child(mAuth.getCurrentUser().getUid()).child("accType")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.getValue(String.class).equals("Pelamar")){
                                        mAuth.signOut();
                                        Toast.makeText(Login.this,
                                                "Mohon login menggunakan akun khusus Pelamar", Toast.LENGTH_SHORT).show();
                                    }
                                    else {

                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                } else {

                    Toast.makeText(getApplicationContext(), "Email atau Password Anda Salah", Toast.LENGTH_SHORT).show();

                }

            }
        });


//       if(extras != null){
//           type = extras.getString("accType");
//       }
//
//        if (type.equalsIgnoreCase("Pelamar")) {
//
//            mAuth.signInWithEmailAndPassword(Semail, Spass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//
//                    progressBar.setVisibility(View.GONE);
//
//                    if (task.isSuccessful()) {
//
//                        Intent intent = new Intent(Login.this, MainActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//
//                    } else {
//
//                        Toast.makeText(getApplicationContext(), "Email atau Password Anda Salah", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//            });
//        } else {
//
//            Toast.makeText(getApplicationContext(),"Gunakan Akun Pelamar", Toast.LENGTH_SHORT).show();;
//
//        }


    }
}
