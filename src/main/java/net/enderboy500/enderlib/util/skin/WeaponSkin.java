package net.enderboy500.enderlib.util.skin;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.ModifiedComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class WeaponSkin extends ComponentSkin {
    private final Modifier modifier;

    public WeaponSkin(String id, Identifier modelId, Modifier modifier) {
        super(id, modelId);
        this.modifier = modifier;
    }

    @Override
    public <T> List<ModifiedComponent<T>> modifiedComponents(ItemStack stack) {
        return modifier.modifiedComponents();
    }

    public static class Modifier {
        private final ParticleEffect particleEffect;
        private final SoundEvent soundEvent;

        public Modifier(ParticleEffect particleEffect, SoundEvent soundEvent) {
            this.particleEffect = particleEffect;
            this.soundEvent = soundEvent;
        }

        public <T> List<ModifiedComponent<T>> modifiedComponents() {
            List<ModifiedComponent<T>> list = new ArrayList<>();
            if (particleEffect != null) list.add(ModifiedComponent.create(EnderLibComponents.SWEEP_ATTACK_PARTICLE, particleEffect));
            if (soundEvent != null) list.add(ModifiedComponent.create(EnderLibComponents.ATTACK_SOUND_EFFECT, soundEvent));
            return list;
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
