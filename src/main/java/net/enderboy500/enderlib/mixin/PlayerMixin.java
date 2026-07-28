package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.interfaces.ScreenShake;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.scores.PlayerTeam;
import net.enderboy500.enderlib.events.BedInteractionEvent;
import net.enderboy500.enderlib.events.CanPlayerModifyWorldEvent;
import net.enderboy500.enderlib.events.DeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;
import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements ScreenShake {

    @Shadow public abstract float getAttackStrengthScale(float baseTime);

    @Shadow protected abstract MutableComponent decorateDisplayNameComponent(MutableComponent component);

    @Shadow protected abstract float getEnchantedDamage(Entity target, float baseDamage, DamageSource damageSource);

    @Shadow public abstract void playServerSideSound(SoundEvent sound);

    @Shadow public abstract ItemStack getWeaponItem();

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "blockActionRestricted", at = @At("HEAD"), cancellable = true)
    public void enderlib$modifyWorld(Level world, BlockPos pos, GameType gameMode, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player)(Object) this;
        boolean canModify = CanPlayerModifyWorldEvent.EVENT.invoker().getB(player);
        if (!canModify && !player.isCreative()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "blockUsingItem", at = @At("HEAD"), cancellable = true)
    public void enderlib$shieldCooldown(ServerLevel world, LivingEntity attacker, CallbackInfo ci) {
        ItemStack itemStack = this.getItemBlockingWith();
        BlocksAttacks blocksAttacksComponent = itemStack != null ? (BlocksAttacks)itemStack.get(DataComponents.BLOCKS_ATTACKS) : null;
        ItemStack weapon = attacker.getMainHandItem();
        if (!weapon.isEmpty() && weapon.has(EnderLibComponents.SHIELD_BLOCKER)) {
            blocksAttacksComponent.disable(world, this, weapon.get(EnderLibComponents.SHIELD_BLOCKER), itemStack);
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getAttackStrengthScale(F)F"))
    private void enderlib$spawnCustomHitParticlesAndPlayCustomHitSound(Entity target, CallbackInfo ci) {
        ItemStack stack = this.getMainHandItem();
        if (!stack.isEmpty() && stack.has(EnderLibComponents.ATTACK_STATUS_EFFECT) && target instanceof LivingEntity livingEntity) {
            stack.getComponents().get(EnderLibComponents.ATTACK_STATUS_EFFECT).applyEffect(livingEntity);
        }
    }

    @Inject(method = "isSweepAttack", at = @At("HEAD"), cancellable = true)
    public void enderlib$canSweep(boolean cooldownPassed, boolean criticalHit, boolean knockback, CallbackInfoReturnable<Boolean> cir) {
        if (getMainHandItem().has(EnderLibComponents.HAS_SWEEP_ATTACK)) cir.setReturnValue(getMainHandItem().get(EnderLibComponents.HAS_SWEEP_ATTACK));
    }

    @Inject(method = "playServerSideSound", at = @At("HEAD"), cancellable = true)
    public void enderlib$sound(SoundEvent sound, CallbackInfo ci) {
        if (getMainHandItem().hasNonDefault(EnderLibComponents.ATTACK_SOUND_EFFECT)) {
            this.level().playSound((Entity)null, this.getX(), this.getY(), this.getZ(), getMainHandItem().get(EnderLibComponents.ATTACK_SOUND_EFFECT), this.getSoundSource(), 1.0F, 1.0F);
            ci.cancel();
        }
    }

    @Unique
    public SoundEvent sweepSound() {
        ItemStack stack = this.getWeaponItem();
        return (!stack.isEmpty() && stack.has(EnderLibComponents.ATTACK_SOUND_EFFECT)) ? stack.get(EnderLibComponents.ATTACK_SOUND_EFFECT) : SoundEvents.PLAYER_ATTACK_SWEEP;
    }

    @Inject(method = "doSweepAttack", at = @At("HEAD"), cancellable = true)
    public final void doSweepingAttack(Entity target, float damage, DamageSource damageSource, float cooldownProgress, CallbackInfo ci) {
        if (!getMainHandItem().isEmpty() && getMainHandItem().has(EnderLibComponents.SWEEP_ATTACK_PARTICLE)) {
            this.playServerSideSound(sweepSound());
            Level var6 = this.level();
            if (var6 instanceof ServerLevel serverWorld) {
                float var12 = 1.0F + (float)this.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * damage;

                for(LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate((double)1.0F, (double)0.25F, (double)1.0F))) {
                    if (livingEntity != this && livingEntity != target && !this.isAlliedTo(livingEntity)) {
                        if (livingEntity instanceof ArmorStand) {
                            ArmorStand armorStandEntity = (ArmorStand)livingEntity;
                            if (armorStandEntity.isMarker()) {
                                continue;
                            }
                        }

                        if (this.distanceToSqr(livingEntity) < (double)9.0F) {
                            float g = this.getEnchantedDamage(livingEntity, var12, damageSource) * cooldownProgress;
                            if (livingEntity.hurtServer(serverWorld, damageSource, g)) {
                                livingEntity.knockback((double)0.4F, (double)Mth.sin((double)(this.getYRot() * ((float)Math.PI / 180F))), (double)(-Mth.cos((double)(this.getYRot() * ((float)Math.PI / 180F)))));
                                EnchantmentHelper.doPostAttackEffects(serverWorld, livingEntity, damageSource);
                            }
                        }
                    }
                }

                double d = (double)(-Mth.sin((double)(this.getYRot() * ((float)Math.PI / 180F))));
                double e = (double)Mth.cos((double)(this.getYRot() * ((float)Math.PI / 180F)));
                serverWorld.sendParticles(getMainHandItem().get(EnderLibComponents.SWEEP_ATTACK_PARTICLE), this.getX() + d, this.getY((double)0.5F), this.getZ() + e, 0, d, (double)0.0F, e, (double)0.0F);
            }
            ci.cancel();
        }
    }

    @Inject(method = "getDisplayName", at = @At("HEAD"),cancellable = true)
    public void enderlib$bdayName(CallbackInfoReturnable<Component> cir) {
        Calendar calendar = Calendar.getInstance();
        if (this.getUUID().equals(UUID.fromString("9cd1d98f-ddc2-427b-95a3-caed34c17529")) &&
                (calendar.get(Calendar.MONTH) == Calendar.NOVEMBER && calendar.get(Calendar.DATE) >= 12 && calendar.get(Calendar.DATE) <= 14)) {
            MutableComponent mutableText = PlayerTeam.formatNameForTeam(this.getTeam(), this.getName());
            cir.setReturnValue(this.decorateDisplayNameComponent(mutableText).append(Component.literal( " " + String.valueOf('\uE500'))));
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

    @Inject(method = "die", at = @At("TAIL"))
    public void enderlib$death(DamageSource damageSource, CallbackInfo ci) {
        DeathEvent.PLAYER_DEATH.invoker().die((Player) (Object) this,level(), damageSource);
    }
    @Inject(method = "stopSleeping()V", at = @At("TAIL"))
    public void enderlib$wake(CallbackInfo ci) {
        BedInteractionEvent.WAKE_UP.invoker().sleep((Player) (Object) this);
    }

    @Unique
    public float lerpToZero(float value) {
        float easingFactor = 0.1f;
        return value - (value * easingFactor);
    }

    @Override
    public void setScreenShakeDuration(int duration) {
        entityData.set(EnderLib.SCREENSHAKE_DURATION, duration);
    }

    @Override
    public void setScreenShakeIntensity(float intensity) {
        float value = Math.clamp(intensity, 0, 10);
        if(value <= 0.01F){
            value = 0;
        }
        entityData.set(EnderLib.SCREENSHAKE_INTENSITY, value);
    }

    @Override
    public int getScreenShakeDuration() {
        return entityData.get(EnderLib.SCREENSHAKE_DURATION);
    }

    @Override
    public float getScreenShakeIntensity() {
        return entityData.get(EnderLib.SCREENSHAKE_INTENSITY);
    }

    @Inject(method = "defineSynchedData", at = @At("HEAD"))
    public void enderlib$initDataTracker(SynchedEntityData.Builder builder, CallbackInfo ci){
        builder.define(EnderLib.SCREENSHAKE_INTENSITY, 0.0F);
        builder.define(EnderLib.SCREENSHAKE_DURATION, 0);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void enderlib$readNBT(ValueInput view, CallbackInfo ci){
        view.getIntOr("ScreenShakeDuration", getScreenShakeDuration());
        view.getFloatOr("ScreenShakeIntensity", getScreenShakeIntensity());

    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void enderlib$writeNBT(ValueOutput view, CallbackInfo ci){
        view.putInt("ScreenShakeDuration", getScreenShakeDuration());
        view.putFloat("ScreenShakeIntensity", getScreenShakeIntensity());
    }

    @Unique
    public void startScreenshake(float intensity, int duration) {
        setScreenShakeIntensity(intensity);
        setScreenShakeDuration(duration);
    }
}
