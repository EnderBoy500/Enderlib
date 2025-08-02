package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.item.CustomAttackSoundEffect;
import net.enderboy500.enderlib.item.CustomSweepingEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {


    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"))
    private void spawnCustomHitParticlesAndPlayCustomHitSound(Entity target, CallbackInfo ci) {
        if (this.getAttackCooldownProgress(0.5F) > 0.90F) {
            if (this.getMainHandStack().getItem() instanceof CustomSweepingEffect customSweepingEffect) {
                customSweepingEffect.spawnHitParticles((PlayerEntity) (Object) this);
            }
                if (this.getMainHandStack().getItem() instanceof CustomAttackSoundEffect customAttackSoundEffect) {
                    customAttackSoundEffect.playSoundEffect((PlayerEntity) (Object) this);
                }
        }
    }

    /**
     * @author Ciph3rJ
     * @reason To look cool
     */
    @Overwrite
    public void spawnSweepAttackParticles() {
        double d = (double)(-MathHelper.sin(this.getYaw() * ((float)Math.PI / 180F)));
        double e = (double)MathHelper.cos(this.getYaw() * ((float)Math.PI / 180F));
        if (this.getWorld() instanceof ServerWorld && !(this.getMainHandStack().getItem() instanceof CustomSweepingEffect)) {
            ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + d, this.getBodyY((double)0.5F), this.getZ() + e, 0, d, (double)0.0F, e, (double)0.0F);
        }
    }
}
