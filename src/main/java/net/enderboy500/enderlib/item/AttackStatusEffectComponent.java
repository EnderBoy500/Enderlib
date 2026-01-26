package net.enderboy500.enderlib.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import java.util.List;

public record AttackStatusEffectComponent(List<StatusEffectInstance> effects) {
    public static final Codec<AttackStatusEffectComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(StatusEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(AttackStatusEffectComponent::effects)).apply(instance, AttackStatusEffectComponent::new));
    public static final PacketCodec<RegistryByteBuf, AttackStatusEffectComponent> PACKET_CODEC = PacketCodec.tuple(StatusEffectInstance.PACKET_CODEC.collect(PacketCodecs.toList()), AttackStatusEffectComponent::effects, AttackStatusEffectComponent::new);


    public AttackStatusEffectComponent(StatusEffectInstance effect) {
        this(List.of(effect));
    }

    @Override
    public List<StatusEffectInstance> effects() {
        return effects;
    }

    public void applyEffect(LivingEntity target) {
        boolean bl = false;
        for(StatusEffectInstance statusEffectInstance : this.effects) {
            if (target.addStatusEffect(new StatusEffectInstance(statusEffectInstance))) {
                bl = true;
            }
        }

    }
}
