package sashastudios.lk.locationtracker.services;

import android.support.annotation.NonNull;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import sashastudios.lk.locationtracker.models.Bus;
import sashastudios.lk.locationtracker.models.BusLocation;
import sashastudios.lk.locationtracker.models.Route;
import sashastudios.lk.locationtracker.models.User;

public class AdminService {
    //sql connection
    Connection connection;

    public int SubmitUser(User user){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return 0;
            } else {
                String query = "EXEC [dbo].[Insert_User] " +
                        "@UserName = "+ user.UserName +
                        ", @UserTypeId=" + user.UserType +
                        ", @Password = " + user.PassWord +
                        ", @Email = " + user.Email +
                        ", @Dob = '" + user.DOB + "'"+
                        ", @FirstName = " + user.FirstName +
                        ", @LastName = " + user.LastName +
                        ", @UserId = " + user.UserId +
                        "";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int Id = resultSet.getInt(1);

                if(Id > 0){
                    return 1;
                }else
                    return 0;
            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return 0;
        }
    }

    public int SubmitRoute(Route route){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return 0;
            } else {
                String query = "EXEC [dbo].[Insert_Route] " +
                        " @RouteName= "+ route.Name +
                        ", @RouteNumber=" + route.Number +
                        ", @Duration = " + route.Duration +
                        ", @StartLat = " + route.StartLatitude +
                        ", @StartLog = '" + route.StartLongitude + "'"+
                        ", @EndLat = " + route.EndLatitude +
                        ", @EndLog = " + route.EndLongitude +
                        ", @StartPoint = " + route.StartPoint +
                        ", @EndPoint = " + route.EndPoint +
                        ", @UserId = " + 0 +
                        "";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int Id = resultSet.getInt(1);

                return Id;
            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return 0;
        }
    }

    public List<User> GetAllUsers(){
        List<User> users = new ArrayList<User>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return null;
            } else {
                String query = "EXEC [dbo].[Select_AllUsers]";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    int Id = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);

                    User user = new User(Id, firstName, lastName);

                    users.add((User) user);
                }

                return users;

            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return null;
        }
    }

    public List<Route> GetAllRoutes(){
        List<Route> routes = new ArrayList<Route>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return null;
            } else {
                String query = "EXEC [dbo].[Select_AllRoutes]";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next()){
                    int Id = resultSet.getInt(1);
                    String Name = resultSet.getString(2);
                    int Number = resultSet.getInt(3);

                    Route route = new Route(Id, Name, Number);

                    routes.add((Route) route);
                }

                return routes;

            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return null;
        }
    }

    public int SubmitBus(Bus bus){
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return 0;
            } else {
                String query = "EXEC [dbo].[Insert_Bus] " +
                       "@BusNumber =" + bus.Number +
                       ", @UserId =" + bus.UserId +
                       ", @RouteId =" + bus.RouteId +
                       ", @User =" + 0 +
                        "";
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query);

                resultSet.next();
                int Id = resultSet.getInt(1);

                return Id;
            }
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            return 0;
        }
    }

    public List<BusLocation> GetAllBusLocations(){
        List<BusLocation> busLocations = new ArrayList<BusLocation>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.GetSqlConnection();
            if (connection == null) {
                Log.d("Error", "Connection problem");
                return null;
            } else {
                String query = "EXEC [dbo].[Select_AllBusTrips]";
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
