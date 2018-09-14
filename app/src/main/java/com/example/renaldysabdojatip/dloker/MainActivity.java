package com.example.renaldysabdojatip.dloker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
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

import java.sql.Time;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "MAIN_ACTIVITY";
    private static final String CHANNEL_ID = "com.example.renaldy.notif";
    private BottomNavigationView bottomNavigationView;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;

    private FrameLayout mainFrame;

    private ProfileFragment pf;
    private SearchFragment sf;
    private BookmarkFragment bf;
    private RiwayatFragment rf;
    private TimelineFragment tf;
    private RekomendasiFragment rekf;
    private AcaraFragment af;

    //navigation
    TextView tnama, temail;
    CircleImageView pict;
    String snama, semail, url;

    View headerLayout;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);
        mAuth = FirebaseAuth.getInstance();
        final String suid = mAuth.getCurrentUser().getUid();
        String uid = mAuth.getUid();

        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                snama = dataSnapshot.child("Nama").getValue(String.class);
                tnama.setText(snama);
                semail =dataSnapshot.child("Email").getValue(String.class);
                temail.setText(semail);
                url = dataSnapshot.child("Pict").getValue(String.class);
                Glide.with(MainActivity.this)
                        .load(url)
                        .into(pict);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //notif
        final DatabaseReference mRef = mDatabase.child("Lamaran");
        //final String sUid = mAuth.getUid();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String uid = ds.child("UID").getValue(String.class);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String uid = ds.child("UID").getValue(String.class);
                    if (suid.equalsIgnoreCase(uid)) {
                        String title = ds.child("Title").getValue(String.class);
                        String perusahaan = ds.child("Perusahaan").getValue(String.class);
                        String lokasi = ds.child("Lokasi").getValue(String.class);
                        String detail = ds.child("DetailPekerjaan").getValue(String.class);
                        String idCompany = ds.child("idCompany").getValue(String.class);
                        String idLowongan = ds.child("idLowongan").getValue(String.class);
                        String status = ds.child("statusLmr").getValue(String.class);
                        String pict = ds.child("PictComp").getValue(String.class);
                        String nama = ds.child("Nama").getValue(String.class);
                        String email = ds.child("Email").getValue(String.class);
                        String alamat = ds.child("Alamat").getValue(String.class);
                        String ntfUser = ds.child("ntfUser").getValue(String.class);

                        if(status.equalsIgnoreCase("accepted") || status.equalsIgnoreCase("refused")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Riwayat", "Ok");
                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // intent.putExtra("ntf", "1");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);


                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .setSmallIcon(R.drawable.dlogo)
                                    .setContentTitle("Lamaran Di Respon")
                                    .setContentText("Lamaran Anda Telah Di Respon")
                                    .setLights(Color.RED, 1000, 300)
                                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                    .setVibrate(new long[]{100, 200, 300, 400, 500})
                                    .setDefaults(Notification.DEFAULT_VIBRATE)
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel(
                                        CHANNEL_ID, "Respon Lamaran", NotificationManager.IMPORTANCE_DEFAULT
                                );
                                channel.setDescription("Lamaran Telah Di Respon Oleh Perusahaan");
                                channel.setShowBadge(true);
                                channel.canShowBadge();
                                channel.enableLights(true);
                                channel.setLightColor(Color.RED);
                                channel.enableVibration(true);
                                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
                                notificationManager.createNotificationChannel(channel);
                            }

                            if(ntfUser.equalsIgnoreCase("true")){
                                notificationManager.notify(1, mBuilder.build());
                                mRef.child(ds.child("idLamaran").getValue(String.class)).child("ntfUser")
                                        .setValue("false");
                                Log.d("TAG", "CUK");
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        //init fragment

        pf = new ProfileFragment();
        sf = new SearchFragment();
        bf = new BookmarkFragment();
        rf = new RiwayatFragment();
        tf = new TimelineFragment();
        rekf = new RekomendasiFragment();
        af = new AcaraFragment();

        //init timeline fragment
        setFragment(tf);

        //firebase


        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Log.d(TAG, "sing in : "+user.getUid() );
                } else {
                    Toast.makeText(getApplicationContext(), "Keluar ", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        };


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        headerLayout = navigationView.getHeaderView(0);

        tnama = (TextView)headerLayout.findViewById(R.id.nama_navigation);
        temail = (TextView)headerLayout.findViewById(R.id.email_navigation);

        pict = (CircleImageView) headerLayout.findViewById(R.id.imageView_navigation);
        //nama.setText("renal");

        // set nama dan email header



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_profile:
                        setFragment(pf);
                        return true;

                    case R.id.bottom_rekom:
                        setFragment(rekf);
                        return true;

                    case R.id.bottom_search:
                        setFragment(sf);
                        return true;

                    case R.id.bottom_timeline:
                        setFragment(tf);
                        return true;

                    default:
                        return false;
                }

            }
        });

        bottomNavigationView.setSelectedItemId(R.id.bottom_timeline);
        String notif = "";
        notif = getIntent().getStringExtra("Riwayat");
        if(notif == null){

        } else {
            Bundle bundle = new Bundle();
            bundle.putString("cuk", "f");

            rf.setArguments(bundle);
            setFragment(rf);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.drawer_bookmark) {
            setFragment(bf);
        } else if (id == R.id.drawer_riwayat) {
            setFragment(rf);
        } else if(id == R.id.drawer_signout){
            mAuth.signOut();
        } else if (id == R.id.drawer_acara){
            setFragment(af);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setFragment(Fragment fragment) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mListener);

    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mListener != null){

            mAuth.removeAuthStateListener(mListener);
        }
    }
}
