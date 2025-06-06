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

    private static final int walkMills = 840;
    private static final int runMillis = 600;

    public MoveState(Player player, MoveInput input) {
        this.player = player;
        moveInputList = new ArrayList<>();
        currentInput = input;
        millisLeft = getMoveMillis();
    }

    private int getMoveMillis() {
        if (player.isFootKungFuEnabled())
            return runMillis;
        return walkMills;
    }

    public void handle(MoveInput input) {
        moveInputList.add(input);
    }

    public void update(int delta) {
        if (millisLeft <= 0)
            return;
        if (millisLeft == getMoveMillis()) {
            if (!currentInput.from().equals(player.getCoordinate())) {
                log.debug("Rewind, from {} to {}, current {}.", currentInput.from(), currentInput.to(), player.getCoordinate());
                player.changeToIdle(player.getCoordinate());
                player.emitEvent(new PositionEvent(player.getId(), player.getCoordinate(), player.getDirection()));
                return;
            }
            log.debug("Moving from {} to {}.", player.getCoordinate(), currentInput.to());
            player.setDirection(currentInput.direction());
            player.emitEvent(new PlayerMoveEvent(player.getId(), player.getCoordinate(), player.getDirection(), player.isFootKungFuEnabled() ? MoveAction.Run : MoveAction.Walk));
//            log.debug("Sent move event.");
        }
        millisLeft -= delta;
        if (millisLeft <= 0) {
            player.setCoordinate(player.getCoordinate().move(currentInput.direction()));
            if (moveInputList.isEmpty()) {
                player.changeToIdle(player.getCoordinate());
//                player.emitEvent(new PositionEvent(player.getId(), player.getCoordinate(), player.getDirection()));
                return;
            }
            currentInput = moveInputList.remove(0);
            millisLeft = getMoveMillis();
        }
    }
}
