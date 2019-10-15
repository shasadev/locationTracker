package sashastudios.lk.locationtracker.activities.adminActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import sashastudios.lk.locationtracker.R;
import sashastudios.lk.locationtracker.models.Bus;
import sashastudios.lk.locationtracker.models.Route;
import sashastudios.lk.locationtracker.models.User;
import sashastudios.lk.locationtracker.services.AdminService;

public class AddBusActivity extends AppCompatActivity {

    //view elements
    Spinner users;
    Spinner routes;
    EditText busNumber;
    Button submitBus;
    Button cancelBus;

    //services instance
    AdminService adminService = new AdminService();

    //spinner elements
    ArrayList<String> usersSpins = new ArrayList<String>();
    ArrayList<String> routesSpins = new ArrayList<String>();

    //process  variables
    boolean isRouteSelected = true;
    boolean isUserSelected = true;

    int selectedUserId = 0;
    int selectedRouteId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus);

        //set view elements
        users = findViewById(R.id.userSpinner);
        routes = findViewById(R.id.routeSpinner);
        busNumber = findViewById(R.id.busNoTxt);
        submitBus = findViewById(R.id.submitBus);
        cancelBus = findViewById(R.id.cancelBus);

        //set spinners
        String[] userItems = setUsersSpinner().toArray(new String[usersSpins.size()]);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, userItems);

        users.setAdapter(userAdapter);
        users.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String  item =  (String) parent.getItemAtPosition(position);

                String[] userParts = item.split(",");
                selectedUserId = Integer.parseInt(userParts[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing selected for spinner
                isUserSelected = false;
            }
        });

        String[] routeItems = setRoutesSpinner().toArray(new String[routesSpins.size()]);

        ArrayAdapter<String> routeAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, routeItems);

        routes.setAdapter(routeAdapter);
        routes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String  item =  (String) parent.getItemAtPosition(position);

                String[] routeParts = item.split(",");
                selectedRouteId = Integer.parseInt(routeParts[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing selected for spinner
                isRouteSelected = false;
            }
        });

        cancelBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAdminHomeIntent();
            }
        });

        submitBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bus bus = setBus();
                int isSuccessId = adminService.SubmitBus(bus);

                if(isSuccessId > 0){
                    Toast.makeText(AddBusActivity.this,"Bus added successfully As " + isSuccessId , Toast.LENGTH_SHORT).show();
                    getAdminHomeIntent();
                }
                else {
                    Toast.makeText(AddBusActivity.this,"Something went wrong. Please try again." , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public ArrayList<String> setUsersSpinner(){
        List<User> users = new ArrayList<User>();
        users = adminService.GetAllUsers();

        for (User user : users){
            String spin = user.UserId + ", " + user.FirstName + " " + user.LastName;
            usersSpins.add(spin);
        }
        return  usersSpins;
    }

    public ArrayList<String> setRoutesSpinner(){
    List<Route> routes = new ArrayList<Route>();
    routes = adminService.GetAllRoutes();

    for ( Route route : routes){
        String spin = route.Id + ", " + route.Name + " " + route.Number;
        routesSpins.add(spin);
    }
    return routesSpins;
    }

    public Bus setBus(){
        if(!isRouteSelected && !isUserSelected){
            Toast.makeText(AddBusActivity.this,"You must set route and user."  , Toast.LENGTH_SHORT).show();
            return null;
        }
        else if(busNumber.getText().toString().length() > 0){
            Bus bus = new Bus(0, busNumber.getText().toString(), selectedUserId, selectedRouteId);
            return bus;
        }
        else {
            Toast.makeText(AddBusActivity.this,"Something is wrong! Please recheck."  , Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void getAdminHomeIntent(){
        Intent routeIntent = new Intent(this, AdminHomeActivity.class);
        startActivity(routeIntent);
    }
}
