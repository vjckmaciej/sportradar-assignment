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

    /**
     * Updates the current score.
     *
     * @param homeScore non-negative score for the home team
     * @param awayScore non-negative score for the away team
     * @throws IllegalArgumentException if any score is negative
     */
    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        this.homeTeamScore = homeScore;
        this.awayTeamScore = awayScore;
    }

    /**
     * Calculates total goals scored in the match.
     */
    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    public void setInsertionCounter(int insertionCounter) {
        this.insertionCounter = insertionCounter;
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