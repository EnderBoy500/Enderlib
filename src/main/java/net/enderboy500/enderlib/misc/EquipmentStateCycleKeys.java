package net.enderboy500.enderlib.misc;

import net.minecraft.screen.slot.SlotActionType;

public enum EquipmentStateCycleKeys {
    Swap(SlotActionType.SWAP),
    QuickMove(SlotActionType.QUICK_MOVE),
    RightClick(SlotActionType.CLONE)
    ;

    private final SlotActionType key;


    EquipmentStateCycleKeys(SlotActionType key) {
        this.key = key;
    }

    public SlotActionType get() {
        return this.key;
    }
}
