package org.example;

import io.netty.buffer.ByteBufAllocator;

public record MoveMessage(Vector2 from, Direction direction) implements Message {


    public static MoveMessage create(int x, int y, int dir) {
        return new MoveMessage(new Vector2(x, y), Direction.fromValue(dir));
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[16];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.Move.value());
        buffer.writeInt(from().x());
        buffer.writeInt(from().y());
        buffer.writeInt(direction().value());
        return bytes;
    }
}
