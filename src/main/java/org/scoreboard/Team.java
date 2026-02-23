package org.scoreboard;

/**
 * Represents a football team.
 * Names and slugs are automatically converted to uppercase for consistency.
 *
 * @param teamName full name of the team (e.g. "Mexico")
 * @param teamSlug unique short identifier (e.g. "MEX")
 */
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
