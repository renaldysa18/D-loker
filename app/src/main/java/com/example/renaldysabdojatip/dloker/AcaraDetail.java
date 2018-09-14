package com.example.renaldysabdojatip.dloker;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class AcaraDetail extends AppCompatActivity {
    public String strJudul, strLokasi, strTanggal, strDesc, strNotelp, strImage;

    FloatingActionButton fabNotelp;
    TextView judul, lokasi, tanggal, desc, notelp;
    ImageView pict;

    public AcaraDetail() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acara_detail);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toobar_acara);
        toolbar.setTitle(getString(R.string.Detail_acara));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            strDesc = extras.getString("Desc");
            strImage = extras.getString("Img");
            strJudul = extras.getString("Judul");
            strLokasi = extras.getString("Lokasi");
            strTanggal = extras.getString("Tanggal");
            strNotelp = extras.getString("Contact");
        }
        //init
        judul = findViewById(R.id.textView_judulAcaraDetail);
        lokasi = findViewById(R.id.textView_lokasiAcraDetail);
        tanggal = findViewById(R.id.textView_tanggalAcaraDetail);
        desc = findViewById(R.id.textView_descAcaraDetail);
        notelp = findViewById(R.id.textView_contactAcaraDetail);

        pict = findViewById(R.id.imageView_acaraDetail);

        fabNotelp = findViewById(R.id.fabNoTelp);

        fabNotelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + strNotelp));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
        //image
        Glide.with(getApplicationContext())
                .load(strImage)
                .into(pict);

        //set
        judul.setText(strJudul);
        lokasi.setText(strLokasi);
        tanggal.setText(strTanggal);
        notelp.setText(strNotelp);
        desc.setText(strDesc);
    }
}
