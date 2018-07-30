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
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgetPasword extends AppCompatActivity implements View.OnClickListener {

    EditText email;

    Button to_singup, to_login, btn_forget;

    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pasword);

        email = (EditText)findViewById(R.id.email_forget);

        to_singup = (Button)findViewById(R.id.forget_to_singup);

        to_singup.setOnClickListener(this);

        to_login = (Button)findViewById(R.id.forget_to_login);

        to_login.setOnClickListener(this);

        btn_forget = (Button)findViewById(R.id.btn_forget);

        btn_forget.setOnClickListener(this);

        progressBar = (ProgressBar)findViewById(R.id.pb_forget);

        //firebase

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.forget_to_login:
                toLogin();
                break;

            case R.id.forget_to_singup:
                toSignup();
                break;

            case R.id.btn_forget:
                forgetEmail();
                break;

        }

    }

    private void forgetEmail() {

        String Semail;
        Semail = email.getText().toString().trim();

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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(Semail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);

                if(task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Kami Mengirimkan Email Untuk Reset Password" , Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Gagal Untuk Mengirimkan Email", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void toSignup() {
        Intent intent = new Intent(ForgetPasword.this, SignUp.class);
        startActivity(intent);
    }

    private void toLogin() {
        Intent intent = new Intent(ForgetPasword.this, Login.class);
        startActivity(intent);
    }
}
