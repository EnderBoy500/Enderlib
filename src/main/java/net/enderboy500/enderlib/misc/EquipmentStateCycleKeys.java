package net.enderboy500.enderlib.misc;

import net.minecraft.world.inventory.ClickType;

public enum EquipmentStateCycleKeys {
    Swap(ClickType.SWAP),
    QuickMove(ClickType.QUICK_MOVE),
    RightClick(ClickType.CLONE)
    ;

    private final ClickType key;


    EquipmentStateCycleKeys(ClickType key) {
        this.key = key;
    }

    public ClickType get() {
        return this.key;
    }
}
