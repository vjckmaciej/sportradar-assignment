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

        Match newMatch = new Match(homeTeam, awayTeam);

        if (matches.containsKey(newMatch.getMatchKey())) {
            throw new IllegalStateException("Match already exists");
        }

        matches.put(newMatch.getMatchKey(), newMatch);
    }

    public Optional<Match> getMatch(Team homeTeam, Team awayTeam) {
        String key = homeTeam.teamSlug() + "-" + awayTeam.teamSlug();
        return Optional.ofNullable(matches.get(key));
    }

    protected int getOngoingMatchesCount() {
        return matches.size();
    }
}