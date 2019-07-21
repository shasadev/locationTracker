package sashastudios.lk.locationtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

public class TripEndDataActivity extends AppCompatActivity {

    //layout
    Button endDetailsSubmitBtn;
    EditText amountEt;
    EditText otherDataEt;

    //firebase tools
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_end_data);

        //initialize layout elements
        endDetailsSubmitBtn = findViewById(R.id.endDetailsSubmitBtn);
        amountEt = findViewById(R.id.amountEt);
        otherDataEt = findViewById(R.id.otherEt);

        //database instance
        firebaseDatabase = FirebaseDatabase.getInstance();

        endDetailsSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertFirebaseEndData();

            }
        });
    }

    public void InsertFirebaseEndData(){
        DatabaseReference ref = firebaseDatabase.getReference().child("EndDetails");
        String amount = amountEt.getText().toString();
        String otherData = otherDataEt.getText().toString();

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        //Toast.makeText(SetRouteDetails.this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        EndDetails endDetails = new EndDetails(amount, otherData, currentFirebaseUser.getUid());

        String key = ref.child("EndDetails").push().getKey();

        Map<String, Object> endDetailMap = endDetails.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("EndDetails", endDetailMap);

        ref.updateChildren(childUpdates);
    }
}


@IgnoreExtraProperties
class EndDetails {
    private String amount;
    private String otherData;
    private String createdUserId;

    public EndDetails(String amount, String otherData, String createdUserId ){
        this.amount = amount;
        this.otherData = otherData;
        this.createdUserId = createdUserId;
    }

    public String getBusNo() {
        return amount;
    }

    public void setBusNo(String busNo) {
        this.amount = busNo;
    }

    public String getDriverId() {
        return otherData;
    }

    public void setDriverId(String driverId) {
        this.otherData = driverId;
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
        result.put("busNo", amount);
        result.put("driverId", otherData);
        result.put("createdUserId", createdUserId);
        return result;
    }
}