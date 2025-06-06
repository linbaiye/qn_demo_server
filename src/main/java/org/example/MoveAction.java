package org.example;

public enum MoveAction {
    Walk(0),
    Run(1),
    Fly(2),
    ;

    private final int v;

    MoveAction(int v) {
        this.v = v;
    }

    public int value() {
        return v;
    }
}

