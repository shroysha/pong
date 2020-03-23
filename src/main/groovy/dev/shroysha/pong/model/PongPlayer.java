package dev.shroysha.pong.model;


import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class PongPlayer {

    @Getter @NonNull
    private String name;
    @Getter @Setter
    private int points = 0;

}
