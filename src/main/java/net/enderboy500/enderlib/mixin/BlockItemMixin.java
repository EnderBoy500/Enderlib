package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.CanPlayerModifyWorldEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$stopPlacing(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Player player = context.getPlayer();
        boolean canModify = CanPlayerModifyWorldEvent.EVENT.invoker().getB(player);
        if (!canModify && !player.isCreative()) {
            cir.setReturnValue(InteractionResult.FAIL);
            cir.cancel();
        }
    }
}
