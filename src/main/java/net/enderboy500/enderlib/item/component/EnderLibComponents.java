package net.enderboy500.enderlib.item.component;

import com.mojang.serialization.Codec;
import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.registry.template.EUnvariedRegistry;
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
    private static EUnvariedRegistry eUnvariedRegistry = new EUnvariedRegistry(EnderLib.MOD_ID);

    public static final DataComponentType<Boolean> CYCLED_EQUIPMENT_STATE = eUnvariedRegistry.registerDataComponent("cycled_equipment_state", Codec.BOOL, ByteBufCodecs.BOOL);
    public static final DataComponentType<Integer> EQUIPMENT_STATE = eUnvariedRegistry.registerDataComponent("equipment_state", Codec.INT, ByteBufCodecs.INT);
    public static final DataComponentType<Boolean> EQUIPMENT_VISIBLE = eUnvariedRegistry.registerDataComponent("equipment_visible", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> SHOW_HAND = eUnvariedRegistry.registerDataComponent("show_hand", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> HAS_SWEEP_ATTACK = eUnvariedRegistry.registerDataComponent("has_sweep_attack", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> HOLD_WITH_BOTH_HANDS = eUnvariedRegistry.registerDataComponent("hold_with_both_hands", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> POINT_TO_CAMERA = eUnvariedRegistry.registerDataComponent("point_at_camera", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> POINT_LIKE_BOW = eUnvariedRegistry.registerDataComponent("point_like_bow", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> UNDROPPABlE = eUnvariedRegistry.registerDataComponent("undroppable", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> CAN_STRIP = eUnvariedRegistry.registerDataComponent("can_strip",Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> CAN_FLATTEN_TO_PATH = eUnvariedRegistry.registerDataComponent("can_flatten_to_path", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Boolean> CAN_TILL = eUnvariedRegistry.registerDataComponent("can_till", Codec.BOOL,ByteBufCodecs.BOOL);
    public static final DataComponentType<Float> SHIELD_BLOCKER = eUnvariedRegistry.registerDataComponent("shield_blocker", Codec.FLOAT,ByteBufCodecs.FLOAT);
    public static final DataComponentType<AttackStatusEffectComponent> ATTACK_STATUS_EFFECT = eUnvariedRegistry.registerDataComponent("attack_status_effect", AttackStatusEffectComponent.CODEC,AttackStatusEffectComponent.PACKET_CODEC);
    public static final DataComponentType<ParticleOptions> SWEEP_ATTACK_PARTICLE = eUnvariedRegistry.registerDataComponent("sweep_attack_particle", ParticleTypes.CODEC,ParticleTypes.STREAM_CODEC);
    public static final DataComponentType<SoundEvent> ATTACK_SOUND_EFFECT = eUnvariedRegistry.registerDataComponent("attack_sound_effect", SoundEvent.DIRECT_CODEC,SoundEvent.DIRECT_STREAM_CODEC);
    public static final DataComponentType<Identifier> CUSTOM_RIPTIDE_TEXTURE = eUnvariedRegistry.registerDataComponent("custom_riptide_texture", Identifier.CODEC,Identifier.STREAM_CODEC);
    public static final DataComponentType<Integer> SKIN_ID = eUnvariedRegistry.registerDataComponent("skin_id", Codec.INT,ByteBufCodecs.INT);



    public static void load() {};
}
