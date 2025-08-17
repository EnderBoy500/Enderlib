package net.enderboy500.enderlib.helper;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

public interface ArmorHelper {

    default boolean isWearingFullSet(PlayerEntity player, TagKey<Item> itemTagKey) {
        if (player.getEquippedStack(EquipmentSlot.HEAD).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.CHEST).isIn(itemTagKey)
            && player.getEquippedStack(EquipmentSlot.LEGS).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.FEET).isIn(itemTagKey)) {
            return true;
        } else {
            return false;
        }
    }

}
