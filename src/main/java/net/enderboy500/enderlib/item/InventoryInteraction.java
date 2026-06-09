package net.enderboy500.enderlib.item;

import net.minecraft.item.ItemStack;

public interface InventoryInteraction {
    void onSlotInteraction(ItemStack stack, boolean bl);
}
