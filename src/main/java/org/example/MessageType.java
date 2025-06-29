package org.example;

public enum MessageType {
    Login(0),
    Move(1),
    Show(2),
    LoginOk(3),
    Remove(4),
    POSITION(5),
    FootKungFu(6),

    Equip(7)
    ;
    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static MessageType fromValue(int v) {
        for (MessageType type: MessageType.values()) {
            if (type.value() == v)
                return type;
        }
        throw new IllegalArgumentException();
    }
}
