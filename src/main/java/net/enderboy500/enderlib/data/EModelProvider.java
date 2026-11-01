package net.enderboy500.enderlib.data;

import net.enderboy500.enderlib.blocks.ColoredBlockFamily;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.Block;

public abstract class EModelProvider extends FabricModelProvider {
    public EModelProvider(FabricDataOutput output) {
        super(output);
    }


    public void createGenericBlockFromColoredBlockFamily(BlockModelGenerators blockModelGenerators,ColoredBlockFamily coloredBlockFamily) {
        for (Block block : ColoredBlockFamily.getBlocks(coloredBlockFamily)) {
            blockModelGenerators.createTrivialCube(block);
        }
    }

    public void createCutBlocks(BlockModelGenerators blockStateModelGenerator, Block base, Block stair, Block slab) {
        BlockModelGenerators.BlockFamilyProvider pool = blockStateModelGenerator.family(base);
        pool.stairs(stair);
        pool.slab(slab);
    }

    public void createStoneBlocks(BlockModelGenerators blockStateModelGenerator, Block base, Block stair, Block slab, Block wall) {
        BlockModelGenerators.BlockFamilyProvider pool = blockStateModelGenerator.family(base);
        pool.stairs(stair);
        pool.slab(slab);
        pool.wall(wall);
    }

    public void generateFullArmor(ItemModelGenerators itemModelGenerators, ResourceKey<EquipmentAsset> key,
                                  Item helmet, Item chestplate, Item leggings, Item boots , boolean dyable) {
        itemModelGenerators.generateTrimmableItem(helmet, key, ItemModelGenerators.TRIM_PREFIX_HELMET, dyable);
        itemModelGenerators.generateTrimmableItem(chestplate, key, ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, dyable);
        itemModelGenerators.generateTrimmableItem(leggings, key, ItemModelGenerators.TRIM_PREFIX_LEGGINGS, dyable);
        itemModelGenerators.generateTrimmableItem(boots, key, ItemModelGenerators.TRIM_PREFIX_BOOTS, dyable);
    }



    public void generateFullToolSet(ItemModelGenerators itemModelGenerators, Item sword, Item pickaxe, Item axe,
                                    Item shovel, Item hoe) {
        itemModelGenerators.generateFlatItem(sword, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(pickaxe, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(axe, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(shovel, ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(hoe, ModelTemplates.FLAT_HANDHELD_ITEM);
    }
}
