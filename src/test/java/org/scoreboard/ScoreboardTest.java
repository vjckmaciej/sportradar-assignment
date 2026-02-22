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
        Team home = new Team("Mexico", "mexico");
        Team away = new Team("Canada", "canada");

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
        assertThrows(IllegalArgumentException.class, () -> scoreboard.addMatch(null, new Team("Canada", "can")));
        assertThrows(IllegalArgumentException.class, () -> scoreboard.addMatch(new Team("Mexico", "mex"), null));
    }

    @Test
    @DisplayName("Should throw exception when adding the same match twice")
    void shouldThrowExceptionWhenMatchAlreadyExists() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "mex");
        Team away = new Team("Canada", "can");
        scoreboard.addMatch(home, away);

        // When & Then
        assertThrows(IllegalStateException.class, () -> scoreboard.addMatch(home, away));
    }
}