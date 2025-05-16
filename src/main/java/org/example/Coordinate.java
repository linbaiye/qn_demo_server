package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public record Coordinate(int x, int y) {


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate vector2)) return false;
        return x == vector2.x && y == vector2.y;
    }

    public Coordinate move(Direction direction) {
        int tx = x + direction.XOffset();
        int ty = y + direction.YOffset();
        return new Coordinate(tx, ty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
