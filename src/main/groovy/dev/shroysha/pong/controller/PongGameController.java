package dev.shroysha.pong.controller;


import dev.shroysha.pong.model.PongBall;
import dev.shroysha.pong.model.PongBoundary;
import dev.shroysha.pong.model.PongBumper;
import dev.shroysha.pong.model.PongGame;
import lombok.Getter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class PongGameController extends KeyAdapter {

    @Getter
    private final PongGame pongGame = new PongGame();

    private void resetBall() {
        int height = PongBoundary.HEIGHT + (pongGame.getBottomBoundary().y - pongGame.getTopBoundary().y) / 2;
        pongGame.getBall().y = height - PongBall.HEIGHT / 2;
    }

    private void resetBumpers() {
        int height = PongBoundary.HEIGHT + (pongGame.getBottomBoundary().y - pongGame.getTopBoundary().y) / 2;
        int newy = height - (pongGame.getBumper1().height / 2);

        pongGame.getBumper1().y = newy;
        pongGame.getBumper2().y = newy;
    }

    private void refreshGame() {
        PongBall ball = pongGame.getBall();

        if (ball.isMovingRight()) {
            ball.x += PongBall.MOVEMENT;
        } else {
            ball.x -= PongBall.MOVEMENT;
        }

        if (ball.isMovingUp()) {
            ball.y += PongBall.MOVEMENT;
        } else {
            ball.y -= PongBall.MOVEMENT;
        }

        if (pongGame.getBumper1().isGoingUp() && bumperHitTop(pongGame.getBumper1())) {
            pongGame.getBumper1().y += PongBumper.MOVEMENT;
        } else if (pongGame.getBumper1().isGoingDown() && bumperHitBottom(pongGame.getBumper1())) {
            pongGame.getBumper1().y -= PongBumper.MOVEMENT;
        }

        if (pongGame.getBumper2().isGoingUp() && bumperHitTop(pongGame.getBumper2())) {
            pongGame.getBumper2().y += PongBumper.MOVEMENT;
        } else if (pongGame.getBumper2().isGoingDown() && bumperHitBottom(pongGame.getBumper2())) {
            pongGame.getBumper2().y -= PongBumper.MOVEMENT;
        }

        if (ballHitTopBumper(pongGame.getBumper1()) || ballHitTopBumper(pongGame.getBumper2()))
            ball.setMovingUp(true);

        if (ballHitBottomBumper(pongGame.getBumper1()) || ballHitBottomBumper(pongGame.getBumper2()))
            ball.setMovingRight(false);

        if (ballHitTop()) {
            ball.setMovingUp(false);
        } else if (ballHitBottom()) {
            ball.setMovingUp(true);
        }

        if (ballHitLeftBumper()) {
            ball.setMovingRight(true);
        } else if (ballHitRightBumper()) {
            ball.setMovingRight(false);
        }

        boolean hitLeftGoal = ballHitLeftGoal();
        if (hitLeftGoal) {
            pongGame.getPlayer2().setPoints(pongGame.getPlayer2().getPoints() + 1);
            resetBall();
            ball.setMovingRight(!ball.isMovingRight());
        }

        boolean hitRightGoal = ballHitRightGoal();
        if (hitRightGoal) {
            pongGame.getPlayer1().setPoints(pongGame.getPlayer1().getPoints() + 1);
            resetBall();
            ball.setMovingRight(!ball.isMovingRight());
        }
    }


    private boolean ballHitTop() {
        return pongGame.getBall().y == PongBoundary.HEIGHT;
    }

    private boolean ballHitBottom() {
        return pongGame.getBall().y == pongGame.getBall().y + PongBall.HEIGHT;
    }

    private boolean ballHitLeftBumper() {
        int check = pongGame.getBumper1().x + pongGame.getBumper1().width;
        PongBall ball = pongGame.getBall();
        if (check == ball.x) {
            boolean topHit = ball.y >= pongGame.getBumper1().y && ball.y <= pongGame.getBumper1().y + pongGame.getBumper1().height;
            boolean bottomHit = ball.y + PongBall.HEIGHT >= pongGame.getBumper1().y && ball.y + PongBall.HEIGHT <= pongGame.getBumper1().y + pongGame.getBumper1().height;
            return topHit || bottomHit;
        }
        return false;
    }

    private boolean ballHitRightBumper() {
        PongBall ball = pongGame.getBall();
        if (ball.x + PongBall.WIDTH == pongGame.getBumper2().x) {
            boolean topHit = ball.y > pongGame.getBumper2().y && ball.y < pongGame.getBumper2().y + pongGame.getBumper2().height;
            boolean bottomHit = ball.y + PongBall.HEIGHT > pongGame.getBumper2().y && ball.y + PongBall.HEIGHT < pongGame.getBumper2().y + pongGame.getBumper2().height;
            return topHit || bottomHit;
        }
        return false;
    }

    private boolean ballHitLeftGoal() {
        int check = pongGame.getGoal1().width;
        return check == pongGame.getBall().x;
    }

    private boolean ballHitRightGoal() {
        int check = pongGame.getBall().x + PongBall.WIDTH;
        return check == pongGame.getGoal2().x;
    }

    private boolean bumperHitBottom(PongBumper bumper) {
        return bumper.y + bumper.height < pongGame.getBottomBoundary().y;
    }

    private boolean bumperHitTop(PongBumper bumper) {
        return bumper.y > PongBoundary.HEIGHT;
    }

    private boolean ballHitTopBumper(PongBumper bumper) {
        PongBall ball = pongGame.getBall();
        int check = ball.y + PongBall.HEIGHT;
        if (check == bumper.y) {
            boolean insideLeft = ball.x < bumper.x + bumper.width && ball.x > bumper.x;
            boolean insideRight = ball.x + PongBall.WIDTH > bumper.x && ball.x + PongBall.WIDTH < bumper.x + bumper.width;
            return insideLeft || insideRight;
        }

        return false;
    }

    private boolean ballHitBottomBumper(PongBumper bumper) {
        PongBall ball = pongGame.getBall();
        if (bumper.y + bumper.height == ball.y) {
            boolean insideLeft = ball.x < bumper.x + bumper.width && ball.x > bumper.x;
            boolean insideRight = ball.x + PongBall.WIDTH > bumper.x && ball.x + PongBall.WIDTH < bumper.x + bumper.width;
            return insideLeft || insideRight;
        }

        return false;
    }

    public void moveBumper(PongBumper bumper, int inc) {
        bumper.y = bumper.y + inc;
    }


    public void keyPressed(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (keycode == KeyEvent.VK_W) {
            pongGame.getBumper1().setGoingUp(true);
        }
        if (keycode == KeyEvent.VK_S) {
            pongGame.getBumper1().setGoingDown(true);
        }
        if (keycode == KeyEvent.VK_UP) {
            pongGame.getBumper2().setGoingUp(true);
        }
        if (keycode == KeyEvent.VK_DOWN) {
            pongGame.getBumper2().setGoingDown(true);
        }
    }

    public void keyReleased(KeyEvent e) {
        int keycode = e.getKeyCode();
        if (keycode == KeyEvent.VK_W) {
            pongGame.getBumper1().setGoingUp(false);
        }
        if (keycode == KeyEvent.VK_S) {
            pongGame.getBumper1().setGoingDown(false);
        }
        if (keycode == KeyEvent.VK_UP) {
            pongGame.getBumper2().setGoingUp(false);
        }
        if (keycode == KeyEvent.VK_DOWN) {
            pongGame.getBumper2().setGoingDown(false);
        }
    }

    private void resetPoints() {
        pongGame.getPlayer1().setPoints(0);
        pongGame.getPlayer2().setPoints(0);
    }
}
