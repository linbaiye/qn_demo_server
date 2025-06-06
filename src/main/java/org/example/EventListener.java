package org.example;

public interface EventListener {

    void onPlayerEvent(PlayerMoveEvent message);

    void onPositionEvent(PositionEvent event);

    void onFootKungFuEvent(FootKungFuEvent event);

    void onEquipWeaponEvent(EquipWeaponEvent event);
}
