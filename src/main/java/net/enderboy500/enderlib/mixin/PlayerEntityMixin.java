package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.EnderLibComponents;
import net.enderboy500.enderlib.client.ScreenShake;
import net.enderboy500.enderlib.events.BedInteractionEvent;
import net.enderboy500.enderlib.events.CanPlayerModifyWorldEvent;
import net.enderboy500.enderlib.events.DeathEvent;
import net.enderboy500.enderlib.item.CustomAttackSoundEffect;
import net.enderboy500.enderlib.item.CustomSweepingEffect;
import net.enderboy500.enderlib.util.ItemUtils;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;
import java.util.UUID;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements ScreenShake {

    @Shadow public abstract float getAttackCooldownProgress(float baseTime);

    @Shadow protected abstract MutableText addTellClickEvent(MutableText component);

    @Shadow protected abstract float getDamageAgainst(Entity target, float baseDamage, DamageSource damageSource);

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
        PlayerEntity player = (PlayerEntity) (Object)this;
        if (this.getAttackCooldownProgress(0.5F) > 0.90F) {
            if (this.getMainHandStack().getItem() instanceof CustomSweepingEffect customSweepingEffect) {
                ItemUtils.spawnSweepAttackParticles(player, customSweepingEffect.sweepParticle());
            }
            if (this.getMainHandStack().getItem() instanceof CustomAttackSoundEffect customAttackSoundEffect) {
                player.playSoundToPlayer(customAttackSoundEffect.attackSound(), SoundCategory.PLAYERS,10,1);
            }
        }
        ItemStack stack = player.getMainHandStack();
        if (!stack.isEmpty() && stack.contains(EnderLibComponents.HAS_SWEEP_ATTACK)) {
            ItemUtils.createSweepAttack(player, target, stack);
        }
        if (!stack.isEmpty() && stack.contains(EnderLibComponents.ATTACK_STATUS_EFFECT) && target instanceof LivingEntity livingEntity) {
            stack.getComponents().get(EnderLibComponents.ATTACK_STATUS_EFFECT).applyEffect(livingEntity);
        }
    }

    /**
     * @author Enderboy500
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
        DeathEvent.DEATH.invoker().die((PlayerEntity) (Object) this);
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

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void enderlib$readNBT(NbtCompound nbt, CallbackInfo ci){
        nbt.putInt("ScreenShakeDuration", getScreenShakeDuration());
        nbt.putFloat("ScreenShakeIntensity", getScreenShakeIntensity());

    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void enderlib$writeNBT(NbtCompound nbt, CallbackInfo ci){
        setScreenShakeDuration(nbt.getInt("ScreenShakeDuration").isPresent() ? nbt.getInt("ScreenShakeDuration").get() : 0);
        setScreenShakeIntensity(nbt.getFloat("ScreenShakeIntensity").isPresent() ? nbt.getFloat("ScreenShakeIntensity").get() : 0);
    }

    @Unique
    public void startScreenshake(float intensity, int duration) {
        setScreenShakeIntensity(intensity);
        setScreenShakeDuration(duration);
    }
}
