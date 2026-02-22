package org.scoreboard;

public class Match {
    private final Team homeTeam;
    private final Team awayTeam;
    private final String matchKey;
    private int homeTeamScore;
    private int awayTeamScore;
    private int insertionCounter;

    public Match(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = 0;
        this.awayTeamScore = 0;
        this.matchKey = generateMatchKeyBasedOnTeamSlugs(homeTeam, awayTeam);
    }

    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeTeamScore = homeScore;
        this.awayTeamScore = awayScore;
    }

    public void setInsertionCounter(int insertionCounter) {
        this.insertionCounter = insertionCounter;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public int getInsertionCounter() {
        return insertionCounter;
    }

    private static String generateMatchKeyBasedOnTeamSlugs(Team homeTeam, Team awayTeam) {
        return homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
    }
}