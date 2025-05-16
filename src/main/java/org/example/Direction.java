package org.example;

import java.util.HashSet;
import java.util.Set;

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

    private static final Set<Direction> UP_DIR = Set.of(Up, UpLeft, UpRight);
    private static final Set<Direction> DOWN_DIR = Set.of(Down, DownLeft, DownRight);
    private static final Set<Direction> LEFT_DIR = Set.of(Left, UpLeft, DownLeft);
    private static final Set<Direction> RIGHT_DIR = Set.of(Right, UpRight, DownRight);

    public int XOffset() {
        return LEFT_DIR.contains(this) ? -1 : (RIGHT_DIR.contains(this) ? 1 : 0);
    }

    public int YOffset() {
        return UP_DIR.contains(this) ? -1 : (DOWN_DIR.contains(this) ? 1 : 0);
    }
}
