package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.CanConsumeEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Consumable.class)
public abstract class ConsumableMixin {

    @Shadow public abstract boolean canConsume(LivingEntity user, ItemStack stack);

    @Inject(method = "canConsume",at = @At("HEAD"), cancellable = true)
    public void enderlib$consume(LivingEntity user, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        boolean canConsumeE = CanConsumeEvent.EVENT.invoker().getB(user);
        if (!canConsumeE) {
            cir.setReturnValue(canConsumeE);
        }
    }
}
