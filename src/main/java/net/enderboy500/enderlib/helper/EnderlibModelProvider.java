package net.enderboy500.enderlib.helper;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.*;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.Block;

import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class EnderlibModelProvider extends FabricModelProvider {
    public EnderlibModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }

    public void generateFullArmor(ItemModelGenerators itemModelGenerator , ResourceKey<EquipmentAsset> key,
                                  Item helmet, Item chestplate, Item leggings, Item boots , boolean dyable) {
        itemModelGenerator.generateTrimmableItem(helmet, key, ItemModelGenerators.TRIM_PREFIX_HELMET, dyable);
        itemModelGenerator.generateTrimmableItem(chestplate, key, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, dyable);
        itemModelGenerator.generateTrimmableItem(leggings, key, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, dyable);
        itemModelGenerator.generateTrimmableItem(boots, key, ItemModelGenerators.TRIM_PREFIX_BOOTS, dyable);
    }

    public void registerCutBlocks(BlockModelGenerators blockStateModelGenerator, Block base, Block stair, Block slab) {
        BlockModelGenerators.BlockFamilyProvider pool = blockStateModelGenerator.family(base);
        pool.stairs(stair);
        pool.slab(slab);
    }

    public void registerStoneBlocks(BlockModelGenerators blockStateModelGenerator, Block base, Block stair, Block slab, Block wall) {
        BlockModelGenerators.BlockFamilyProvider pool = blockStateModelGenerator.family(base);
        pool.stairs(stair);
        pool.slab(slab);
        pool.wall(wall);
    }

    public void generateFullToolSet(ItemModelGenerators itemModelGenerator, Item sword, Item pickaxe, Item axe,
                                    Item shovel, Item hoe) {
        itemModelGenerator.generateFlatItem(sword, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(pickaxe, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(axe, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(shovel, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerator.generateFlatItem(hoe, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
