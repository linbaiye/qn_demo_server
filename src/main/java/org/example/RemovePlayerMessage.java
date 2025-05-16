package org.example;

import io.netty.buffer.ByteBufAllocator;

public record RemovePlayerMessage(int id) implements Message {

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[8];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.Remove.value());
        buffer.writeInt(id);
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }
}
