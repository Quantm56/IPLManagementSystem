package model;

public class Venue {

    private int venueId;
    private String venueName;
    private String city;
    private int capacity;
    private double latitude;
    private double longitude;

    public Venue(int venueId, String venueName, String city,
                 int capacity, double latitude, double longitude) {

        this.venueId = venueId;
        this.venueName = venueName;
        this.city = city;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getVenueId() { return venueId; }
    public String getVenueName() { return venueName; }
    public String getCity() { return city; }
    public int getCapacity() { return capacity; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}