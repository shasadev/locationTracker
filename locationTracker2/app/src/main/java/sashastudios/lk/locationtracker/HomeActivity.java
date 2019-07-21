package sashastudios.lk.locationtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //firebase tools
    FirebaseDatabase firebaseDatabase;

    //location tools
    LocationListener locationListener;

    //layout items
    //Button enterUserDetailsBtn;
    Button tripSetBtn;
//    Button tripEndDataBtn;
    Chronometer chronometer;

    //Is timer running
    Boolean isTimerRunning = false;

    // if route details set for the trip
    Boolean isRouteSet = false;

    //navigator
    private Toolbar toolbar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //initiate layout elements
//        enterUserDetailsBtn = findViewById(R.id.enterUserDetailsBtn);
        tripSetBtn = findViewById(R.id.tripSetBtn);
//        tripEndDataBtn = findViewById(R.id.tripEndDataBtn);
        chronometer = findViewById(R.id.chronometer);

//        tripEndDataBtn.setVisibility(View.GONE);

        //navigation-- not implemented


        IsRouteSet();
        //database instance
        firebaseDatabase = FirebaseDatabase.getInstance();

        //navigator
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //trace location
        locationListener = new android.location.LocationListener(){
            @Override
            public void onLocationChanged(Location location){
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle){

            }

            @Override
            public void onProviderEnabled(String s){

            }

            @Override
            public void onProviderDisabled(String s){

            }
        };




        tripSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });
    }

    //routes
    public void GetHome(){
        Intent routeIntent = new Intent(this, HomeActivity.class);
        startActivity(routeIntent);
    }

    public void GetRoute(){
        Intent routeIntent = new Intent(this, SetRouteDetails.class);
        startActivity(routeIntent);

    }

    public void GetEnd(){
        Intent routeIntent = new Intent(this, TripEndDataActivity.class);
        startActivity(routeIntent);
    }
    public void GetAbout(){
        Intent routeIntent = new Intent(this, HomeActivity.class);
        startActivity(routeIntent);
    }
    public void GetContact(){
        Intent routeIntent = new Intent(this, HomeActivity.class);
        startActivity(routeIntent);
    }

    public void IsRouteSet(){
        String loggedUser = getUserId();

        //DatabaseReference rotesRef = firebaseDatabase.getReference().child("RouteDetails").child("RouteDetails");
//        rotesRef.orderByChild("createdUserId").equalTo(loggedUser).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                System.out.println(dataSnapshot.getKey());
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //Toast.makeText(HomeActivity.this,"this is toast" , Toast.LENGTH_SHORT).show();
    }

    public String getUserId(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        return currentFirebaseUser.getUid();
    }

    public void startTimer(){
        if(!isTimerRunning){
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isTimerRunning = true;
            if(isTimerRunning){
                tripSetBtn.setText("Stop Trip");
            }
            if(!isTimerRunning){
                tripSetBtn.setText("Start Trip");
            }
        }

    }

    public void stopTimer(){
        if(isTimerRunning){
            chronometer.stop();
            isTimerRunning = false;
            if(isTimerRunning){
                tripSetBtn.setText("Stop Trip");
            }
            if(!isTimerRunning){
                tripSetBtn.setText("Start Trip");
                sendTripTime();
            }
        }
    }

    public void sendTripTime(){
//        tripEndDataBtn.setVisibility(View.VISIBLE);
    }

    //navigator
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            GetHome();
        } else if (id == R.id.nav_trip_details) {
            GetRoute();
        } else if (id == R.id.nav_trip_end) {
            GetEnd();
        } else if( id == R.id.nav_about){
            GetAbout();
        } else if ( id == R.id.nav_contact) {
            GetContact();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
