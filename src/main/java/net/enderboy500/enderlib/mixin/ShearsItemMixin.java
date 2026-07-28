package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.util.interfaces.ToolMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {


    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$useInjection(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level world = context.getLevel();
        Player playerEntity = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = context.getLevel().getBlockState(pos);
        BlockState blockState1 = ToolMaps.SHEAR.get(blockState.getBlock());
        BlockState blockState2 = null;
        if (blockState1 != null) {
            world.playSound(playerEntity, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            blockState2 = blockState1;
        }
        if (blockState2 != null) {
            world.setBlock(pos, blockState2, 11);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(playerEntity, blockState2));
            if (playerEntity != null) {
                context.getItemInHand().hurtWithoutBreaking(1, playerEntity);
            }
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

}
