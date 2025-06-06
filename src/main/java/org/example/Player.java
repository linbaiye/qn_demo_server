package org.example;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Player {
    private Coordinate coordinate;
    private final int id;
    private Direction direction;
    private PlayerState state;
    private final EventListener eventListener;

    @Getter
    private boolean footKungFuEnabled;

    private WeaponType weaponType;

    public Player(int id, Coordinate coordinate, EventListener eventListener) {
        this.coordinate = coordinate;
        this.id = id;
        this.direction = Direction.Down;
        this.eventListener = eventListener;
        this.state = new IdleState(this);
        this.footKungFuEnabled = false;
    }

    public void toggleFootKungFu() {
        footKungFuEnabled = !footKungFuEnabled;
        emitEvent(new FootKungFuEvent(id, footKungFuEnabled));
    }



    public void handle(MoveInput input) {
        state.handle(input);
    }

    public void emitEvent(PlayerMoveEvent event) {
        eventListener.onPlayerEvent(event);
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
        eventListener.onEquipWeaponEvent(new EquipWeaponEvent(this.id, this.weaponType));
    }

    public void emitEvent(FootKungFuEvent event) {
        eventListener.onFootKungFuEvent(event);
    }

    public void emitEvent(PositionEvent event) {
        eventListener.onPositionEvent(event);
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void changeToIdle(Coordinate coordinate)  {
        this.coordinate = coordinate;
        this.state = new IdleState(this);
    }

    public void update(int delta) {
        this.state.update(delta);
    }

    public void changeState(PlayerState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Direction getDirection() {
        return direction;
    }
}
