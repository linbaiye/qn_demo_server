package org.example;

public interface PlayerState {

    void handle(MoveInput moveInput);

    void update(int delta);
}
