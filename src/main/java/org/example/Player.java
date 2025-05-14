package org.example;


public class Player {
    private Vector2 coordinate;

    private final int id;

    private State state;

    private int stateSecondsLeft;

    private Direction direction;

    private final EventListener eventListener;

    public Player(int id, Vector2 coordinate, EventListener eventListener) {
        this.coordinate = coordinate;
        this.id = id;
        this.state = State.IDLE;
        this.direction = Direction.Down;
        this.eventListener = eventListener;
    }


    public void move(Vector2 coordinate, Direction direction) {
        if (!coordinate.equals(this.coordinate)) {
            this.state = State.MOVE;
            eventListener.onPlayerEvent(new PlayerMoveMessage(id, coordinate, direction));
        }
    }

    public void update(int delta) {
        if (stateSecondsLeft <= 0)
            return;
        stateSecondsLeft -= delta;
        if (stateSecondsLeft <= 0) {
            state = State.IDLE;
        }
    }

    public int getId() {
        return id;
    }

    public Vector2 getCoordinate() {
        return coordinate;
    }
}
