package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.item.CycleEquipmentStateBool;
import net.enderboy500.enderlib.item.CycleEquipmentStateInt;
import net.enderboy500.enderlib.item.SlotChangeFunction;
import net.enderboy500.enderlib.item.TogglableEquipmentVisibility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "onClicked", at = @At("HEAD"), cancellable = true)
    public void enderlib$itemSwap(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        if (clickType == ClickType.RIGHT && EnderLib.canRightClickToCycle()) {
            if (this instanceof CycleEquipmentStateBool cycleEquipmentStateBool) {
                cycleEquipmentStateBool.changeState(stack, true);
                cycleEquipmentStateBool.updateEquippmentState(stack);
                cir.setReturnValue(true);
            } else if (this instanceof SlotChangeFunction slotChangeFunction) {
                slotChangeFunction.slotChangeFunction(stack, true);
                cir.setReturnValue(true);
            } else if (this instanceof CycleEquipmentStateInt cycleEquipmentStateInt) {
                cycleEquipmentStateInt.changeState(stack, true);
                cycleEquipmentStateInt.updateEquippmentState(stack);
                cir.setReturnValue(true);
            } else if (this instanceof TogglableEquipmentVisibility togglableEquipmentVisibility) {
                togglableEquipmentVisibility.changeState(stack, true);
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }
}
