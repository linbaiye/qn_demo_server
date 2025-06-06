package org.example;

import io.netty.buffer.ByteBufAllocator;

public record PlayerMoveEvent(int playerId, Coordinate from, Direction dir, MoveAction moveAction) implements Message {
    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[24];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.Move.value());
        buffer.writeInt(playerId);
        buffer.writeInt(from().x());
        buffer.writeInt(from().y());
        buffer.writeInt(dir.value());
        buffer.writeInt(moveAction.value());
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }
}
