package net.enderboy500.enderlib.item.component;

import com.mojang.serialization.Codec;
import net.enderboy500.enderlib.helper.RegistryHelper;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class EnderLibComponents {
    public static final ComponentType<Boolean> CYCLED_EQUIPMENT_STATE = RegistryHelper.registerDataComponent("cycled_equipment_state", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Integer> EQUIPMENT_STATE = RegistryHelper.registerDataComponent("equipment_state", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
    public static final ComponentType<Boolean> EQUIPMENT_VISIBLE = RegistryHelper.registerDataComponent("equipment_visible", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Boolean> SHOW_HAND = RegistryHelper.registerDataComponent("show_hand", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Boolean> HAS_SWEEP_ATTACK = RegistryHelper.registerDataComponent("has_sweep_attack", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Boolean> HOLD_WITH_BOTH_HANDS = RegistryHelper.registerDataComponent("hold_with_both_hands", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Boolean> POINT_TO_CAMERA = RegistryHelper.registerDataComponent("point_at_camera", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Boolean> POINT_LIKE_BOW = RegistryHelper.registerDataComponent("point_like_blow", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Boolean> UNDROPPABlE = RegistryHelper.registerDataComponent("undroppable", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Boolean> UNPICKABLE = RegistryHelper.registerDataComponent("unpickable", builder -> builder.codec(Codec.BOOL).packetCodec(PacketCodecs.BOOLEAN));
    public static final ComponentType<Float> SHIELD_BLOCKER = RegistryHelper.registerDataComponent("shield_blocker", builder -> builder.codec(Codec.FLOAT).packetCodec(PacketCodecs.FLOAT));
    public static final ComponentType<AttackStatusEffectComponent> ATTACK_STATUS_EFFECT = RegistryHelper.registerDataComponent("attack_status_effect", builder -> builder.codec(AttackStatusEffectComponent.CODEC).packetCodec(AttackStatusEffectComponent.PACKET_CODEC));
    public static final ComponentType<ParticleEffect> SWEEP_ATTACK_PARTICLE = RegistryHelper.registerDataComponent("sweep_attack_particle", builder -> builder.codec(ParticleTypes.TYPE_CODEC).packetCodec(ParticleTypes.PACKET_CODEC));
    public static final ComponentType<SoundEvent> ATTACK_SOUND_EFFECT = RegistryHelper.registerDataComponent("attack_sound_effect", builder -> builder.codec(SoundEvent.CODEC).packetCodec(SoundEvent.PACKET_CODEC));
    public static final ComponentType<Identifier> CUSTOM_RIPTIDE_TEXTURE = RegistryHelper.registerDataComponent("custom_riptide_texture", builder -> builder.codec(Identifier.CODEC).packetCodec(Identifier.PACKET_CODEC));
    public static final ComponentType<Integer> SKIN_ID = RegistryHelper.registerDataComponent("skin_id", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));

    public static void load() {};
}
