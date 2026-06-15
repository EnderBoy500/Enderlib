package net.enderboy500.enderlib.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.enderboy500.enderlib.effect.Unclearable;
import net.enderboy500.enderlib.events.CanEntityHealEvent;
import net.enderboy500.enderlib.events.DamageEvent;
import net.enderboy500.enderlib.events.DeathEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean addEffect(MobEffectInstance effect);

    @Shadow public abstract @Nullable LivingEntity asLivingEntity();

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    public void enderlib$canHeal(float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object) this;
        boolean canHeal = CanEntityHealEvent.EVENT.invoker().getB(entity);
        if (!canHeal) {
            ci.cancel();
        }
    }
    @WrapMethod(method = "removeAllEffects")
    private boolean preventClear(Operation<Boolean> original) {
        LivingEntity living = (LivingEntity)(Object)this;
        if (!living.level().isClientSide()) {
            for (MobEffectInstance instance : living.getActiveEffectsMap().values()) {
                if (instance.getEffect().value() instanceof Unclearable) {
                    boolean result = original.call();
                    this.addEffect(instance);
                    return result;
                }
            }
        }
        return original.call();
    }

    @Inject(method = "hurtServer", at = @At("TAIL"))
    public void damage(ServerLevel world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        DamageEvent.ON_DAMAGED.invoker().damage((LivingEntity) (Object) this, world, source, amount);
    }

    @Inject(method = "die", at = @At("TAIL"))
    public void enderlib$death(DamageSource damageSource, CallbackInfo ci) {
        DeathEvent.DEATH.invoker().die((LivingEntity) (Object) this, this.asLivingEntity().level(), damageSource);
    }
}
