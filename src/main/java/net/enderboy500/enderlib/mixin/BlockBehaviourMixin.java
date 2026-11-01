package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.BlockEvents;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.item.skin.ItemSkin;
import net.enderboy500.enderlib.item.skin.ItemSkinRegistry;
import net.enderboy500.enderlib.item.skin.ModifierSkin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @Inject(method = "useItemOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$skin(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        /*if (context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.SMITHING_TABLE) && player.isShiftKeyDown() && ItemSkinRegistry.getMap().containsKey(itemStack)) {
            System.out.println(1);
            int currentSkin = itemStack.getOrDefault(EnderLibComponents.SKIN_ID, 0);
            ItemSkin prevSkin = ItemSkinRegistry.getMap().get(itemStack).get(currentSkin);
            if (currentSkin == ItemSkinRegistry.getMap().get(itemStack).size()) {
                currentSkin = 0;
            } else currentSkin++;
            ItemSkin newSkin = ItemSkinRegistry.getMap().get(itemStack).get(currentSkin);
            if (prevSkin instanceof ModifierSkin modifierSkin) modifierSkin.resetDefaults(itemStack);

            if (newSkin instanceof ModifierSkin modifierSkin) modifierSkin.modify(itemStack);
            itemStack.set(DataComponents.ITEM_MODEL, ItemSkinRegistry.getMap().get(itemStack).get(currentSkin).getModelId());
            System.out.println(2);
        }*/
    }
}
