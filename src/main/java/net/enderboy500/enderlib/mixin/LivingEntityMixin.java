package net.enderboy500.enderlib.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.enderboy500.enderlib.effect.Unclearable;
import net.enderboy500.enderlib.events.CanEntityHealEvent;
import net.enderboy500.enderlib.events.DamageEvent;
import net.enderboy500.enderlib.events.DeathEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract @Nullable LivingEntity getEntity();

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
        if (!living.getEntityWorld().isClient()) {
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

    @Inject(method = "damage", at = @At("TAIL"))
    public void damage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        DamageEvent.ON_DAMAGED.invoker().damage((LivingEntity) (Object) this, world, source, amount);
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    public void enderlib$death(DamageSource damageSource, CallbackInfo ci) {
        DeathEvent.DEATH.invoker().die((LivingEntity) (Object) this, this.getEntity().getEntityWorld(), damageSource);
    }
}
