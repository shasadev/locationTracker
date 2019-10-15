package sashastudios.lk.locationtracker.activities.adminActivities;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import sashastudios.lk.locationtracker.LoginActivity;
import sashastudios.lk.locationtracker.R;
import sashastudios.lk.locationtracker.activities.userActivities.UserHomeActivity;
import sashastudios.lk.locationtracker.models.User;
import sashastudios.lk.locationtracker.services.AdminService;

public class AddUserActivity extends AppCompatActivity {

    //view elements
    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText password;
    EditText rePassword;
    EditText email;
    EditText dob;
    Spinner userType;
    Button onSubmitBtn;
    Button cancelRouteBtn;

    //process params
    int userTypeId;
    boolean isTypeSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        //set view elements
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.rePassword);
        email = (EditText) findViewById(R.id.email);
        userType = (Spinner) findViewById(R.id.userType);
        dob = (EditText) findViewById(R.id.dob);
        onSubmitBtn = (Button) findViewById(R.id.onSubmitBtn);
        cancelRouteBtn = (Button) findViewById(R.id.cancelUser);

        //spinner items
        String[] items = new String[] { "Admin", "User", "Customer" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String  item =  (String) parent.getItemAtPosition(position);
                if(item == "Admin")
                    userTypeId = 1;
                else if (item == "User")
                    userTypeId = 2;
                else
                    userTypeId = 3;
                isTypeSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //nothing selected for spinner
                isTypeSelected = false;
            }
        });

        onSubmitBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String pwd = password.getText().toString();
                String rpwd = rePassword.getText().toString();
                String un = userName.getText().toString();
                if( isTypeSelected){
                    User user = new User(0, userName.getText().toString(), userTypeId, password.getText().toString(),
                            email.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),
                            dob.getText().toString() );
                    submitUser(user);
                }
            }
        } );

        cancelRouteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getHomeIntent();
            }
        });
    }

    public void submitUser(User user){
        AdminService adminService = new AdminService();
        int isSuccessId = adminService.SubmitUser(user);
        if(isSuccessId == 0){
            Toast.makeText(AddUserActivity.this,"Something went wrong, please try again." , Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(AddUserActivity.this,"Successfully created user as " + isSuccessId + "." , Toast.LENGTH_SHORT).show();
            getHomeIntent();
        }
    }

    public void getHomeIntent(){
        Intent routeIntent = new Intent(this, AdminHomeActivity.class);
        startActivity(routeIntent);
    }
}
