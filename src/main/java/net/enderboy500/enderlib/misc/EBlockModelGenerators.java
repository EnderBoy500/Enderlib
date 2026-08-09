package net.enderboy500.enderlib.misc;

import net.enderboy500.enderlib.blocks.ColoredBlockFamily;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class EBlockModelGenerators extends BlockModelGenerators {

    public EBlockModelGenerators(Consumer<BlockModelDefinitionGenerator> consumer, ItemModelOutput itemModelOutput, BiConsumer<Identifier, ModelInstance> biConsumer) {
        super(consumer, itemModelOutput, biConsumer);
    }

    public void createGenericBlockFromColoredBlockFamily(ColoredBlockFamily coloredBlockFamily) {
        for (Block block : ColoredBlockFamily.getBlocks(coloredBlockFamily)) {
            createTrivialCube(block);
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
}
