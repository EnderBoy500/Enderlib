package net.enderboy500.enderlib.util;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

public interface EnderlibTags {
    interface ItemTags {
        TagKey<Item> CROSSBOW_AMMO = TagKey.create(Registries.ITEM, EnderLib.id("crossbow_ammo"));
        TagKey<Item> SKIN_INCOMPATIBILITY = TagKey.create(Registries.ITEM, EnderLib.id("skin_incompatibility"));
    }
    interface MobEffectTags {
        TagKey<MobEffect> DAMAGE_DEALING = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("damage_dealing"));
        TagKey<MobEffect> MOTION_MODIFYING = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("motion_modifying"));
        TagKey<MobEffect> WATER_EFFECTS = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("water_effects"));
        TagKey<MobEffect> HEALING_EFFECTS = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("healing_effects"));
        TagKey<MobEffect> OMEN = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("omen"));
        TagKey<MobEffect> OVERLAYING_EFFECTS = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("overlaying_effects"));
        TagKey<MobEffect> HUNGER = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("hunger"));
        TagKey<MobEffect> MOTION_BOOST = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("motion_boost"));
        TagKey<MobEffect> SLOWNESS = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("slowness"));
        TagKey<MobEffect> MINING = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("mining"));
        TagKey<MobEffect> ENTITY_MARKERS = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("entity_markers"));
        TagKey<MobEffect> RESISTANCE = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("resistance"));
        TagKey<MobEffect> SUBMERGED_BREATHING = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("submerged_breathing"));
        TagKey<MobEffect> INSTANT = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("instant"));
        TagKey<MobEffect> STRENGTH = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("strength"));
        TagKey<MobEffect> VISION_MODIFIERS = TagKey.create(Registries.MOB_EFFECT, EnderLib.id("vision_modifiers"));
    }
    static void loadTags() {}
}
