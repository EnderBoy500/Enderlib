package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.equipment.Equippable;

public interface CycleEquipmentStateBool extends InventoryInteraction {
    String trueKey();
    String falseKey();
    EquipmentSlot equipmentType();

    default void updateEquipmentState(ItemStack stack) {
        stack.set(DataComponents.EQUIPPABLE, Equippable.builder(equipmentType()).setAsset(key(stack)).build());
    }

    @Override
    default void onSlotInteraction(ItemStack stack, Player player, boolean bl) {
        if (bl) {
            if (Boolean.TRUE.equals(stack.get(EnderLibComponents.CYCLED_EQUIPMENT_STATE))) {
                stack.set(EnderLibComponents.CYCLED_EQUIPMENT_STATE, false);
            } else {
                stack.set(EnderLibComponents.CYCLED_EQUIPMENT_STATE, true);
            }
        }
    };

    default ResourceKey<EquipmentAsset> key(ItemStack stack) {
        if (stack.get(EnderLibComponents.CYCLED_EQUIPMENT_STATE)) {
            return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.withDefaultNamespace(trueKey()));
        } else {
            return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.withDefaultNamespace(falseKey()));
        }
    }
}
