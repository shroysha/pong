package dev.shroysha.pong.model;

import lombok.Getter;
import lombok.Setter;

public class PongGame {

    public static final int WINNING_POINTS = 10;

    @Getter
    private final PongPlayer
            player1 = new PongPlayer("Player 1"),
            player2 = new PongPlayer("Player 2");
    @Getter
    private final PongBall ball = new PongBall();
    @Getter
    private final PongBumper
            bumper1 = new PongBumper(),
            bumper2 = new PongBumper();
    @Getter
    private final PongBoundary
            topBoundary = new PongBoundary(),
            bottomBoundary = new PongBoundary();
    @Getter
    private final PongGoal
            goal1 = new PongGoal(),
            goal2 = new PongGoal();

    @Getter @Setter
    private boolean isPlaying = false;

    public PongGame() {
        topBoundary.y = 0;
        goal1.x = 0;
    }

    public boolean hasWinner() {
        return getWinner() != null;
    }

    public PongPlayer getWinner() {
        if (player1.getPoints() == WINNING_POINTS) {
            return player1;
        } else if (player2.getPoints() == WINNING_POINTS) {
            return player2;
        } else {
            return null;
        }
    }
}
