package org.example;

import io.netty.buffer.ByteBufAllocator;

public class LoginOkMessage implements Message {
    private final int id;

    private final Coordinate coordinate;

    public LoginOkMessage(int id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[16];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.LoginOk.value());
        buffer.writeInt(id);
        buffer.writeInt(coordinate.x());
        buffer.writeInt(coordinate.y());
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }
}
