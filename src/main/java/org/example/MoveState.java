package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MoveState implements PlayerState {
    private final Player player;

    private final List<MoveInput> moveInputList;

    private int millisLeft;

    private MoveInput currentInput;

    public MoveState(Player player, MoveInput input) {
        this.player = player;
        moveInputList = new ArrayList<>();
        currentInput = input;
        millisLeft = 840;
    }

    public void handle(MoveInput input) {
        moveInputList.add(input);
    }

    public void update(int delta) {
        if (millisLeft <= 0)
            return;
        if (millisLeft == 840) {
            if (!currentInput.from().equals(player.getCoordinate())) {
                player.changeToIdle();
                player.emitEvent(new PositionEvent());
                return;
            }
            player.setDirection(currentInput.direction());
            player.emitEvent(new PlayerMoveEvent(player.getId(), player.getCoordinate(), player.getDirection()));
            log.debug("Sent move event.");
        }
        millisLeft -= delta;
        if (millisLeft == 0) {
            if (moveInputList.isEmpty()) {
                player.changeToIdle(player.getCoordinate().move(currentInput.direction()));
                return;
            }
            currentInput = moveInputList.remove(0);
            player.setCoordinate(player.getCoordinate().move(currentInput.direction()));
            millisLeft = 840;
        }
    }
}
