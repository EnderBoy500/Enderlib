package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.item.InventoryInteraction;
import net.enderboy500.enderlib.util.EnderlibTags;
import net.enderboy500.enderlib.util.skin.ItemSkinRegistry;
import net.enderboy500.enderlib.util.skin.ModifierSkin;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "overrideOtherStackedOnMe", at = @At("HEAD"), cancellable = true)
    public void enderlib$itemSwap(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        if (clickType == ClickAction.SECONDARY && EnderLib.canRightClickToCycle()) {
            if (this instanceof InventoryInteraction slotChangeFunction) {
                slotChangeFunction.onSlotInteraction(stack, true);
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$smith(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        if (player != null && player.isShiftKeyDown() && context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.SMITHING_TABLE) && ItemSkinRegistry.getMap().containsKey(stack.getItem()) && !stack.is(EnderlibTags.SKIN_INCOMPATIBILITY)) {

            if (!stack.has(EnderLibComponents.SKIN_ID)) stack.set(EnderLibComponents.SKIN_ID, 0);

            if (ItemSkinRegistry.getMap().containsKey(stack.getItem()) && stack.has(EnderLibComponents.SKIN_ID)) {
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
                    stack.set(DataComponents.ITEM_MODEL, ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID)).getModelId());
                    stack.set(EnderLibComponents.SKIN_ID, stack.get(EnderLibComponents.SKIN_ID) + 1);
                } else {
                    if (stack.get(EnderLibComponents.SKIN_ID) == ItemSkinRegistry.getMap().get(stack.getItem()).size() && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                        modifierSkin.resetDefaults(stack);
                    }
                    stack.set(DataComponents.ITEM_MODEL, stack.getPrototype().get(DataComponents.ITEM_MODEL));
                    stack.set(EnderLibComponents.SKIN_ID, 0);
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}
