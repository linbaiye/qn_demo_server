package org.example;

public class IdleState implements PlayerState {

    private final Player player;

    public IdleState(Player player) {
        this.player = player;
    }

    @Override
    public void handle(MoveInput moveInput) {
        player.changeState(new MoveState(player, moveInput));
    }

    @Override
    public void update(int delta) {
    }

}
