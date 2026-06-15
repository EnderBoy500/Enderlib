package net.enderboy500.enderlib.util;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class EnderlibTags {
    public static final TagKey<Item> CROSSBOW_AMMO = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EnderLib.MOD_ID, "crossbow_ammo"));
    public static final TagKey<Item> SKIN_INCOMPATIBILITY = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EnderLib.MOD_ID, "skin_incompatibility"));
    public static void loadTags() {}
}
