package net.enderboy500.enderlib.item.component;

import com.mojang.serialization.Codec;
import net.enderboy500.enderlib.ELib;
import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.helper.RegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.UnaryOperator;

public class EnderLibComponents {
    public static final DataComponentType<Boolean> CYCLED_EQUIPMENT_STATE = registerDataComponent("cycled_equipment_state", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Integer> EQUIPMENT_STATE = registerDataComponent("equipment_state", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DataComponentType<Boolean> EQUIPMENT_VISIBLE = registerDataComponent("equipment_visible", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> SHOW_HAND = registerDataComponent("show_hand", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> HAS_SWEEP_ATTACK = registerDataComponent("has_sweep_attack", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> HOLD_WITH_BOTH_HANDS = registerDataComponent("hold_with_both_hands", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> POINT_TO_CAMERA = registerDataComponent("point_at_camera", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> POINT_LIKE_BOW = registerDataComponent("point_like_bow", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> UNDROPPABlE = registerDataComponent("undroppable", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> CAN_STRIP = registerDataComponent("can_strip", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> CAN_FLATTEN_TO_PATH = registerDataComponent("can_flatten_to_path", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Boolean> CAN_TILL = registerDataComponent("can_till", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    //public static final DataComponentType<Boolean> CAN_SHEAR = registerDataComponent("can_shear", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DataComponentType<Float> SHIELD_BLOCKER = registerDataComponent("shield_blocker", builder -> builder.persistent(Codec.FLOAT).networkSynchronized(ByteBufCodecs.FLOAT));
    public static final DataComponentType<AttackStatusEffectComponent> ATTACK_STATUS_EFFECT = registerDataComponent("attack_status_effect", builder -> builder.persistent(AttackStatusEffectComponent.CODEC).networkSynchronized(AttackStatusEffectComponent.PACKET_CODEC));
    public static final DataComponentType<ParticleOptions> SWEEP_ATTACK_PARTICLE = registerDataComponent("sweep_attack_particle", builder -> builder.persistent(ParticleTypes.CODEC).networkSynchronized(ParticleTypes.STREAM_CODEC));
    public static final DataComponentType<SoundEvent> ATTACK_SOUND_EFFECT = registerDataComponent("attack_sound_effect", builder -> builder.persistent(SoundEvent.DIRECT_CODEC).networkSynchronized(SoundEvent.DIRECT_STREAM_CODEC));
    public static final DataComponentType<Identifier> CUSTOM_RIPTIDE_TEXTURE = registerDataComponent("custom_riptide_texture", builder -> builder.persistent(Identifier.CODEC).networkSynchronized(Identifier.STREAM_CODEC));
    public static final DataComponentType<Integer> SKIN_ID = registerDataComponent("skin_id", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static <T>DataComponentType<T> registerDataComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, EnderLib.id(id),
                builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void load() {};
}
