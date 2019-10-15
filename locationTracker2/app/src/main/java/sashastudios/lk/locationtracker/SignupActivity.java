package sashastudios.lk.locationtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sashastudios.lk.locationtracker.activities.adminActivities.AddUserActivity;
import sashastudios.lk.locationtracker.activities.adminActivities.AdminHomeActivity;
import sashastudios.lk.locationtracker.models.User;
import sashastudios.lk.locationtracker.services.AdminService;
import sashastudios.lk.locationtracker.services.LoginService;

public class SignupActivity extends AppCompatActivity {

    //view elements
    EditText firstName;
    EditText lastName;
    EditText dob;
    EditText email;
    EditText userName;
    EditText password;
    EditText rePassword;
    Button submitBtn;
    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //set view elements
        firstName = findViewById(R.id.firstNameTxt);
        lastName = findViewById(R.id.lastNameTxt);
        dob = findViewById(R.id.dobTxt);
        userName = findViewById(R.id.usernameTxt);
        password = findViewById(R.id.pwdTxt);
        rePassword = findViewById(R.id.rpwdTxt);
        submitBtn = findViewById(R.id.signupSubmit);
        cancelBtn = findViewById(R.id.signupCancel);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogin();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = setUser();
                submitUser(user);
            }
        });

    }

    public User setUser(){
        String fname = firstName.getText().toString();
        String lName = lastName.getText().toString();
        String db = dob.getText().toString();
        String mail = lastName.getText().toString();
        String uName = userName.getText().toString();
        String pwd = password.getText().toString();
        String rpwd = rePassword.getText().toString();

        boolean pwdMatch = true;
        User user;

        if(pwd != rpwd){
            Toast.makeText(SignupActivity.this,"Passwords do not match." , Toast.LENGTH_SHORT).show();
            pwdMatch = false;
            return null;

        }
        else if( fname.length() > 0 && lName.length() > 0 && db.length() > 0 && mail.length() > 0 && uName.length() > 0 && pwd.length() > 0 && pwdMatch){
            user = new User(0, uName, 3, pwd, mail, fname, lName, db);
            return user;
        }
        else{
            Toast.makeText(SignupActivity.this,"You should fill all the fields." , Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public void submitUser(User user){
        AdminService adminService = new AdminService();
        int isSuccessInt = adminService.SubmitUser(user);

        if(isSuccessInt > 0)
            Toast.makeText(SignupActivity.this,"Something went wrong." , Toast.LENGTH_SHORT).show();
        else{
            Toast.makeText(SignupActivity.this,"Successfully signed up. Please login here." , Toast.LENGTH_SHORT).show();
            getLogin();
        }
    }

    public void getLogin(){
        Intent routeIntent = new Intent(this, LoginActivity.class);
        startActivity(routeIntent);
    }


}
