package org.example;

import lombok.Getter;

public class Player {

    private Vector2 coordinate;
    private final long id;
    public Player(long id, Vector2 coordinate) {
        this.coordinate = coordinate;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Vector2 getCoordinate() {
        return coordinate;
    }
}
