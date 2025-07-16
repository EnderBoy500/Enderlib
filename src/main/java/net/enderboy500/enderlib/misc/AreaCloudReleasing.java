package net.enderboy500.enderlib.misc;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public interface AreaCloudReleasing {
    default void spawnCloud(World world, BlockPos pos, RegistryEntry<StatusEffect> effect, ParticleEffect particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setParticleType(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        areaEffectCloud.addEffect(new StatusEffectInstance(effect, effectDuration, effectAmplifier));

        world.spawnEntity(areaEffectCloud);
    }
    default void spawnCloud(World world, BlockPos pos, RegistryEntry<StatusEffect> effect1, RegistryEntry<StatusEffect> effect2, ParticleEffect particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setParticleType(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        areaEffectCloud.addEffect(new StatusEffectInstance(effect1, effectDuration, effectAmplifier));
        areaEffectCloud.addEffect(new StatusEffectInstance(effect2, effectDuration, effectAmplifier));

        world.spawnEntity(areaEffectCloud);
    }
    default void spawnCloud(World world, BlockPos pos, RegistryEntry<StatusEffect> effect1, RegistryEntry<StatusEffect> effect2, RegistryEntry<StatusEffect> effect3, ParticleEffect particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setParticleType(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        areaEffectCloud.addEffect(new StatusEffectInstance(effect1, effectDuration, effectAmplifier));
        areaEffectCloud.addEffect(new StatusEffectInstance(effect2, effectDuration, effectAmplifier));
        areaEffectCloud.addEffect(new StatusEffectInstance(effect3, effectDuration, effectAmplifier));

        world.spawnEntity(areaEffectCloud);
    }
    default void spawnCloud(World world, BlockPos pos, RegistryEntry<StatusEffect> effect1, RegistryEntry<StatusEffect> effect2, RegistryEntry<StatusEffect> effect3, RegistryEntry<StatusEffect> effect4, ParticleEffect particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloudEntity areaEffectCloud = new AreaEffectCloudEntity(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setParticleType(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        areaEffectCloud.addEffect(new StatusEffectInstance(effect1, effectDuration, effectAmplifier));
        areaEffectCloud.addEffect(new StatusEffectInstance(effect2, effectDuration, effectAmplifier));
        areaEffectCloud.addEffect(new StatusEffectInstance(effect3, effectDuration, effectAmplifier));
        areaEffectCloud.addEffect(new StatusEffectInstance(effect4, effectDuration, effectAmplifier));

        world.spawnEntity(areaEffectCloud);
    }
}
