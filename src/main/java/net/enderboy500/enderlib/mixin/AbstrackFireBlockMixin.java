package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.PlaceFireEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseFireBlock.class)
public class AbstrackFireBlockMixin {
    @Inject(method = "getState", at = @At("RETURN"), cancellable = true)
    private static void customFire(BlockGetter world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        Block block = PlaceFireEvent.BASE_BLOCK.invoker().baseBlock(world, pos);
        if (block != null) cir.setReturnValue(block.defaultBlockState());
    }
}
