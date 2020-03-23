package dev.shroysha.pong.model;


import lombok.Getter;
import lombok.Setter;

import java.awt.*;


public class PongBall extends Rectangle {

    public static final int
            WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int MOVEMENT = 5;
    @Getter
    @Setter
    private boolean isMovingRight;
    @Getter
    @Setter
    private boolean isMovingUp;

    public PongBall() {
        super(0, 0, WIDTH, HEIGHT);
    }
}
