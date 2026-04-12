package net.enderboy500.enderlib.util.skin;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class WeaponSkin extends ModifierSkin {
    private final Modifier modifier;

    public WeaponSkin(String id, Identifier modelId, Modifier modifier) {
        super(id, modelId);
        this.modifier = modifier;
    }

    @Override
    public void modify(ItemStack item) {
        modifier.modify(item);
    }

    @Override
    public void resetDefaults(ItemStack item) {
        modifier.resetDefaults(item);
    }

    public static class Modifier {
        private final ParticleEffect particleEffect;
        private final SoundEvent soundEvent;

        public Modifier(ParticleEffect particleEffect, SoundEvent soundEvent) {
            this.particleEffect = particleEffect;
            this.soundEvent = soundEvent;
        }
        public void modify(ItemStack item) {
            if (particleEffect != null) item.set(EnderLibComponents.SWEEP_ATTACK_PARTICLE, particleEffect);
            if (soundEvent != null) item.set(EnderLibComponents.ATTACK_SOUND_EFFECT, soundEvent);
        }
        public void resetDefaults(ItemStack item) {
            if (particleEffect != null) item.remove(EnderLibComponents.SWEEP_ATTACK_PARTICLE);
            if (soundEvent != null) item.remove(EnderLibComponents.ATTACK_SOUND_EFFECT);
        }

        public static Modifier create(ParticleEffect particleEffect, SoundEvent soundEvent) {
            return new Modifier(particleEffect, soundEvent);
        }
        public static Modifier create(ParticleEffect particleEffect) {
            return new Modifier(particleEffect, null);
        }
        public static Modifier create(SoundEvent soundEvent) {
            return new Modifier(null , soundEvent);
        }
    }
}
