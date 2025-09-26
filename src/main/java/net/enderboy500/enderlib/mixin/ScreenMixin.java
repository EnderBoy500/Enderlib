package net.enderboy500.enderlib.mixin;
import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.EnderLibConfig;
import net.enderboy500.enderlib.misc.CycleEquipmentStateBool;
import net.enderboy500.enderlib.misc.CycleEquipmentStateInt;
import net.enderboy500.enderlib.misc.SlotChangeFunction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenMixin {
    @Shadow @Final public DefaultedList<Slot> slots;

    @Inject(method = "internalOnSlotClick", at = @At("HEAD"), cancellable = true)
    private void internalOnSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (actionType == EnderLib.slotActionType()) {
            Slot slot = this.slots.get(slotIndex);
            ItemStack stack = slot.getStack();
            if (stack.getItem() instanceof CycleEquipmentStateBool cycleEquippmentStateBool) {
                boolean bl = actionType == EnderLib.slotActionType();
                cycleEquippmentStateBool.changeState(stack, bl);
                cycleEquippmentStateBool.updateEquippmentState(stack);
                ci.cancel();
            }
            if (stack.getItem() instanceof CycleEquipmentStateInt cycleEquipmentStateInt) {
                boolean bl = actionType == EnderLib.slotActionType();
                cycleEquipmentStateInt.changeState(stack, bl);
                cycleEquipmentStateInt.updateEquippmentState(stack);
                ci.cancel();
            }
            if (stack.getItem() instanceof SlotChangeFunction slotChangeFunction) {
                boolean bl = actionType == EnderLib.slotActionType();
                slotChangeFunction.slotChangeFunction(stack, bl);
                ci.cancel();
            }
        }
    }
}
