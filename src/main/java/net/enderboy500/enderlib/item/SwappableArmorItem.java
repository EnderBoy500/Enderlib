package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.client.EnderLibKeyBinds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;


public interface SwappableArmorItem {
    default void swapHelmet(Entity entity, Item helmet1, Item helmet2) {
        int cooldown = 10;
        if (EnderLibKeyBinds.isCycleHeadwearStateKeyPressed() && entity instanceof PlayerEntity player && cooldown == 0) {
            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(helmet1) ) {
                player.getInventory().setStack(39 , helmet2.getDefaultStack());
                cooldown=10;
            } else if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(helmet2) ) {
                player.getInventory().setStack(39, helmet1.getDefaultStack());
                cooldown = 10;
            }
            if (cooldown > 0) {
                cooldown--;
            }
        }
    }

    default void swapHelmetIrreversible(Entity entity, Item helmet1, Item helmet2) {
        int cooldown = 10;
        if (EnderLibKeyBinds.isCycleHeadwearStateKeyPressed() && entity instanceof PlayerEntity player && cooldown == 0) {
            if (player.getEquippedStack(EquipmentSlot.HEAD).isOf(helmet1) ) {
                player.getInventory().setStack(39, helmet2.getDefaultStack());
                cooldown = 10;
            }
            if (cooldown > 0) {
                cooldown--;
            }
        }
    }

}
