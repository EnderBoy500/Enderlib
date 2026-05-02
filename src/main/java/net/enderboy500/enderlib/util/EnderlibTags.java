package net.enderboy500.enderlib.util;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class EnderlibTags {
    public static final TagKey<Item> CROSSBOW_AMMO = TagKey.of(RegistryKeys.ITEM, Identifier.of(EnderLib.MOD_ID, "crossbow_ammo"));
    public static final TagKey<Item> SKIN_INCOMPATIBILITY = TagKey.of(RegistryKeys.ITEM, Identifier.of(EnderLib.MOD_ID, "skin_incompatibility"));
    public static void loadTags() {}
}
