package dao;

import model.Venue;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class VenueDAO {

    // ✅ GET ALL VENUES (FIXED LAT + LONG)
    public List<Venue> getAllVenues() {
        List<Venue> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM venues";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Venue v = new Venue(
                        rs.getInt("venue_id"),
                        rs.getString("venue_name"),
                        rs.getString("city"),
                        rs.getInt("capacity"),
                        rs.getDouble("latitude"),   // 🔥 FIX
                        rs.getDouble("longitude")   // 🔥 FIX
                );

                list.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ ADD VENUE (KEEPING YOUR LOGIC — BUT SAFE DEFAULT)
    public void addVenue(Venue venue) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO venues (venue_id, venue_name, city, capacity, latitude, longitude) " +
                    "VALUES (venue_seq.NEXTVAL, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, venue.getVenueName());
            ps.setString(2, venue.getCity());
            ps.setInt(3, venue.getCapacity());

            // 🔥 IMPORTANT FIX: allow proper coords
            ps.setDouble(4, venue.getLatitude());
            ps.setDouble(5, venue.getLongitude());

            ps.executeUpdate();

            System.out.println("Venue added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ DELETE VENUE
    public void deleteVenue(int venueId) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "DELETE FROM venues WHERE venue_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, venueId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Venue deleted successfully!");
            } else {
                System.out.println("No venue found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ UPDATE VENUE (NOW ALSO UPDATES LAT + LONG)
    public void updateVenue(Venue venue) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "UPDATE venues SET venue_name = ?, city = ?, capacity = ?, latitude = ?, longitude = ? WHERE venue_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, venue.getVenueName());
            ps.setString(2, venue.getCity());
            ps.setInt(3, venue.getCapacity());
            ps.setDouble(4, venue.getLatitude());   // 🔥 FIX
            ps.setDouble(5, venue.getLongitude());  // 🔥 FIX
            ps.setInt(6, venue.getVenueId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Venue updated successfully!");
            } else {
                System.out.println("No venue found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ FULL INSERT (UNCHANGED — ALREADY CORRECT)
    public void addVenueFull(Venue venue) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO venues VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, venue.getVenueId());
            ps.setString(2, venue.getVenueName());
            ps.setString(3, venue.getCity());
            ps.setInt(4, venue.getCapacity());
            ps.setDouble(5, venue.getLatitude());
            ps.setDouble(6, venue.getLongitude());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}