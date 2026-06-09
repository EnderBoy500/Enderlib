package net.enderboy500.enderlib.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public class RightClickEquipmentCycleItem extends Item {

    public RightClickEquipmentCycleItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT) {
            if (this instanceof InventoryInteraction slotChangeFunction) {
                slotChangeFunction.onSlotInteraction(stack, true);
            }
            return true;
        }
        return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
    }
}
