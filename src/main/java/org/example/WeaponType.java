package org.example;

public enum WeaponType {
    Sword(0),
    Axe(1),
    ;
    private final int v;

    WeaponType(int v) {
        this.v = v;
    }

    public int value() {
        return v;
    }
    public static WeaponType fromValue(int v) {
        for (WeaponType type: WeaponType.values()) {
            if (type.value() == v)
                return type;
        }
        throw new IllegalArgumentException();
    }
}
