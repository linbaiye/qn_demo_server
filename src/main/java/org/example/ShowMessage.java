package org.example;

import io.netty.buffer.ByteBufAllocator;

public class ShowMessage implements Message {

    private final int id;

    private final Coordinate coordinate;

    public ShowMessage(int id, Coordinate coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[16];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.Show.value());
        buffer.writeInt(id);
        buffer.writeInt(coordinate.x());
        buffer.writeInt(coordinate.y());
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }
}
