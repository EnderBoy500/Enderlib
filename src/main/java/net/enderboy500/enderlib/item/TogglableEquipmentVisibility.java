package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.item.ItemStack;

public interface TogglableEquipmentVisibility {

    default void changeState(ItemStack stack, boolean sneaking) {
        if (sneaking) {
            if (Boolean.TRUE.equals(stack.get(EnderLib.EnderLibComponents.EQUIPMENT_VISIBLE))) {
                stack.set(EnderLib.EnderLibComponents.EQUIPMENT_VISIBLE, false);
            } else {
                stack.set(EnderLib.EnderLibComponents.EQUIPMENT_VISIBLE, true);
            }
        }
    }

    default boolean isVisible(ItemStack stack) {
        return Boolean.TRUE.equals(stack.get(EnderLib.EnderLibComponents.EQUIPMENT_VISIBLE));
    }
}
