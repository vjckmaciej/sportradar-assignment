package org.scoreboard;

public record Team(String teamName, String teamSlug) {
    public Team {
        if (teamName != null) {
            teamName = teamName.toUpperCase();
        }
        if (teamSlug != null) {
            teamSlug = teamSlug.toUpperCase();
        }
    }
}
