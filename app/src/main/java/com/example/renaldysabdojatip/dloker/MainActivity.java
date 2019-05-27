package com.example.renaldysabdojatip.dloker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    //navigation
    TextView tnama, temail;
    CircleImageView pict;
    String snama, semail, url;

    View headerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_nav);

        //init fragment

        pf = new ProfileFragment();
        sf = new SearchFragment();
        bf = new BookmarkFragment();
        rf = new RiwayatFragment();
        tf = new TimelineFragment();
        rekf = new RekomendasiFragment();


        //init timeline fragment
        setFragment(tf);

        //firebase
        mAuth = FirebaseAuth.getInstance();

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
                Glide.with(getApplicationContext())
                        .load(url)
                        .into(pict);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


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
