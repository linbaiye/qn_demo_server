package org.example;

public enum Direction {
    Up(0),
    UpRight(1),
    Right(2),
    DownRight(3),
    Down(4),
    DownLeft(5),
    Left(6),
    UpLeft(7),
    ;
    private final int value;

    Direction(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static Direction fromValue(int v) {
        for (var type: values()) {
            if (type.value() == v)
                return type;
        }
        throw new IllegalArgumentException();
    }
}
