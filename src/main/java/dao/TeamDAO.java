package dao;

import model.Team;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class TeamDAO {

    public List<Team> getAllTeams() {

        List<Team> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM teams";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                Team t = new Team(
                        rs.getInt("team_id"),
                        rs.getString("team_name"),
                        rs.getString("coach"),
                        rs.getString("captain"),
                        rs.getInt("home_venue_id")
                );

                list.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 NEW METHOD (MULTIPLE BEST TEAMS)
    public List<Integer> getBestTeamIds() {

        List<Integer> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            CallableStatement cs = conn.prepareCall("{call get_best_performing_teams(?)}");

            cs.registerOutParameter(1, Types.REF_CURSOR);

            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);

            while (rs.next()) {
                list.add(rs.getInt("winner_team_id"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ---------------- EXISTING METHODS (UNCHANGED) ----------------

    public boolean addTeam(Team team) {

        try {
            Connection conn = DBConnection.getConnection();

            String query = "INSERT INTO teams VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, team.getTeamId());
            ps.setString(2, team.getTeamName());
            ps.setString(3, team.getCoach());
            ps.setString(4, team.getCaptain());
            ps.setInt(5, team.getHomeVenueId());

            ps.executeUpdate();

            conn.close();
            return true;

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {

            // 🔥 HANDLE UNIQUE CONSTRAINT
            if (e.getMessage().contains("home_venue")) {
                System.out.println("This venue is already assigned to another team.");
            } else {
                System.out.println("Duplicate entry detected.");
            }

            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteTeam(int teamId) {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "DELETE FROM teams WHERE team_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, teamId);
            ps.executeUpdate();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTeam(Team team) {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "UPDATE teams SET team_name = ?, coach = ?, captain = ?, home_venue_id = ? WHERE team_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, team.getTeamName());
            ps.setString(2, team.getCoach());
            ps.setString(3, team.getCaptain());
            ps.setInt(4, team.getHomeVenueId());
            ps.setInt(5, team.getTeamId());

            ps.executeUpdate();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFollowing(int userId, int teamId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM FOLLOWED_TEAMS WHERE USER_ID = ? AND TEAM_ID = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, teamId);

            ResultSet rs = ps.executeQuery();

            boolean exists = rs.next();

            con.close();
            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void followTeam(int userId, int teamId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO FOLLOWED_TEAMS (USER_ID, TEAM_ID) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, teamId);

            ps.executeUpdate();
            con.close();

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            System.out.println("Already followed");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unfollowTeam(int userId, int teamId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM FOLLOWED_TEAMS WHERE USER_ID = ? AND TEAM_ID = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, teamId);

            ps.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Team getTeamById(int id) {
        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT * FROM teams WHERE team_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Team(
                        rs.getInt("team_id"),
                        rs.getString("team_name"),
                        rs.getString("coach"),
                        rs.getString("captain"),
                        rs.getInt("home_venue_id")
                );
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Team> getFollowedTeams(int userId) {

        List<Team> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();

            String query =
                    "SELECT t.* FROM teams t " +
                            "JOIN followed_teams f ON t.team_id = f.team_id " +
                            "WHERE f.user_id = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Team t = new Team(
                        rs.getInt("team_id"),
                        rs.getString("team_name"),
                        rs.getString("coach"),
                        rs.getString("captain"),
                        rs.getInt("home_venue_id")
                );

                list.add(t);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 NEW METHOD → TEAM POPULARITY FUNCTION
    public int getTeamFollowers(int teamId) {

        int count = 0;

        try {
            Connection conn = DBConnection.getConnection();

            String query = "SELECT get_team_popularity(?) FROM dual";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setInt(1, teamId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}