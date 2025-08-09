package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.helper.ToolHelper;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class SwordMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"))
    public void useInjection(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        World world = context.getWorld();
        PlayerEntity playerEntity = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        BlockState blockState = context.getWorld().getBlockState(pos);
        BlockState blockState1 = ToolHelper.SWORD.get(blockState.getBlock());
        BlockState blockState2 = null;
        if (context.getStack().isIn(ItemTags.SWORDS)) {
            if (blockState1 != null) {
                world.playSound(playerEntity, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
                blockState2 = blockState1;
            }
            if (blockState2 != null) {
                world.setBlockState(pos, blockState2, 11);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(playerEntity, blockState2));
                if (playerEntity != null) {
                    context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                }
                context.getStack().useOnBlock(context).isAccepted();
            }
        }
    }

}
