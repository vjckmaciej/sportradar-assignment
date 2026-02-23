A simple, in-memory library to manage and track live football match scores. Built with TDD.

Features:
- add a new match with an initial score of 0-0
- update scores for ongoing matches with built-in validation
- remove finished matches from the board
-  get a live summary of matches ordered by total score and recency

Business rules:
- a team cannot play against itself
- a team cannot participate in more than one match at the same time
- team slugs are automatically capitalized to prevent duplicates (e.g. "mex" vs "MEX")

Prerequisites
- jdk 17 or higher
- maven for dependency management

## how to run
this project uses the maven wrapper, so you don't need to install maven manually

### run tests
to verify the logic (12 tests):
```bash
# windows
mvnw.cmd test
# linux/mac
./mvnw test
```

### run demo
to see the live scoreboard summary on your console:

```bash
# windows
mvnw.cmd compile exec:java -Dexec.mainClass="org.scoreboard.Main"
# linux/mac
./mvnw compile exec:java -Dexec.mainClass="org.scoreboard.Main"
```