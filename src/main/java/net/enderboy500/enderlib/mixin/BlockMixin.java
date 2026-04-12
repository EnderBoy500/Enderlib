package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.BlockEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void enderlib$place(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack, CallbackInfo ci) {
        BlockEvents.PLACE.invoker().placeBlock(world, pos, state, placer, itemStack);
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    public void enderlib$break(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        BlockEvents.BREAK.invoker().breakBlock(world, pos, state, player);
    }
}
