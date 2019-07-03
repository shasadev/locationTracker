package sashastudios.lk.locationtracker;

import android.location.Location;
import android.location.LocationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    //firebase tools
    FirebaseDatabase firebaseDatabase;

    //location tools
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
    }
}
