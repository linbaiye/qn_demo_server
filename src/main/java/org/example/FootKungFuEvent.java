package org.example;

import io.netty.buffer.ByteBufAllocator;

public record FootKungFuEvent(int playerId, boolean enable) implements Message {
    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[5];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.FootKungFu.value());
        buffer.writeBoolean(enable);
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }
}
