package net.enderboy500.enderlib.helper;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class EnderlibModelProvider extends FabricModelProvider {
    public EnderlibModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }

    public void generateFullArmor(ItemModelGenerator itemModelGenerator , RegistryKey<EquipmentAsset> key,
                                  Item helmet, Item chestplate, Item leggings, Item boots) {
        itemModelGenerator.registerArmor(helmet, key, ItemModelGenerator.HELMET_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(chestplate, key, ItemModelGenerator.CHESTPLATE_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(leggings, key, ItemModelGenerator.LEGGINGS_TRIM_ID_PREFIX, false);
        itemModelGenerator.registerArmor(boots, key, ItemModelGenerator.BOOTS_TRIM_ID_PREFIX, false);
    }

    public void registerCutBlocks(BlockStateModelGenerator blockStateModelGenerator, Block base, Block stair, Block slab) {
        BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.registerCubeAllModelTexturePool(base);
        pool.stairs(stair);
        pool.slab(slab);
    }

    public void registerStoneBlocks(BlockStateModelGenerator blockStateModelGenerator, Block base, Block stair, Block slab, Block wall) {
        BlockStateModelGenerator.BlockTexturePool pool = blockStateModelGenerator.registerCubeAllModelTexturePool(base);
        pool.stairs(stair);
        pool.slab(slab);
        pool.wall(wall);
    }

    public void generateFullToolSet(ItemModelGenerator itemModelGenerator, Item sword, Item pickaxe, Item axe,
                                    Item shovel, Item hoe) {
        itemModelGenerator.register(sword, Models.HANDHELD);
        itemModelGenerator.register(pickaxe, Models.HANDHELD);
        itemModelGenerator.register(axe, Models.HANDHELD);
        itemModelGenerator.register(shovel, Models.HANDHELD);
        itemModelGenerator.register(hoe, Models.HANDHELD);
    }
}
