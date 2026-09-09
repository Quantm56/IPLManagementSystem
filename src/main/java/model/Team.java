package model;

public class Team {
    private int teamId;
    private String teamName;
    private String coach;
    private String captain;
    private int homeVenueId;

    // Constructor
    public Team(int teamId, String teamName, String coach, String captain, int homeVenueId) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.coach = coach;
        this.captain = captain;
        this.homeVenueId = homeVenueId;
    }

    // Getters
    public int getTeamId() { return teamId; }
    public String getTeamName() { return teamName; }
    public String getCoach() { return coach; }
    public String getCaptain() { return captain; }
    public int getHomeVenueId() { return homeVenueId; }

    // 🔥 VERY IMPORTANT (FIXES YOUR DROPDOWN ISSUE)
    @Override
    public String toString() {
        return teamName;
    }
}