package org.scoreboard;

import java.util.*;

public class Scoreboard {
    private final Map<String, Match> matches = new HashMap<>();
    private int globalCounter = 0;

    public Scoreboard() {
    }

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

    public List<Match> getTotalSummary() {
        return matches.values().stream()
                .sorted(Comparator
                        .comparingInt(Match::getTotalScore)
                        .thenComparingInt(Match::getInsertionCounter)
                        .reversed())
                .toList();
    }

    public void updateScore(Team homeTeam, Team awayTeam, int homeScore, int awayScore) {
        String key = homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
        Match match = matches.get(key);

        if (match == null) {
            throw new IllegalArgumentException("Match not found: " + key);
        }

        match.updateScore(homeScore, awayScore);
    }

    public void endMatch(Team homeTeam, Team awayTeam) {
        String key = homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
        if (matches.remove(key) == null) {
            throw new IllegalArgumentException("Cannot end match - not found: " + key);
        }
    }

    public Optional<Match> getMatch(Team homeTeam, Team awayTeam) {
        String key = homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
        return Optional.ofNullable(matches.get(key));
    }

    protected int getOngoingMatchesCount() {
        return matches.size();
    }

    private boolean isTeamInvolved(Match match, Team team) {
        return match.getHomeTeam().teamSlug().equals(team.teamSlug()) ||
                match.getAwayTeam().teamSlug().equals(team.teamSlug());
    }
}