package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.BlockEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    public void enderlib$place(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        BlockEvents.PLACE.invoker().placeBlock(world, pos, state, placer, itemStack);
    }

    @Inject(method = "playerWillDestroy", at = @At("HEAD"))
    public void enderlib$break(Level world, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<BlockState> cir) {
        BlockEvents.BREAK.invoker().breakBlock(world, pos, state, player);
    }
}
