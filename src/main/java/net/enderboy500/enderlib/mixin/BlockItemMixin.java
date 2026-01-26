package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.CanPlayerModifyWorldEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin {

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void enderlib$stopPlacing(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        PlayerEntity player = context.getPlayer();
        boolean canModify = CanPlayerModifyWorldEvent.EVENT.invoker().getB(player);
        if (!canModify && !player.isCreative()) {
            cir.setReturnValue(ActionResult.FAIL);
            cir.cancel();
        }
    }
}
