package net.enderboy500.enderlib.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public interface ProtectFromDeath {
    void onProtected(Level level, LivingEntity livingEntity, DamageSource damageSource);
}
