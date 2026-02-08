package net.enderboy500.enderlib.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;

import java.util.List;

public record SweepAttackParticleComponent(ParticleEffect particle) {
    public static final Codec<SweepAttackParticleComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(ParticleTypes.TYPE_CODEC.fieldOf("particle").forGetter(SweepAttackParticleComponent::particles)).apply(instance, SweepAttackParticleComponent::new));
    public static final PacketCodec<RegistryByteBuf, SweepAttackParticleComponent> PACKET_CODEC = PacketCodec.tuple(ParticleTypes.PACKET_CODEC.cast(), SweepAttackParticleComponent::particles, SweepAttackParticleComponent::new);

    public ParticleEffect particles() {
        return particle;
    }
}
