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

import java.util.Map;

public interface CycleEquipmentStateInt extends InventoryInteraction {
    Map<Integer, ResourceKey<EquipmentAsset>> keys();
    EquipmentSlot equipmentType();

    default ResourceKey<EquipmentAsset> key(ItemStack stack) {
        return keys().get(stack.get(EnderLibComponents.EQUIPMENT_STATE));
    }

    @Override
    default void onSlotInteraction(ItemStack stack, Player player, boolean bl) {
        int max = keys().size() - 1;
        if (stack.get(EnderLibComponents.EQUIPMENT_STATE) == max) stack.set(EnderLibComponents.EQUIPMENT_STATE, 0);
        else stack.set(EnderLibComponents.EQUIPMENT_STATE, stack.get(EnderLibComponents.EQUIPMENT_STATE) + 1);

        stack.set(DataComponents.EQUIPPABLE, Equippable.builder(equipmentType()).setAsset(key(stack)).build());
    }
}
