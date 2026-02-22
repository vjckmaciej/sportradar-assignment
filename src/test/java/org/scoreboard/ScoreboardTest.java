package org.scoreboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    @DisplayName("Should successfully end a match and remove it from scoreboard")
    void shouldEndMatch() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "MEX");
        Team away = new Team("Canada", "CAN");
        scoreboard.addMatch(home, away);

        // When
        scoreboard.endMatch(home, away);

        // Then
        assertEquals(0, scoreboard.getOngoingMatchesCount(), "Scoreboard should be empty after ending the match");
        assertTrue(scoreboard.getMatch(home, away).isEmpty(), "Match should no longer be accessible");
    }

    @Test
    @DisplayName("Should throw exception when trying to end a match that does not exist")
    void shouldThrowExceptionWhenEndingNonExistentMatch() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team home = new Team("Mexico", "MEX");
        Team away = new Team("Canada", "CAN");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> scoreboard.endMatch(home, away));
    }

    @Test
    @DisplayName("Should return summary sorted by total score and recency (The World Cup Example)")
    void shouldReturnSummarySortedByScoreAndRecency() {
        // Given
        Scoreboard scoreboard = new Scoreboard();
        Team mex = new Team("Mexico", "MEX");
        Team can = new Team("Canada", "CAN");
        Team spa = new Team("Spain", "SPA");
        Team bra = new Team("Brazil", "BRA");
        Team ger = new Team("Germany", "GER");
        Team fra = new Team("France", "FRA");
        Team uru = new Team("Uruguay", "URU");
        Team ita = new Team("Italy", "ITA");
        Team arg = new Team("Argentina", "ARG");
        Team aus = new Team("Australia", "AUS");

        scoreboard.addMatch(mex, can);
        scoreboard.addMatch(spa, bra);
        scoreboard.addMatch(ger, fra);
        scoreboard.addMatch(uru, ita);
        scoreboard.addMatch(arg, aus);

        scoreboard.updateScore(mex, can, 0, 5);
        scoreboard.updateScore(spa, bra, 10, 2);
        scoreboard.updateScore(ger, fra, 2, 2);
        scoreboard.updateScore(uru, ita, 6, 6);
        scoreboard.updateScore(arg, aus, 3, 1);

        // When
        List<Match> summary = scoreboard.getTotalSummary();

        // Then
        assertEquals(5, summary.size());
        assertEquals("URU", summary.get(0).getHomeTeam().teamSlug());
        assertEquals("SPA", summary.get(1).getHomeTeam().teamSlug());
        assertEquals("MEX", summary.get(2).getHomeTeam().teamSlug());
        assertEquals("ARG", summary.get(3).getHomeTeam().teamSlug());
        assertEquals("GER", summary.get(4).getHomeTeam().teamSlug());
    }
}