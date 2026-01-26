package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.EnderLibComponents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public interface CycleEquipmentStateInt {
    void changeState(ItemStack stack, boolean sneaking);
    String keys(ItemStack stack);
    EquipmentSlot equipmentType();
    default RegistryKey<EquipmentAsset> key(ItemStack stack) {
        return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.ofVanilla(keys(stack)));
    }

    default void updateEquippmentState(ItemStack stack) {
        stack.set(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(equipmentType()).model(key(stack)).build());
    }

    default int getState(ItemStack stack) {
        return stack.get(EnderLibComponents.EQUIPMENT_STATE);
    }

    default String getKeyPerState(ItemStack stack, int state, String key, String falseValue) {
        if (stack.get(EnderLibComponents.EQUIPMENT_STATE).equals(state))
            return key;
        else
            return falseValue;
    }

}
