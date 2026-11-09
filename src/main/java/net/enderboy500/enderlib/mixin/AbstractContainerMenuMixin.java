package net.enderboy500.enderlib.mixin;
import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.item.InventoryInteraction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public class AbstractContainerMenuMixin {
    @Shadow @Final public NonNullList<Slot> slots;

    @Inject(method = "doClick", at = @At("HEAD"), cancellable = true)
    private void enderlib$internalOnSlotClick(int slotIndex, int button, ClickType actionType, Player player, CallbackInfo ci) {
        if (!EnderLib.canRightClickToCycle() && actionType == EnderLib.options.equipmentStateCycleKey.get().getClickType()) {
            Slot slot = this.slots.get(slotIndex);
            ItemStack stack = slot.getItem();
            if (stack.getItem() instanceof InventoryInteraction slotChangeFunction) {
                boolean bl = actionType == EnderLib.options.equipmentStateCycleKey.get().getClickType();
                slotChangeFunction.onSlotInteraction(stack, player, bl);
                ci.cancel();
            }
        }
    }
}
