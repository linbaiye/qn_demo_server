package org.example;

import io.netty.buffer.ByteBufAllocator;

public class ShowMessage implements Message {

    private final long id;

    private final Vector2 coordinate;

    public ShowMessage(long id, Vector2 coordinate) {
        this.id = id;
        this.coordinate = coordinate;
    }

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[12];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeLong(id);
        buffer.writeInt(coordinate.x());
        buffer.writeInt(coordinate.y());
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }
}
