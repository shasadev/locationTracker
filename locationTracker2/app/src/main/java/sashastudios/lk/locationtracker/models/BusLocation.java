package sashastudios.lk.locationtracker.models;

public class BusLocation {
    public int BusId;
    public String Number;
    public String StartPoint;
    public String EndPoint;
    public double Latitude;
    public double Longitude;


    public BusLocation(int id, String number, double latitude, double longitude, String startPoint, String endPoint) {
        BusId = id;
        Number = number;
        Latitude = latitude;
        Longitude = longitude;
        StartPoint = startPoint;
        EndPoint = endPoint;
    }
}
