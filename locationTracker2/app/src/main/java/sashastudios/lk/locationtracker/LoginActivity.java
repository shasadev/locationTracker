package sashastudios.lk.locationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sashastudios.lk.locationtracker.activities.adminActivities.AdminHomeActivity;
import sashastudios.lk.locationtracker.activities.customerActivities.CustomerHomeActivity;
import sashastudios.lk.locationtracker.activities.userActivities.UserHomeActivity;
import sashastudios.lk.locationtracker.models.User;
import sashastudios.lk.locationtracker.services.LoginService;
import sashastudios.lk.locationtracker.statics.UserTypes;

public class LoginActivity extends AppCompatActivity {

    //interface objects
    Button loginBtn;
    EditText userName;
    EditText password;
    Button signupBtn;

    // user types
    private UserTypes userTypes = new UserTypes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set login button
        loginBtn = findViewById(R.id.loginBtn);
        userName = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signupBtn = findViewById(R.id.signUpBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser(userName.getText().toString(), password.getText().toString());
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSignup();
            }
        });
    }

    public void validateUser(String userName, String password){
        LoginService loginService = new LoginService();
        User user = loginService.UserLogin(userName, password);

        if(user == null){
            Toast.makeText(LoginActivity.this,"Login Failed" , Toast.LENGTH_SHORT).show();
        }else {
            getHomeByUser(user);
        }
    }

    public void getHomeByUser(User user){
        if(user.UserType == userTypes.ADMIN_USER){
            Intent routeIntent = new Intent(this, AdminHomeActivity.class);
            startActivity(routeIntent);
        }
        else if(user.UserType == userTypes.USER_USER){
            Intent routeIntent = new Intent(this, UserHomeActivity.class);
            routeIntent.putExtra("USER_Id", user.UserId);
            startActivity(routeIntent);
        }else{
            Intent routeIntent = new Intent(this, CustomerHomeActivity.class);
            startActivity(routeIntent);
        }
    }

    public void getSignup(){
        Intent routeIntent = new Intent(this, SignupActivity.class);
        startActivity(routeIntent);
    }
}
