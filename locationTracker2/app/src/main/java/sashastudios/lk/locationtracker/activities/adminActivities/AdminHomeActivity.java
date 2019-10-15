package sashastudios.lk.locationtracker.activities.adminActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import sashastudios.lk.locationtracker.AdminSummaryActivity;
import sashastudios.lk.locationtracker.R;
import sashastudios.lk.locationtracker.models.BusLocation;
import sashastudios.lk.locationtracker.services.AdminService;

public class AdminHomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    //view elements
    Button addUser;
    Button addRoute;
    Button addBus;
    Button summary;

    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //set view elements
        addUser = (Button) findViewById(R.id.addUserBtn);
        addRoute = (Button) findViewById(R.id.addRouteBtn);
        addBus = (Button) findViewById(R.id.addBusBtn);
        summary = (Button) findViewById(R.id.summaryBtn);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddUserIntent();
            }
        });

        addRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddRouteIntent();
            }
        });

        addBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddBusIntent();
            }
        });

        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSummaryIntent();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap map){
        List<BusLocation> busLocations = new ArrayList<BusLocation>();
        AdminService adminService = new AdminService();
        busLocations = adminService.GetAllBusLocations();

        for(BusLocation busLocation : busLocations ){
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(busLocation.Latitude, busLocation.Longitude))
                    .title(busLocation.Number + " / " + busLocation.StartPoint + " - " + busLocation.EndPoint));
            LatLng pos=new LatLng(busLocation.Latitude,busLocation.Longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18.0f));

        }
    }

    public void getAddUserIntent(){
        Intent routeIntent = new Intent(this, AddUserActivity.class);
        startActivity(routeIntent);
    }
    public void getAddRouteIntent(){
        Intent routeIntent = new Intent(this, AddRouteActivity.class);
        startActivity(routeIntent);
    }
    public void getAddBusIntent(){
        Intent routeIntent = new Intent(this, AddBusActivity.class);
        startActivity(routeIntent);
    }
    public void getSummaryIntent(){
        Intent routeIntent = new Intent(this, AdminSummaryActivity.class);
        startActivity(routeIntent);
    }

}
