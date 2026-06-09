package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.item.InventoryInteraction;
import net.enderboy500.enderlib.util.EnderlibTags;
import net.enderboy500.enderlib.util.skin.ItemSkinRegistry;
import net.enderboy500.enderlib.util.skin.ModifierSkin;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ActionResult;
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
            if (this instanceof InventoryInteraction slotChangeFunction) {
                slotChangeFunction.onSlotInteraction(stack, true);
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void enderlib$smith(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        if (player != null && player.isSneaking() && context.getWorld().getBlockState(context.getBlockPos()).isOf(Blocks.SMITHING_TABLE) && ItemSkinRegistry.getMap().containsKey(stack.getItem()) && !stack.isIn(EnderlibTags.SKIN_INCOMPATIBILITY)) {

            if (!stack.contains(EnderLibComponents.SKIN_ID)) stack.set(EnderLibComponents.SKIN_ID, 0);

            if (ItemSkinRegistry.getMap().containsKey(stack.getItem()) && stack.contains(EnderLibComponents.SKIN_ID)) {
                if (stack.get(EnderLibComponents.SKIN_ID) < ItemSkinRegistry.getMap().get(stack.getItem()).size()) {

/*                    if (ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID)) instanceof ModifierSkin modifierSkin) {
                        modifierSkin.modify(stack);
                    } else if (stack.get(EnderLibComponents.SKIN_ID) > 0 && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                        modifierSkin.resetDefaults(stack);
                    }*/
                    if (stack.get(EnderLibComponents.SKIN_ID) > 0 && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                        System.out.println("1");
                        modifierSkin.resetDefaults(stack);
                    }
                    if (ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID)) instanceof ModifierSkin modifierSkin) {
                        modifierSkin.modify(stack);
                    }
                    stack.set(DataComponentTypes.ITEM_MODEL, ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID)).getModelId());
                    stack.set(EnderLibComponents.SKIN_ID, stack.get(EnderLibComponents.SKIN_ID) + 1);
                } else {
                    if (stack.get(EnderLibComponents.SKIN_ID) == ItemSkinRegistry.getMap().get(stack.getItem()).size() && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                        modifierSkin.resetDefaults(stack);
                    }
                    stack.set(DataComponentTypes.ITEM_MODEL, stack.getDefaultComponents().get(DataComponentTypes.ITEM_MODEL));
                    stack.set(EnderLibComponents.SKIN_ID, 0);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }
}
