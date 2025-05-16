package org.example;


public class Player {
    private Vector2 coordinate;

    private final int id;

    private State state;

    private int millisLeft;

    private Direction direction;

    private final EventListener eventListener;

    public Player(int id, Vector2 coordinate, EventListener eventListener) {
        this.coordinate = coordinate;
        this.id = id;
        this.state = State.IDLE;
        this.direction = Direction.Down;
        this.eventListener = eventListener;
    }


    public void move(MoveInput input) {
        this.state = State.MOVE;
        direction = input.direction();
        millisLeft = 840;
        eventListener.onPlayerEvent(new PlayerMoveMessage(id, coordinate, direction));
    }


    public void update(int delta) {
        if (millisLeft <= 0)
            return;
        millisLeft -= delta;
        if (millisLeft <= 0) {
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
