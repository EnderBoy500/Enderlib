package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.world.item.ItemStack;

public interface TogglableEquipmentVisibility extends InventoryInteraction{

    @Override
    default void onSlotInteraction(ItemStack stack, boolean bl) {
        if (bl) {
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
