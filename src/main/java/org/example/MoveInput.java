package org.example;

public record MoveInput(Vector2 from, Direction direction) {
    public static MoveInput create(int x, int y, int dir) {
        return new MoveInput(new Vector2(x, y), Direction.fromValue(dir));
    }
}
