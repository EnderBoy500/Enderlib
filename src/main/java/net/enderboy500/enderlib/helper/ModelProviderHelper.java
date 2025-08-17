package net.enderboy500.enderlib.helper;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.registry.RegistryKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class ModelProviderHelper extends FabricModelProvider {
    public ModelProviderHelper(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
    }

    public final void registerBar(BlockStateModelGenerator blockStateModelGenerator, Identifier identifier, Block bar) {
        TextureMap textureMap = TextureMap.texture(identifier);
        WeightedVariant weightedVariant = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_POST.upload(bar, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant2 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_SIDE.upload(bar, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant3 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(bar, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant4 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(bar, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant5 = createWeightedVariant(Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(bar, textureMap, blockStateModelGenerator.modelCollector));
        Item item = bar.asItem();
        blockStateModelGenerator.registerItemModel(item);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockModelDefinitionCreator.create(bar).with(weightedVariant).with(createMultipartConditionBuilder().put(Properties.NORTH, true), weightedVariant2).with(createMultipartConditionBuilder().put(Properties.EAST, true), weightedVariant2.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.SOUTH, true), weightedVariant3).with(createMultipartConditionBuilder().put(Properties.WEST, true), weightedVariant3.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.NORTH, false), weightedVariant4).with(createMultipartConditionBuilder().put(Properties.EAST, false), weightedVariant5).with(createMultipartConditionBuilder().put(Properties.SOUTH, false), weightedVariant5.apply(ROTATE_Y_90)).with(createMultipartConditionBuilder().put(Properties.WEST, false), weightedVariant4.apply(ROTATE_Y_270)));
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
