package org.example;

public record MoveMessage(Vector2 from, Direction direction) {


    public static MoveMessage create(int x, int y, int dir) {
        return new MoveMessage(new Vector2(x, y), Direction.fromValue(dir));
    }

}
