import dao.UserDAO;

public class TestDB {
    public static void main(String[] args) {

        UserDAO dao = new UserDAO();

        dao.unfollowTeam(21, 1);
    }
}