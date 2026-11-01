package net.enderboy500.enderlib.item;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.Map;

public class TestItem extends Item implements CycleEquipmentStateInt {
    public TestItem(Properties properties) {
        super(properties.component(EnderLibComponents.EQUIPMENT_STATE, 0));
    }

    @Override
    public Map<Integer, ResourceKey<EquipmentAsset>> keys() {
        return Map.of(0, EquipmentAssets.COPPER, 1, EquipmentAssets.CHAINMAIL, 2, EquipmentAssets.SADDLE, 3, EquipmentAssets.DIAMOND);
    }

    @Override
    public EquipmentSlot equipmentType() {
        return EquipmentSlot.HEAD;
    }
}
