package net.enderboy500.enderlib.item;

import net.minecraft.world.item.ItemStack;

public interface InventoryInteraction {
    void onSlotInteraction(ItemStack stack, boolean bl);
}
