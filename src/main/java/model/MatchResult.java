package model;

public class MatchResult {

    private int matchId;
    private int winnerTeamId;
    private String resultText;

    public MatchResult(int matchId, int winnerTeamId, String resultText) {
        this.matchId = matchId;
        this.winnerTeamId = winnerTeamId;
        this.resultText = resultText;
    }

    public int getMatchId() { return matchId; }
    public int getWinnerTeamId() { return winnerTeamId; }
    public String getResultText() { return resultText; }
}