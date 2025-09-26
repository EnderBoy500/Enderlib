package net.enderboy500.enderlib.misc;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public interface CycleEquipmentStateBool {
    String trueKey();
    String falseKey();
    EquipmentSlot equipmentType();

    default void updateEquippmentState(ItemStack stack) {
        stack.set(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(equipmentType()).model(key(stack)).build());
    }

    default void changeState(ItemStack stack, boolean sneaking) {
        if (sneaking) {
            if (Boolean.TRUE.equals(stack.get(EnderLib.EnderLibComponents.CYCLED_EQUIPMENT_STATE))) {
                stack.set(EnderLib.EnderLibComponents.CYCLED_EQUIPMENT_STATE, false);
            } else {
                stack.set(EnderLib.EnderLibComponents.CYCLED_EQUIPMENT_STATE, true);
            }
        }
    }

    default RegistryKey<EquipmentAsset> key(ItemStack stack) {
        if (stack.get(EnderLib.EnderLibComponents.CYCLED_EQUIPMENT_STATE)) {
            return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.ofVanilla(trueKey()));
        } else {
            return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.ofVanilla(falseKey()));
        }
    }
}
