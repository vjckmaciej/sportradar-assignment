package org.scoreboard;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scoreboard scoreboard = new Scoreboard();

        // create teams
        Team mex = new Team("Mexico", "MEX");
        Team can = new Team("Canada", "CAN");
        Team spa = new Team("Spain", "SPA");
        Team bra = new Team("Brazil", "BRA");

        // start matches
        scoreboard.addMatch(mex, can);
        scoreboard.addMatch(spa, bra);

        // update scores
        scoreboard.updateScore(mex, can, 0, 5);
        scoreboard.updateScore(spa, bra, 10, 2);

        // show summary
        System.out.println("scoreboard summary:");
        List<Match> summary = scoreboard.getTotalSummary();
        for (int i = 0; i < summary.size(); i++) {
            Match m = summary.get(i);
            System.out.println((i + 1) + ". " + m.getHomeTeam().teamName() + " " + m.getHomeTeamScore() +
                    " - " + m.getAwayTeam().teamName() + " " + m.getAwayTeamScore());
        }

        // end match
        scoreboard.endMatch(mex, can);
        System.out.println("match mex-can ended.");
    }
}
