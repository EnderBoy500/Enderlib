package net.enderboy500.enderlib.util.interfaces;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.level.Level;


public interface AreaCloudCreator {
    static void spawnCloud(Level world, BlockPos pos, Holder<MobEffect> effect, ParticleOptions particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setCustomParticle(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        areaEffectCloud.addEffect(new MobEffectInstance(effect, effectDuration, effectAmplifier));

        world.addFreshEntity(areaEffectCloud);
    }
    static void spawnCloud(Level world, BlockPos pos, List<Holder<MobEffect>> statusEffects, ParticleOptions particle, int radius, int cloudDuration, int effectDuration, int effectAmplifier) {
        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(world, pos.getX(), pos.getY(), pos.getZ());

        areaEffectCloud.setCustomParticle(particle);
        areaEffectCloud.setRadius(radius);
        areaEffectCloud.setDuration(cloudDuration);
        for (Holder<MobEffect> statusEffect : statusEffects) {
            areaEffectCloud.addEffect(new MobEffectInstance(statusEffect, effectDuration, effectAmplifier));
        }

        world.addFreshEntity(areaEffectCloud);
    }
}
