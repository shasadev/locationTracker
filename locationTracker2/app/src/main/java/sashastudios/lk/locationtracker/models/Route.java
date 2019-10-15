package sashastudios.lk.locationtracker.models;

public class Route {
    public int Id;
    public String Name;
    public int Number;
    public double Duration;
    public double StartLatitude;
    public double StartLongitude;
    public double EndLatitude;
    public double EndLongitude;
    public String StartPoint;
    public String EndPoint;

    public Route(String name, int number, double duration, double startLatitude, double startLongitude, double endLatitude, double endLongitude, String startPoint, String endPoint) {
        Name = name;
        Number = number;
        Duration = duration;
        StartLatitude = startLatitude;
        StartLongitude = startLongitude;
        EndLatitude = endLatitude;
        EndLongitude = endLongitude;
        StartPoint = startPoint;
        EndPoint = endPoint;
    }

    public Route(int id, String name, int number) {
        Id = id;
        Name = name;
        Number = number;
    }



}
