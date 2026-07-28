package net.enderboy500.enderlib.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface InventoryInteraction {
    void onSlotInteraction(ItemStack stack, Player player, boolean bl);
}
