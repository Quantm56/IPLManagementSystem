package dao;

import model.Match;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class MatchDAO {

    // 🔥 KEEP THIS (USED SOMEWHERE ELSE)
    public List<String> getAllMatches() {

        List<String> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String query =
                    "SELECT t1.team_name AS team1, t2.team_name AS team2 " +
                            "FROM matches m " +
                            "JOIN teams t1 ON m.team1_id = t1.team_id " +
                            "JOIN teams t2 ON m.team2_id = t2.team_id";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String match = rs.getString("team1") + " vs " + rs.getString("team2");
                list.add(match);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ✅ NEW METHOD (FOR ENTER RESULT SCREEN)
    public List<Match> getAllMatchesFull() {

        List<Match> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM matches";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Match m = new Match(
                        rs.getInt("match_id"),   // 🔥 THIS FIXES MATCH 0
                        rs.getInt("team1_id"),
                        rs.getInt("team2_id"),
                        rs.getInt("venue_id"),
                        rs.getDate("match_date"),
                        rs.getString("status")
                );

                list.add(m);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ================= ADD MATCH =================
    public void addMatch(Match match) {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO matches (match_id, team1_id, team2_id, venue_id, match_date, status) " +
                    "VALUES (match_seq.NEXTVAL, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, match.getTeam1Id());
            ps.setInt(2, match.getTeam2Id());
            ps.setInt(3, match.getVenueId());
            ps.setDate(4, match.getMatchDate());
            ps.setString(5, match.getStatus());

            ps.executeUpdate();

            System.out.println("Match added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE MATCH =================
    public void deleteMatch(int matchId) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "DELETE FROM matches WHERE match_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, matchId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Match deleted successfully!");
            } else {
                System.out.println("No match found.");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= UPDATE MATCH =================
    public void updateMatch(Match match) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "UPDATE matches SET team1_id = ?, team2_id = ?, venue_id = ?, match_date = ?, status = ? WHERE match_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, match.getTeam1Id());
            ps.setInt(2, match.getTeam2Id());
            ps.setInt(3, match.getVenueId());
            ps.setDate(4, match.getMatchDate());
            ps.setString(5, match.getStatus());
            ps.setInt(6, match.getMatchId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Match updated successfully!");
            } else {
                System.out.println("No match found.");
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ NEW METHOD (FOR RESULT FLOW)
    public void updateMatchStatus(int matchId, String status) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "UPDATE matches SET status = ? WHERE match_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, status);
            ps.setInt(2, matchId);

            ps.executeUpdate();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}