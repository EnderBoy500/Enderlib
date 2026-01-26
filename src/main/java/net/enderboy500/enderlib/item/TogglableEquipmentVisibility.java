package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.EnderLibComponents;
import net.minecraft.item.ItemStack;

public interface TogglableEquipmentVisibility {

    default void changeState(ItemStack stack, boolean sneaking) {
        if (sneaking) {
            if (Boolean.TRUE.equals(stack.get(EnderLibComponents.EQUIPMENT_VISIBLE))) {
                stack.set(EnderLibComponents.EQUIPMENT_VISIBLE, false);
            } else {
                stack.set(EnderLibComponents.EQUIPMENT_VISIBLE, true);
            }
        }
    }

    default boolean isVisible(ItemStack stack) {
        return Boolean.TRUE.equals(stack.get(EnderLibComponents.EQUIPMENT_VISIBLE));
    }
}
