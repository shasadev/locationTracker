package sashastudios.lk.locationtracker.services;

import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sashastudios.lk.locationtracker.models.BusLocation;
import sashastudios.lk.locationtracker.models.Route;

public class CustomerService {
    Connection connection;

    public List<String> GetAllPoints(){
        List<String> points = new ArrayList<String>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return null;
            } else {
                String query = "EXEC [dbo].[Select_AllPoints]";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    String Point = resultSet.getString(1);
                    points.add((String) Point);
                }

                return points;

            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return null;
        }
    }

    public List<BusLocation>  GetFilteredLocations(String from, String to){
        List<BusLocation> busLocations = new ArrayList<BusLocation>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return null;
            } else {
                String query = "EXEC [dbo].[dbo].[Select_FilteredBus]"
                        + "@From =" + from
                        + ", @To =" + to
                        + "";                        ;
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    int BusId = resultSet.getInt(1);
                    double Latitude = resultSet.getDouble(2);
                    double Longitude = resultSet.getDouble(3);
                    String BusNumber = resultSet.getString(4);
                    String StartPoint = resultSet.getString(5);
                    String EndPoint = resultSet.getString(6);

                    BusLocation busLocation = new BusLocation(BusId, BusNumber, Latitude, Longitude, StartPoint, EndPoint);

                    busLocations.add((BusLocation) busLocation);
                }

                return busLocations;

            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return null;
        }
    }
}
