package sashastudios.lk.locationtracker;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    //firebase tools
    FirebaseDatabase firebaseDatabase;

    //location tools
    LocationListener locationListener;

    //layout items
    Button enterUserDetailsBtn;
    Button tripSetBtn;
    Button tripEndDataBtn;
    Chronometer chronometer;

    //Is timer running
    Boolean isTimerRunning = false;

    // if route details set for the trip
    Boolean isRouteSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //initiate layout elements
        enterUserDetailsBtn = findViewById(R.id.enterUserDetailsBtn);
        tripSetBtn = findViewById(R.id.tripSetBtn);
        tripEndDataBtn = findViewById(R.id.tripEndDataBtn);
        chronometer = findViewById(R.id.chronometer);

        tripEndDataBtn.setVisibility(View.GONE);

        //device location data
        double latitude;
        double longitude;

        IsRouteSet();
        //database instance
        firebaseDatabase = FirebaseDatabase.getInstance();

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


        enterUserDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SetRouteDetails.class));

            }
        });

        tripSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();
            }
        });
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
        tripEndDataBtn.setVisibility(View.VISIBLE);
    }
}
