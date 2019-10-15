package sashastudios.lk.locationtracker.models;

public class Bus {
    public int Id;
    public String Number;
    public int UserId;
    public int RouteId;

    public Bus(int id, String number, int userId, int routeId) {
        Id = id;
        Number = number;
        UserId = userId;
        RouteId = routeId;
    }
}
