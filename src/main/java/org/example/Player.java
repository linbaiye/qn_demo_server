package org.example;

import lombok.Getter;

public class Player {

    private Vector2 coordinate;
    private final int id;
    public Player(int id, Vector2 coordinate) {
        this.coordinate = coordinate;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Vector2 getCoordinate() {
        return coordinate;
    }
}
