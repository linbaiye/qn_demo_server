package org.example;

public record MoveInput(Coordinate from, Direction direction) {

    public Coordinate to() {
        return from.move(direction);
    }

    public static MoveInput create(int x, int y, int dir) {
        return new MoveInput(new Coordinate(x, y), Direction.fromValue(dir));
    }
}
