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

public interface CycleEquipmentStateInt extends InventoryInteraction {
    void changeState(ItemStack stack, boolean sneaking);
    String keys(ItemStack stack);
    EquipmentSlot equipmentType();

    default ResourceKey<EquipmentAsset> key(ItemStack stack) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.withDefaultNamespace(keys(stack)));
    }

    default int getState(ItemStack stack) {
        return stack.get(EnderLibComponents.EQUIPMENT_STATE);
    }

    @Override
    default void onSlotInteraction(ItemStack stack, Player player, boolean bl) {
        stack.set(DataComponents.EQUIPPABLE, Equippable.builder(equipmentType()).setAsset(key(stack)).build());
    }

    default String getKeyPerState(ItemStack stack, int state, String key, String falseValue) {
        if (stack.get(EnderLibComponents.EQUIPMENT_STATE).equals(state))
            return key;
        else
            return falseValue;
    }
}
