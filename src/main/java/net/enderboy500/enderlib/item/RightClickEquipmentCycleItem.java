package net.enderboy500.enderlib.item;

import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class RightClickEquipmentCycleItem extends Item {

    public RightClickEquipmentCycleItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        if (clickType == ClickAction.SECONDARY) {
            if (this instanceof InventoryInteraction slotChangeFunction) {
                slotChangeFunction.onSlotInteraction(stack,player, true);
            }
            return true;
        }
        return super.overrideOtherStackedOnMe(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}
