package net.enderboy500.enderlib.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface MurderEffect {
    void onKill(Level level, LivingEntity killer, ItemStack stack, LivingEntity victim, DamageSource damageSource);
}
