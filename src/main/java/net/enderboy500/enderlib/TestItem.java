package net.enderboy500.enderlib;

import net.enderboy500.enderlib.item.InventoryInteraction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TestItem extends Item implements InventoryInteraction {
    public TestItem(Properties properties) {
        super(properties);
    }

    @Override
    public void onSlotInteraction(ItemStack stack, Player player, boolean bl) {
        player.displayClientMessage(Component.literal("WORKS"), false);
    }
}
