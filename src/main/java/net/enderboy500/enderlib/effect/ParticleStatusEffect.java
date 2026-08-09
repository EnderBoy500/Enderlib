package net.enderboy500.enderlib.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

public class ParticleStatusEffect extends MobEffect {
    public final ParticleOptions particle;

    public ParticleStatusEffect(MobEffectCategory category, int color, ParticleOptions particle) {
        super(category, color);
        this.particle = particle;
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effect) {
        return particle;
    }
}
