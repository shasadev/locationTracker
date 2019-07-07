package sashastudios.lk.locationtracker;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

public class SetRouteDetails extends AppCompatActivity {

    //set layout elements
    Button routeDetailsSubmitBtn;
    EditText busNoEt;
    EditText driverIdEt;
    EditText routeIdEt;

    //firebase tools
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_route_details);

        //initialize layout elements
        routeDetailsSubmitBtn = findViewById(R.id.routeDetailsSubmitBtn);
        busNoEt = findViewById(R.id.busNoEt);
        driverIdEt = findViewById(R.id.driverIdEt);
        routeIdEt = findViewById(R.id.routeIdEt);

        //database instance
        firebaseDatabase = FirebaseDatabase.getInstance();

        routeDetailsSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertFirebaseRouteData();

            }
        });
    }

    public void InsertFirebaseRouteData(){
        DatabaseReference ref = firebaseDatabase.getReference().child("RouteDetails");
        String busNo = busNoEt.getText().toString();
        String driverId = busNoEt.getText().toString();
        String routeId = busNoEt.getText().toString();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        //Toast.makeText(SetRouteDetails.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        RouteDetails routeDetails = new RouteDetails(busNo, driverId, routeId, currentFirebaseUser.getUid());

        String key = ref.child("RouteDetails").push().getKey();

        Map<String, Object> routeDetailMap = routeDetails.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("RouteDetails", routeDetailMap);

        ref.updateChildren(childUpdates);

    }


}

@IgnoreExtraProperties
class RouteDetails {
    private String busNo;
    private String driverId;
    private String routeId;
    private String createdUserId;

    public RouteDetails(String busNo, String driverId, String routeId, String createdUserId ){
        this.busNo = busNo;
        this.driverId = driverId;
        this.routeId = routeId;
        this.createdUserId = createdUserId;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(String createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("busNo", busNo);
        result.put("driverId", driverId);
        result.put("routeId", routeId);
        result.put("createdUserId", createdUserId);
        return result;
    }
}
