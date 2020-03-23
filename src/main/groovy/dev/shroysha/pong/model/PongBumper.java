package dev.shroysha.pong.model;


import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class PongBumper extends Rectangle {

    public static final int MOVEMENT = 10;
    public static final int WIDTH = 20, HEIGHT = 100;

    @Getter
    @Setter
    private boolean isGoingUp = false, isGoingDown = false;

    public PongBumper() {
        super(0, 0, WIDTH, HEIGHT);
    }


}
