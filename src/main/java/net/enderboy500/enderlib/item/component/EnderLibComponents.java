package net.enderboy500.enderlib.item.component;

import com.mojang.serialization.Codec;
import net.enderboy500.enderlib.helper.RegistryHelper;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class EnderLibComponents {
    public static final DataComponentType<Boolean> CYCLED_EQUIPMENT_STATE = RegistryHelper.registerDataComponent("cycled_equipment_state", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Integer> EQUIPMENT_STATE = RegistryHelper.registerDataComponent("equipment_state", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DataComponentType<Boolean> EQUIPMENT_VISIBLE = RegistryHelper.registerDataComponent("equipment_visible", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> SHOW_HAND = RegistryHelper.registerDataComponent("show_hand", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> HAS_SWEEP_ATTACK = RegistryHelper.registerDataComponent("has_sweep_attack", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> HOLD_WITH_BOTH_HANDS = RegistryHelper.registerDataComponent("hold_with_both_hands", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> POINT_TO_CAMERA = RegistryHelper.registerDataComponent("point_at_camera", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> POINT_LIKE_BOW = RegistryHelper.registerDataComponent("point_like_blow", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> UNDROPPABlE = RegistryHelper.registerDataComponent("undroppable", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> UNPICKABLE = RegistryHelper.registerDataComponent("unpickable", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Float> SHIELD_BLOCKER = RegistryHelper.registerDataComponent("shield_blocker", builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));
    public static final DataComponentType<AttackStatusEffectComponent> ATTACK_STATUS_EFFECT = RegistryHelper.registerDataComponent("attack_status_effect", builder -> builder.persistent(AttackStatusEffectComponent.CODEC).networkSynchronized(AttackStatusEffectComponent.PACKET_CODEC));
    public static final DataComponentType<ParticleOptions> SWEEP_ATTACK_PARTICLE = RegistryHelper.registerDataComponent("sweep_attack_particle", builder -> builder.persistent(ParticleTypes.CODEC).networkSynchronized(ParticleTypes.STREAM_CODEC));
    public static final DataComponentType<SoundEvent> ATTACK_SOUND_EFFECT = RegistryHelper.registerDataComponent("attack_sound_effect", builder -> builder.persistent(SoundEvent.DIRECT_CODEC).networkSynchronized(SoundEvent.DIRECT_STREAM_CODEC));
    public static final DataComponentType<Identifier> CUSTOM_RIPTIDE_TEXTURE = RegistryHelper.registerDataComponent("custom_riptide_texture", builder -> builder.persistent(Identifier.CODEC).networkSynchronized(Identifier.STREAM_CODEC));
    public static final DataComponentType<Integer> SKIN_ID = RegistryHelper.registerDataComponent("skin_id", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static void load() {};
}
