package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.PlaceFireEvent;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class AbstrackFireBlockMixin {
    @Inject(method = "getState", at = @At("RETURN"), cancellable = true)
    private static void customFire(BlockView world, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        Block block = PlaceFireEvent.BASE_BLOCK.invoker().baseBlock(world, pos);
        if (block != null) cir.setReturnValue(block.getDefaultState());
    }
}
