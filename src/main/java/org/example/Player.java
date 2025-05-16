package org.example;


public class Player {
    private Coordinate coordinate;

    private final int id;

    private State state;

    private int millisLeft;

    private Direction direction;

    private final EventListener eventListener;

    public Player(int id, Coordinate coordinate, EventListener eventListener) {
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
        coordinate = coordinate.move(direction);
        if (millisLeft <= 0) {
            state = State.IDLE;
        }
    }

    public int getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
