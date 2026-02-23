package org.scoreboard;

import java.util.*;

/**
 * Main entry point for the Football Scoreboard library.
 * Provides functionality to manage matches and retrieve live summaries.
 */
public class Scoreboard {
    private final Map<String, Match> matches = new HashMap<>();
    private int globalCounter = 0;

    public Scoreboard() {
    }

    /**
     * Starts a new match and adds it to the scoreboard.
     * Initial score is always 0-0.
     *
     * @param homeTeam the team playing at home
     * @param awayTeam the team playing away
     * @throws IllegalArgumentException if teams are null or the same
     * @throws IllegalStateException if either team is already involved in another match
     */
    public void addMatch(Team homeTeam, Team awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams cannot be null");
        }

        if (homeTeam.teamSlug().equals(awayTeam.teamSlug())) {
            throw new IllegalArgumentException("A team cannot play against itself");
        }

        boolean isAnyTeamBusy = matches.values().stream()
                .anyMatch(m -> isTeamInvolved(m, homeTeam) || isTeamInvolved(m, awayTeam));

        if (isAnyTeamBusy) {
            throw new IllegalStateException("One of the teams is already playing another match");
        }

        Match newMatch = new Match(homeTeam, awayTeam);
        globalCounter++;
        newMatch.setInsertionCounter(globalCounter);
        matches.put(newMatch.getMatchKey(), newMatch);
    }

    /**
     * Returns a list of all ongoing matches, sorted by:
     * 1. Total score (highest first)
     * 2. Recency (most recently started match first for ties in score)
     * * @return a sorted list of current matches
     */
    public List<Match> getTotalSummary() {
        return matches.values().stream()
                .sorted(Comparator
                        .comparingInt(Match::getTotalScore)
                        .thenComparingInt(Match::getInsertionCounter)
                        .reversed())
                .toList();
    }

    /**
     * Updates the score of an ongoing match.
     *
     * @param homeTeam home team of the match to update
     * @param awayTeam away team of the match to update
     * @param homeScore new score for the home team
     * @param awayScore new score for the away team
     * @throws IllegalArgumentException if match is not found or scores are negative
     */
    public void updateScore(Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
        String key = homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
        Match match = matches.get(key);

        if (match == null) {
            throw new IllegalArgumentException("Match not found: " + key);
        }

        match.updateScore(homeScore, awayScore);
    }

    /**
     * Finishes an ongoing match and removes it from the scoreboard.
     *
     * @param homeTeam home team of the match to finish
     * @param awayTeam away team of the match to finish
     * @throws IllegalArgumentException if the match is not found on the board
     */
    public void endMatch(Team homeTeam, Team awayTeam) {
        String key = homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
        if (matches.remove(key) == null) {
            throw new IllegalArgumentException("Cannot end match - not found: " + key);
        }
    }

    /**
     * Searches for a specific match on the scoreboard.
     *
     * @param homeTeam home team of the match
     * @param awayTeam away team of the match
     * @return an Optional containing the match if found, or empty if not
     */
    public Optional<Match> getMatch(Team homeTeam, Team awayTeam) {
        String key = homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
        return Optional.ofNullable(matches.get(key));
    }

    /**
     * Returns the current number of ongoing matches.
     */
    protected int getOngoingMatchesCount() {
        return matches.size();
    }

    /**
     * Checks if a specific team is currently playing in a given match.
     */
    private boolean isTeamInvolved(Match match, Team team) {
        return match.getHomeTeam().teamSlug().equals(team.teamSlug()) ||
                match.getAwayTeam().teamSlug().equals(team.teamSlug());
    }
}