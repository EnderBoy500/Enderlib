package net.enderboy500.enderlib.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;

public class ParticleStatusEffect extends StatusEffect {
    public static ParticleEffect particle;

    public ParticleStatusEffect(StatusEffectCategory category, int color, ParticleEffect particle) {
        super(category, color);
        this.particle = particle;
    }

    @Override
    public ParticleEffect createParticle(StatusEffectInstance effect) {
        return particle;
    }
}
