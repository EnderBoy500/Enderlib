package net.enderboy500.enderlib.helper;

import net.enderboy500.enderlib.misc.EBlockModelGenerators;
import net.enderboy500.enderlib.misc.EItemModelGenerators;
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

public abstract class EnderlibModelProvider extends FabricModelProvider {
    public EnderlibModelProvider(FabricDataOutput output) {
        super(output);
    }

    public abstract void generateBlockStatesAndModels(EBlockModelGenerators eBlockModelGenerators);
    public abstract void generateItemModels(EItemModelGenerators eBlockModelGenerators);

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        EBlockModelGenerators eBlockModelGenerators = (EBlockModelGenerators) blockStateModelGenerator;
        generateBlockStatesAndModels(eBlockModelGenerators);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        EItemModelGenerators eItemModelGenerators = (EItemModelGenerators) itemModelGenerator;
        generateItemModels(eItemModelGenerators);
    }


}
