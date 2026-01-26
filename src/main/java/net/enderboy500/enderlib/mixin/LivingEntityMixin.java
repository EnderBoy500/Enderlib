package net.enderboy500.enderlib.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.enderboy500.enderlib.effect.Unclearable;
import net.enderboy500.enderlib.events.CanEntityHealEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    public void enderlib$canHeal(float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object) this;
        boolean canHeal = CanEntityHealEvent.EVENT.invoker().getB(entity);
        if (!canHeal) {
            ci.cancel();
        }
    }
    @WrapMethod(method = "clearStatusEffects")
    private boolean preventClear(Operation<Boolean> original) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (!living.getWorld().isClient()) {
            for (StatusEffectInstance instance : living.getActiveStatusEffects().values()) {
                if (instance.getEffectType().value() instanceof Unclearable) {
                    boolean result = original.call();
                    this.addStatusEffect(instance);
                    return result;
                }
            }
        }
        return original.call();
    }
}
