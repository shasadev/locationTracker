package sashastudios.lk.locationtracker.services;

import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sashastudios.lk.locationtracker.models.User;

public class LoginService {

    Connection connection; //connection for sql

    public User UserLogin(String userName, String password) {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return null;
            } else {
                String query = "EXEC [dbo].[Select_UserLogIn] @UserName = "+ userName + ", @Password = " + password ;
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int Id = resultSet.getInt(1);
                String Name = resultSet.getString(2);
                int Type = resultSet.getInt(3);

                User user = new User(Id, Name, Type, "");

                return user;
            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return null;
        }
    }
}
