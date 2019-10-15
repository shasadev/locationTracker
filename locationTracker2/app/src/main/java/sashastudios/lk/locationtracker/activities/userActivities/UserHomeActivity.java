package sashastudios.lk.locationtracker.activities.userActivities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import sashastudios.lk.locationtracker.R;
import sashastudios.lk.locationtracker.activities.adminActivities.AddBusActivity;
import sashastudios.lk.locationtracker.services.UserService;

public class UserHomeActivity extends AppCompatActivity implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;

    double latitude;
    double longitude;

    int loggedUserId;

    Location userLocation;

    // view elements
    Button startTripBtn;
    Button endTripBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        Intent userHome = getIntent();
        loggedUserId = userHome.getIntExtra("USER_Id", 0);

        //set view elements
        startTripBtn = findViewById(R.id.startTripBtn);
        endTripBtn = findViewById(R.id.endTripBtn);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            userLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS},
                    1);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    1);
        }

        startTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserService userService = new UserService();
                int isSuccessId = userService.SubmitStartTrip(loggedUserId, userLocation);

                if(isSuccessId > 0){
                    Toast.makeText(UserHomeActivity.this,"Trip started successfully As " + isSuccessId , Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(UserHomeActivity.this,"Trip starting failed" , Toast.LENGTH_SHORT).show();
            }
        });

        endTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserService userService = new UserService();
                int isSuccessId = userService.SubmitEndTrip(loggedUserId, userLocation);

                if(isSuccessId > 0){
                    Toast.makeText(UserHomeActivity.this,"Trip ended successfully As " + isSuccessId , Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(UserHomeActivity.this,"Trip ending failed"  , Toast.LENGTH_SHORT).show();
            }

        });
    }
    @Override
    public void onLocationChanged(Location location) {
        latitude =  location.getLongitude();
        longitude = location.getLongitude();

        UserService userService = new UserService();
        userService.SubmitUserLocation(latitude, longitude, loggedUserId);

        userLocation = location;

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
    }
