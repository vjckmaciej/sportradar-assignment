package org.scoreboard;

public class Main {
    public static void main(String[] args) {
        Team chelsea = new Team("Chelsea", "CHE");
        Team arsenal = new Team("Arsenal", "ARS");
        Match match = new Match(chelsea, arsenal);
    }
}
