package sashastudios.lk.locationtracker.activities.adminActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sashastudios.lk.locationtracker.LoginActivity;
import sashastudios.lk.locationtracker.R;
import sashastudios.lk.locationtracker.activities.customerActivities.CustomerHomeActivity;
import sashastudios.lk.locationtracker.models.Route;
import sashastudios.lk.locationtracker.services.AdminService;

public class AddRouteActivity extends AppCompatActivity {

    //view elements
    EditText routeName;
    EditText routeNumber;
    EditText routeDuration;
    EditText startLat;
    EditText startLog;
    EditText endLat;
    EditText endLog;
    EditText startLocname;
    EditText endLocName;
    Button submitRoute;
    Button cancelRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_route);

        // set route elements
        routeName = (EditText) findViewById(R.id.routeNameTxt);
        routeNumber = (EditText) findViewById(R.id.routeNumberTxt);
        routeDuration = (EditText) findViewById(R.id.durationTxt);
        startLat = (EditText) findViewById(R.id.startLatTxt);
        startLog = (EditText) findViewById(R.id.startLogTxt);
        endLat = (EditText) findViewById(R.id.endLatTxt);
        endLog = (EditText) findViewById(R.id.endLogTxt);
        startLocname = (EditText) findViewById(R.id.startLocationName);
        endLocName = (EditText) findViewById(R.id.endLocationName);
        submitRoute = (Button) findViewById(R.id.submitRoute);
        cancelRoute = (Button) findViewById(R.id.cancelRoute);

        submitRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Route route = setRouteObject();
                submitroute(route);
            }
        });

        cancelRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHomeIntent();
            }
        });
    }

    public Route setRouteObject(){
        Route route = new Route(
                routeName.getText().toString(),
                Integer.parseInt(routeNumber.getText().toString()),
                Double.parseDouble(routeDuration.getText().toString()),
                Double.parseDouble(startLat.getText().toString()),
                Double.parseDouble(startLog.getText().toString()),
                Double.parseDouble(endLat.getText().toString()),
                Double.parseDouble(endLog.getText().toString()),
                startLocname.getText().toString(),
                endLocName.getText().toString()
        );

        return route;
    }

    public void submitroute(Route route){
        AdminService adminService = new AdminService();
        int isSuccessId = adminService.SubmitRoute(route);

        if(isSuccessId > 0){
            Toast.makeText(AddRouteActivity.this,"Rote added successfully" + isSuccessId , Toast.LENGTH_SHORT).show();
            getHomeIntent();
        }
        else {
            Toast.makeText(AddRouteActivity.this,"Something went wrong. Please try again." , Toast.LENGTH_SHORT).show();
        }
    }

    public void getHomeIntent(){
        Intent routeIntent = new Intent(this, AdminHomeActivity.class);
        startActivity(routeIntent);
    }
}
