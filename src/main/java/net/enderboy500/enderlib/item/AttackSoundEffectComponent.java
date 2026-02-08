package net.enderboy500.enderlib.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public record AttackSoundEffectComponent(SoundEvent soundEvent) {
    public static final Codec<AttackSoundEffectComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(SoundEvent.CODEC.fieldOf("sound").forGetter(AttackSoundEffectComponent::particles)).apply(instance, AttackSoundEffectComponent::new));
    public static final PacketCodec<RegistryByteBuf, AttackSoundEffectComponent> PACKET_CODEC = PacketCodec.tuple(SoundEvent.PACKET_CODEC.cast(), AttackSoundEffectComponent::particles, AttackSoundEffectComponent::new);

    public SoundEvent particles() {
        return soundEvent;
    }
}
