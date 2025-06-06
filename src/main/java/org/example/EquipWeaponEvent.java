package org.example;

import io.netty.buffer.ByteBufAllocator;

public record EquipWeaponEvent(int playerId, WeaponType weaponType) implements Message{

    @Override
    public byte[] toBytes() {
        byte[] bytes = new byte[12];
        var buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeInt(MessageType.Equip.value());
        buffer.writeInt(playerId);
        buffer.writeInt(weaponType.value());
        buffer.readBytes(bytes);
        buffer.release();
        return bytes;
    }

}
