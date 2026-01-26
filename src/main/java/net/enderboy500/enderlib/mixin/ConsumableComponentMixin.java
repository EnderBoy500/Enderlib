package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.events.CanConsumeEvent;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ConsumableComponent.class)
public abstract class ConsumableComponentMixin {

    @Shadow public abstract boolean canConsume(LivingEntity user, ItemStack stack);

    @Inject(method = "canConsume",at = @At("HEAD"), cancellable = true)
    public void enderlib$consume(LivingEntity user, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        boolean canConsumeE = CanConsumeEvent.EVENT.invoker().getB(user);
        if (!canConsumeE) {
            cir.setReturnValue(canConsumeE);
        }
    }
}
