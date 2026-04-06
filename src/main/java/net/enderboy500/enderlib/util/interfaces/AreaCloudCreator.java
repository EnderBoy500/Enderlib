package net.enderboy500.enderlib.util.interfaces;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;


public interface AreaCloudCreator {
    static void spawnCloud(World world, BlockPos pos, RegistryEntry<StatusEffect> effect, ParticleEffect particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setParticleType(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        areaEffectCloud.addEffect(new StatusEffectInstance(effect, effectDuration, effectAmplifier));

        world.spawnEntity(areaEffectCloud);
    }
    static void spawnCloud(World world, BlockPos pos, List<RegistryEntry<StatusEffect>> statusEffects, ParticleEffect particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setParticleType(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        for (RegistryEntry<StatusEffect> statusEffect : statusEffects) {
            areaEffectCloud.addEffect(new StatusEffectInstance(statusEffect, effectDuration, effectAmplifier));
        }

        world.spawnEntity(areaEffectCloud);
    }
}
