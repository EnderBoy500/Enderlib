package net.enderboy500.enderlib.misc;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;

public class EItemModelGenerators extends ItemModelGenerators {
    public EItemModelGenerators(ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> biConsumer) {
        super(itemModelOutput, biConsumer);
    }

    public void generateFullArmor(ResourceKey<EquipmentAsset> key,
                                  Item helmet, Item chestplate, Item leggings, Item boots , boolean dyable) {
        generateTrimmableItem(helmet, key, ItemModelGenerators.TRIM_PREFIX_HELMET, dyable);
        generateTrimmableItem(chestplate, key, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, dyable);
        generateTrimmableItem(leggings, key, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, dyable);
        generateTrimmableItem(boots, key, ItemModelGenerators.TRIM_PREFIX_BOOTS, dyable);
    }



    public void generateFullToolSet(Item sword, Item pickaxe, Item axe,
                                    Item shovel, Item hoe) {
        generateFlatItem(sword, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(pickaxe, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(axe, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(shovel, ModelTemplates.FLAT_HANDHELD_ITEM);
        generateFlatItem(hoe, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
