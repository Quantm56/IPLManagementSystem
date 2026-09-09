package model;

import java.sql.Date;

public class Match {
    private int matchId;
    private int team1Id;
    private int team2Id;
    private int venueId;
    private Date matchDate;
    private String status;

    public Match(int matchId, int team1Id, int team2Id, int venueId, Date matchDate, String status) {
        this.matchId = matchId;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.venueId = venueId;
        this.matchDate = matchDate;
        this.status = status;
    }

    public int getMatchId() { return matchId; }
    public int getTeam1Id() { return team1Id; }
    public int getTeam2Id() { return team2Id; }
    public int getVenueId() { return venueId; }
    public Date getMatchDate() { return matchDate; }
    public String getStatus() { return status; }
    @Override
    public String toString() {
        return "Match " + matchId + " (" + team1Id + " vs " + team2Id + ")";
    }
}