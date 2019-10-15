package sashastudios.lk.locationtracker.activities.customerActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import sashastudios.lk.locationtracker.R;
import sashastudios.lk.locationtracker.activities.adminActivities.AddBusActivity;
import sashastudios.lk.locationtracker.models.BusLocation;
import sashastudios.lk.locationtracker.models.User;
import sashastudios.lk.locationtracker.services.AdminService;
import sashastudios.lk.locationtracker.services.CustomerService;

public class CustomerHomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    //view elements
    Spinner fromSpinner;
    Spinner toSpinner;
    Button searchTripBtn;

    //spinner
    ArrayList<String> spins = new ArrayList<String>();

    CustomerService customerService = new CustomerService();

    //process params
    String selectedFrom;
    String selectedTo;

    boolean isFromSelected = true;
    boolean isToSelected = true;

    boolean isfiltered = false;

    GoogleMap googleMap;

    List<BusLocation> newBusLocations = new ArrayList<BusLocation>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapCustomer);
        mapFragment.getMapAsync(this);

        // set view elements
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        searchTripBtn = findViewById(R.id.searchTripBtn);

        String[] spinItems = setSpinner().toArray(new String[spins.size()]);

        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinItems);

        fromSpinner.setAdapter(spinAdapter);
        fromSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String  item =  (String) parent.getItemAtPosition(position);

                selectedFrom = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing selected for spinner
                isFromSelected = false;
            }
        });

        toSpinner.setAdapter(spinAdapter);
        toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String  item =  (String) parent.getItemAtPosition(position);

                selectedTo = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing selected for spinner
                isToSelected = false;
            }
        });

        searchTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFromSelected && isToSelected){
                    newBusLocations = customerService.GetFilteredLocations(selectedFrom, selectedTo);
                    setUpMap();
                }else
                    Toast.makeText(CustomerHomeActivity.this,"You must select from and to points." , Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map){
        googleMap = map;
        setUpMap();
    }

    public ArrayList<String> setSpinner(){
        List<String> spinners = new ArrayList<String>();
        spinners = customerService.GetAllPoints();

        for (String item : spinners){
            String spin = item;
            spins.add(spin);
        }
        return  spins;
    }

    public void setUpMap(){
        googleMap.clear();
        List<BusLocation> busLocations = new ArrayList<BusLocation>();
        AdminService adminService = new AdminService();
        if(!isfiltered)
            busLocations = adminService.GetAllBusLocations();
        else
            busLocations = newBusLocations;

        for(BusLocation busLocation : busLocations ){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(busLocation.Latitude, busLocation.Longitude))
                    .title(busLocation.Number + " / " + busLocation.StartPoint + " - " + busLocation.EndPoint));
            LatLng pos=new LatLng(busLocation.Latitude,busLocation.Longitude);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18.0f));

        }
    }
}
