package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.EnderLibComponents;
import net.enderboy500.enderlib.client.ScreenShake;
import net.enderboy500.enderlib.events.BedInteractionEvent;
import net.enderboy500.enderlib.events.CanPlayerModifyWorldEvent;
import net.enderboy500.enderlib.events.DeathEvent;
import net.enderboy500.enderlib.util.ItemUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements ScreenShake {

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    @Shadow protected abstract MutableText addTellClickEvent(MutableText component);

    @Shadow protected abstract float getDamageAgainst(Entity target, float baseDamage, DamageSource damageSource);

    @Shadow public abstract void playAttackSound(SoundEvent sound);

    @Shadow public abstract ItemStack getWeaponStack();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
    public void enderlib$modifyWorld(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        PlayerEntity player = (PlayerEntity)(Object) this;
        boolean canModify = CanPlayerModifyWorldEvent.EVENT.invoker().getB(player);
        if (!canModify && !player.isCreative()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "takeShieldHit", at = @At("HEAD"), cancellable = true)
    public void enderlib$shieldCooldown(ServerWorld world, LivingEntity attacker, CallbackInfo ci) {
        ItemStack itemStack = this.getBlockingItem();
        BlocksAttacksComponent blocksAttacksComponent = itemStack != null ? (BlocksAttacksComponent)itemStack.get(DataComponentTypes.BLOCKS_ATTACKS) : null;
        ItemStack weapon = attacker.getMainHandStack();
        if (!weapon.isEmpty() && weapon.contains(EnderLibComponents.SHIELD_BLOCKER)) {
            blocksAttacksComponent.applyShieldCooldown(world, this, weapon.get(EnderLibComponents.SHIELD_BLOCKER), itemStack);
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"))
    private void enderlib$spawnCustomHitParticlesAndPlayCustomHitSound(Entity target, CallbackInfo ci) {
        ItemStack stack = this.getMainHandStack();
        if (!stack.isEmpty() && stack.contains(EnderLibComponents.ATTACK_STATUS_EFFECT) && target instanceof LivingEntity livingEntity) {
            stack.getComponents().get(EnderLibComponents.ATTACK_STATUS_EFFECT).applyEffect(livingEntity);
        }
    }

    @Inject(method = "canUseSweepAttack", at = @At("HEAD"), cancellable = true)
    public void enderlib$canSweep(boolean cooldownPassed, boolean criticalHit, boolean knockback, CallbackInfoReturnable<Boolean> cir) {
        if (getMainHandStack().contains(EnderLibComponents.HAS_SWEEP_ATTACK)) cir.setReturnValue(getMainHandStack().get(EnderLibComponents.HAS_SWEEP_ATTACK));
    }

    @Inject(method = "playAttackSound", at = @At("HEAD"), cancellable = true)
    public void enderlib$sound(SoundEvent sound, CallbackInfo ci) {
        if (getMainHandStack().hasChangedComponent(EnderLibComponents.ATTACK_SOUND_EFFECT)) {
            this.getEntityWorld().playSound((Entity)null, this.getX(), this.getY(), this.getZ(), getMainHandStack().get(EnderLibComponents.ATTACK_SOUND_EFFECT), this.getSoundCategory(), 1.0F, 1.0F);
            ci.cancel();
        }
    }

    @Unique
    public SoundEvent sweepSound() {
        ItemStack stack = this.getWeaponStack();
        return (!stack.isEmpty() && stack.contains(EnderLibComponents.ATTACK_SOUND_EFFECT)) ? stack.get(EnderLibComponents.ATTACK_SOUND_EFFECT) : SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP;
    }

    @Inject(method = "doSweepingAttack", at = @At("HEAD"), cancellable = true)
    public final void doSweepingAttack(Entity target, float damage, DamageSource damageSource, float cooldownProgress, CallbackInfo ci) {
        if (!getMainHandStack().isEmpty() && getMainHandStack().contains(EnderLibComponents.SWEEP_ATTACK_PARTICLE)) {
            this.playAttackSound(sweepSound());
            World var6 = this.getEntityWorld();
            if (var6 instanceof ServerWorld serverWorld) {
                float var12 = 1.0F + (float)this.getAttributeValue(EntityAttributes.SWEEPING_DAMAGE_RATIO) * damage;

                for(LivingEntity livingEntity : this.getEntityWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand((double)1.0F, (double)0.25F, (double)1.0F))) {
                    if (livingEntity != this && livingEntity != target && !this.isTeammate(livingEntity)) {
                        if (livingEntity instanceof ArmorStandEntity) {
                            ArmorStandEntity armorStandEntity = (ArmorStandEntity)livingEntity;
                            if (armorStandEntity.isMarker()) {
                                continue;
                            }
                        }

                        if (this.squaredDistanceTo(livingEntity) < (double)9.0F) {
                            float g = this.getDamageAgainst(livingEntity, var12, damageSource) * cooldownProgress;
                            if (livingEntity.damage(serverWorld, damageSource, g)) {
                                livingEntity.takeKnockback((double)0.4F, (double)MathHelper.sin((double)(this.getYaw() * ((float)Math.PI / 180F))), (double)(-MathHelper.cos((double)(this.getYaw() * ((float)Math.PI / 180F)))));
                                EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity, damageSource);
                            }
                        }
                    }
                }

                double d = (double)(-MathHelper.sin((double)(this.getYaw() * ((float)Math.PI / 180F))));
                double e = (double)MathHelper.cos((double)(this.getYaw() * ((float)Math.PI / 180F)));
                serverWorld.spawnParticles(getMainHandStack().get(EnderLibComponents.SWEEP_ATTACK_PARTICLE), this.getX() + d, this.getBodyY((double)0.5F), this.getZ() + e, 0, d, (double)0.0F, e, (double)0.0F);
            }
            ci.cancel();
        }
    }

    @Inject(method = "getDisplayName", at = @At("HEAD"),cancellable = true)
    public void enderlib$bdayName(CallbackInfoReturnable<Text> cir) {
        Calendar calendar = Calendar.getInstance();
        if (this.getUuid().equals(UUID.fromString("9cd1d98f-ddc2-427b-95a3-caed34c17529")) &&
                (calendar.get(Calendar.MONTH) == Calendar.NOVEMBER && calendar.get(Calendar.DATE) >= 12 && calendar.get(Calendar.DATE) <= 14)) {
            MutableText mutableText = Team.decorateName(this.getScoreboardTeam(), this.getName());
            cir.setReturnValue(this.addTellClickEvent(mutableText).append(Text.literal( " " + String.valueOf('\uE500'))));
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void enderlib$tick(CallbackInfo ci) {
        if (getScreenShakeDuration() > 0){
            setScreenShakeDuration(getScreenShakeDuration() - 1);
        }
        if (getScreenShakeDuration() <= 0 && getScreenShakeIntensity() > 0) {
            setScreenShakeIntensity(this.lerpToZero(getScreenShakeIntensity()));
        }
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    public void enderlib$death(DamageSource damageSource, CallbackInfo ci) {
        DeathEvent.PLAYER_DEATH.invoker().die((PlayerEntity) (Object) this,getEntityWorld(), damageSource);
    }
    @Inject(method = "wakeUp()V", at = @At("TAIL"))
    public void enderlib$wake(CallbackInfo ci) {
        BedInteractionEvent.WAKE_UP.invoker().sleep((PlayerEntity) (Object) this);
    }

    @Unique
    public float lerpToZero(float value) {
        float easingFactor = 0.1f;
        return value - (value * easingFactor);
    }

    @Override
    public void setScreenShakeDuration(int duration) {
        dataTracker.set(EnderLib.SCREENSHAKE_DURATION, duration);
    }

    @Override
    public void setScreenShakeIntensity(float intensity) {
        float value = Math.clamp(intensity, 0, 10);
        if(value <= 0.01F){
            value = 0;
        }
        dataTracker.set(EnderLib.SCREENSHAKE_INTENSITY, value);
    }

    @Override
    public int getScreenShakeDuration() {
        return dataTracker.get(EnderLib.SCREENSHAKE_DURATION);
    }

    @Override
    public float getScreenShakeIntensity() {
        return dataTracker.get(EnderLib.SCREENSHAKE_INTENSITY);
    }

    @Inject(method = "initDataTracker", at = @At("HEAD"))
    public void enderlib$initDataTracker(DataTracker.Builder builder, CallbackInfo ci){
        builder.add(EnderLib.SCREENSHAKE_INTENSITY, 0.0F);
        builder.add(EnderLib.SCREENSHAKE_DURATION, 0);
    }

    @Inject(method = "readCustomData", at = @At("TAIL"))
    public void enderlib$readNBT(ReadView view, CallbackInfo ci){
        view.getInt("ScreenShakeDuration", getScreenShakeDuration());
        view.getFloat("ScreenShakeIntensity", getScreenShakeIntensity());

    }

    @Inject(method = "writeCustomData", at = @At("TAIL"))
    public void enderlib$writeNBT(WriteView view, CallbackInfo ci){
        view.putInt("ScreenShakeDuration", getScreenShakeDuration());
        view.putFloat("ScreenShakeIntensity", getScreenShakeIntensity());
    }

    @Unique
    public void startScreenshake(float intensity, int duration) {
        setScreenShakeIntensity(intensity);
        setScreenShakeDuration(duration);
    }
}
