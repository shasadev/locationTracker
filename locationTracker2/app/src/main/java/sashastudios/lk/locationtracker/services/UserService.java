package sashastudios.lk.locationtracker.services;

import android.location.Location;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import sashastudios.lk.locationtracker.models.User;

public class UserService {
    Connection connection; //connection for sql

    public int SubmitUserLocation(double latitude, double longitude, int userId){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return 0;
            } else {
                String query = "EXEC [dbo].[Insert_UserLocation] " +
                        "@UserId = "+ userId +
                        ", @Latitude = " + latitude  +
                        ", @Longitude=" + longitude +
                        "";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int Id = resultSet.getInt(1);

                if(Id > 0){
                    return 1;
                }else {
                    return 0;
                }
            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return 0;
        }
    }

    public int SubmitStartTrip(int userId, Location location){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return 0;
            } else {
                String query = "EXEC [dbo].[Insert_UserTripStart] " +
                        "@UserId = "+ userId +
                        ", @Latitude = " + location.getLatitude()  +
                        ", @Longitude=" + location.getLongitude() +
                        "";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int Id = resultSet.getInt(1);

                if(Id > 0){
                    return 1;
                }else {
                    return 0;
                }
            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return 0;
        }
    }

    public int SubmitEndTrip(int userId, Location location){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return 0;
            } else {
                String query = "EXEC [dbo].[Update_UserTripEnd] " +
                        "@UserId = "+ userId +
                        ", @Latitude = " + location.getLatitude()  +
                        ", @Longitude=" + location.getLongitude() +
                        "";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int Id = resultSet.getInt(1);

                if(Id > 0){
                    return 1;
                }else {
                    return 0;
                }
            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return 0;
        }
    }
}
