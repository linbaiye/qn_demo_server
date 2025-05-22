package org.example;

import io.netty.buffer.ByteBufAllocator;

public record PositionEvent(int playerId, Coordinate from, Direction dir) implements Message {
    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[20];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.POSITION.value());
        buffer.writeInt(playerId);
        buffer.writeInt(from().x());
        buffer.writeInt(from().y());
        buffer.writeInt(dir.value());
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }
}
