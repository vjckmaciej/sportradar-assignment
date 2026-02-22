package org.scoreboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardTest {

    @Test
    @DisplayName("Should successfully add a match with initial score 0-0")
    void shouldAddMatchWithInitialScoreZeroZero() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "MEX");
        Team away = new Team("Canada", "CAN");

        // When
        scoreboard.addMatch(home, away);

        // Then
        assertEquals(1, scoreboard.getOngoingMatchesCount(), "Scoreboard should have 1 match");

        scoreboard.getMatch(home, away).ifPresentOrElse(match -> {
            assertEquals(0, match.getHomeTeamScore());
            assertEquals(0, match.getAwayTeamScore());
        }, () -> fail("Match should be found on the board"));
    }

    @Test
    @DisplayName("Should throw exception when starting a match with null teams")
    void shouldThrowExceptionWhenTeamsAreNull() {
        // Given
        Scoreboard scoreboard = new Scoreboard();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.addMatch(null, new Team("Canada", "CAN")));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.addMatch(new Team("Mexico", "MEX"), null));
    }

    @Test
    @DisplayName("Should throw exception when adding the same match twice")
    void shouldThrowExceptionWhenMatchAlreadyExists() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "MEX");
        Team away = new Team("Canada", "CAN");
        scoreboard.addMatch(home, away);

        // When & Then
        assertThrows(IllegalStateException.class, () -> scoreboard.addMatch(home, away));
    }

    @Test
    @DisplayName("Should capitalize team name and slug upon creation")
    void shouldCapitalizeTeamData() {
        // Given
        Team team = new Team("mexico", "mex");

        // Then
        assertEquals("MEXICO", team.teamName());
        assertEquals("MEX", team.teamSlug());
    }

    @Test
    @DisplayName("Should successfully update match score")
    void shouldUpdateMatchScore() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "MEX");
        Team away = new Team("Canada", "CAN");
        scoreboard.addMatch(home, away);

        // When
        scoreboard.updateScore(home, away, 2, 1);

        // Then
        Match match = scoreboard.getMatch(home, away).orElseThrow();
        assertEquals(2, match.getHomeTeamScore());
        assertEquals(1, match.getAwayTeamScore());
    }

    @Test
    @DisplayName("Should throw exception when updating score with negative values")
    void shouldThrowExceptionWhenScoresAreNegative() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "MEX");
        Team away = new Team("Canada", "CAN");
        scoreboard.addMatch(home, away);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(home, away, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(home, away, 0, -1));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent match")
    void shouldThrowExceptionWhenMatchNotFound() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "MEX");
        Team away = new Team("Canada", "CAN");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.updateScore(home, away, 1, 1));
    }
}