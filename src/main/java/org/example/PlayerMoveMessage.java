package org.example;

import io.netty.buffer.ByteBufAllocator;

public record PlayerMoveMessage(int playerId, Vector2 from, Direction dir) implements Message {
    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[16];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.Move.value());
        buffer.writeInt(from().x());
        buffer.writeInt(from().y());
        buffer.writeInt(dir.value());
        return bytes;
    }

}
