package sashastudios.lk.locationtracker.services;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {
    //Databse properties
    public String server = "10.0.2.2"; //localhost ip for running device
    public String database = "LocationTracker"; //db name
    public String user = "appUser"; //user name for db user
    public String password = "123"; // password for db user
    //sql server connection string
    public String ConnectionURL = "jdbc:jtds:sqlserver://" + server + "/" + database + ";user=" + user+ ";password=" + password + ";";
    //jdts driver for sql server commands
    public String driverName = "net.sourceforge.jtds.jdbc.Driver";

    public Connection GetSqlConnection(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(ConnectionURL);
            return con;
        }
        catch (ClassNotFoundException cnfe){
            Log.d("Error","Can't load MySQL driver, exiting...");
            System.exit(-1);
            return null;
        }
        catch (SQLException se) {
            Log.e("Error:", se.getMessage());
            return null;
        }
    }
}
