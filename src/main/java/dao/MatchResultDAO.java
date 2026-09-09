package dao;

import model.MatchResult;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class MatchResultDAO {

    // 🔵 INSERT
    public void addMatchResult(MatchResult result) {

        try {
            Connection conn = DBConnection.getConnection();

            // 🔥 CHECK IF RESULT EXISTS
            String checkQuery = "SELECT * FROM match_result WHERE match_id = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setInt(1, result.getMatchId());

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                // 🔥 UPDATE
                String updateQuery = "UPDATE match_result SET winner_team_id = ?, result_text = ? WHERE match_id = ?";
                PreparedStatement ps = conn.prepareStatement(updateQuery);

                ps.setInt(1, result.getWinnerTeamId());
                ps.setString(2, result.getResultText());
                ps.setInt(3, result.getMatchId());

                ps.executeUpdate();

                System.out.println("Result updated!");

            } else {
                // 🔥 INSERT
                String insertQuery = "INSERT INTO match_result VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(insertQuery);

                ps.setInt(1, result.getMatchId());
                ps.setInt(2, result.getWinnerTeamId());
                ps.setString(3, result.getResultText());

                ps.executeUpdate();

                System.out.println("Result inserted!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔵 SELECT (GET ALL)
    public List<MatchResult> getAllResults() {

        List<MatchResult> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM match_result";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                MatchResult result = new MatchResult(
                        rs.getInt("match_id"),
                        rs.getInt("winner_team_id"),
                        rs.getString("result_text")
                );

                list.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔵 UPDATE
    public void updateMatchResult(MatchResult result) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "UPDATE match_result SET winner_team_id = ?, result_text = ? WHERE match_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, result.getWinnerTeamId());
            ps.setString(2, result.getResultText());
            ps.setInt(3, result.getMatchId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Match result updated successfully!");
            } else {
                System.out.println("No result found for this match.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔵 DELETE
    public void deleteMatchResult(int matchId) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "DELETE FROM match_result WHERE match_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, matchId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Match result deleted successfully!");
            } else {
                System.out.println("No result found for this match.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}