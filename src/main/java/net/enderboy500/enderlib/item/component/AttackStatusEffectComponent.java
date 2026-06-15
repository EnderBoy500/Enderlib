package net.enderboy500.enderlib.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public record AttackStatusEffectComponent(List<MobEffectInstance> effects) {
    public static final Codec<AttackStatusEffectComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(AttackStatusEffectComponent::effects)).apply(instance, AttackStatusEffectComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AttackStatusEffectComponent> PACKET_CODEC = StreamCodec.composite(MobEffectInstance.STREAM_CODEC.apply(ByteBufCodecs.list()), AttackStatusEffectComponent::effects, AttackStatusEffectComponent::new);


    public AttackStatusEffectComponent(MobEffectInstance effect) {
        this(List.of(effect));
    }

    @Override
    public List<MobEffectInstance> effects() {
        return effects;
    }

    public void applyEffect(LivingEntity target) {
        boolean bl = false;
        for(MobEffectInstance statusEffectInstance : this.effects) {
            if (target.addEffect(new MobEffectInstance(statusEffectInstance))) {
                bl = true;
            }
        }

    }
}
