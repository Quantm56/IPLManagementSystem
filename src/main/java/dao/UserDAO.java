package dao;

import util.DBConnection;
import model.User;
import model.Post;

import java.sql.*;
import java.util.*;

public class UserDAO {

    // ================= REGISTER =================
    public void registerUser(User user) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO USERS (USER_ID, USERNAME, EMAIL, PASSWORD, ROLE) " +
                    "VALUES (user_seq.NEXTVAL, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());

            ps.executeUpdate();
            System.out.println("User registered successfully");

            con.close();
        } catch (Exception e) {
            System.out.println("Error in registerUser");
            e.printStackTrace();
        }
    }

    // ================= LOGIN =================
    public User loginUser(String username, String password) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getInt("USER_ID"),
                        rs.getString("USERNAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PASSWORD"),
                        rs.getString("ROLE")
                );

                System.out.println("Login successful");
                con.close();
                return user;
            }

            con.close();

        } catch (Exception e) {
            System.out.println("Error in loginUser");
            e.printStackTrace();
        }
        return null;
    }

    // ================= FOLLOW TEAM (FIXED) =================
    public void followTeam(int userId, int teamId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO FOLLOWED_TEAMS (USER_ID, TEAM_ID) VALUES (?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, teamId);

            ps.executeUpdate();
            System.out.println("Team followed");

            con.close();

        } catch (SQLIntegrityConstraintViolationException e) {
            // 🔥 THIS FIXES YOUR CRASH
            System.out.println("Already followed (ignored)");

        } catch (Exception e) {
            System.out.println("Error in followTeam");
            e.printStackTrace();
        }
    }

    // ================= CREATE POST =================
    public void createPost(int userId, String content) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO POSTS (POST_ID, USER_ID, CONTENT) " +
                    "VALUES (post_seq.NEXTVAL, ?, ?)";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setString(2, content);

            ps.executeUpdate();
            System.out.println("Post created");

            con.close();
        } catch (Exception e) {
            System.out.println("Error in createPost");
            e.printStackTrace();
        }
    }

    // ================= LIKE =================
    public void likePost(int userId, int postId) {

        Connection con = null;

        try {
            con = DBConnection.getConnection();

            String query = "INSERT INTO LIKES (USER_ID, POST_ID) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, userId);
            ps.setInt(2, postId);

            ps.executeUpdate();

            System.out.println("Post liked");

        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // 🔥 THIS HANDLES DUPLICATE LIKE
            System.out.println("Already liked");

        } catch (Exception e) {
            System.out.println("Error in likePost");
            e.printStackTrace();

        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception ignored) {}
        }
    }

    // ================= GET POSTS =================
    public List<Post> getAllPosts() {
        List<Post> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM POSTS ORDER BY CREATED_AT DESC";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("POST_ID"),
                        rs.getInt("USER_ID"),
                        rs.getString("CONTENT")
                );
                list.add(p);
            }

            con.close();

        } catch (Exception e) {
            System.out.println("Error in getAllPosts");
            e.printStackTrace();
        }

        return list;
    }

    // ================= DELETE POST =================
    public void deletePost(int userId, int postId) {
        try {
            Connection con = DBConnection.getConnection();

            String deleteLikes = "DELETE FROM LIKES WHERE POST_ID = ?";
            PreparedStatement ps1 = con.prepareStatement(deleteLikes);
            ps1.setInt(1, postId);
            ps1.executeUpdate();

            String deletePost = "DELETE FROM POSTS WHERE POST_ID = ? AND USER_ID = ?";
            PreparedStatement ps2 = con.prepareStatement(deletePost);
            ps2.setInt(1, postId);
            ps2.setInt(2, userId);
            ps2.executeUpdate();

            System.out.println("Post deleted");

            con.close();

        } catch (Exception e) {
            System.out.println("Error in deletePost");
            e.printStackTrace();
        }
    }

    // ================= UNFOLLOW =================
    public void unfollowTeam(int userId, int teamId) {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM FOLLOWED_TEAMS WHERE USER_ID = ? AND TEAM_ID = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, teamId);

            ps.executeUpdate();
            System.out.println("Team unfollowed");

            con.close();
        } catch (Exception e) {
            System.out.println("Error in unfollowTeam");
            e.printStackTrace();
        }
    }
    public int getLikeCount(int postId) {
        int count = 0;

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT COUNT(*) FROM LIKES WHERE POST_ID = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, postId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            con.close();

        } catch (Exception e) {
            System.out.println("Error in getLikeCount");
            e.printStackTrace();
        }

        return count;
    }
}