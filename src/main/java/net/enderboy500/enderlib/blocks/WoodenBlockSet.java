package net.enderboy500.enderlib.blocks;

import net.enderboy500.enderlib.data.ERecipeGenerator;
import net.enderboy500.enderlib.registry.template.EBlockRegistry;
import net.enderboy500.enderlib.registry.template.EItemRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;

public record WoodenBlockSet(Block log, Block strippedLog, Block wood, Block strippedWood, Block planks, Block stairs, Block slab, Block fence,
                             Block fenceGate, Block door, Block trapdoor, Block pressurePlate, Block button, Block shelf, Block sign, Block wallSign,
                             Item signItem, Block hangingSign, Block wallHangingSign, Item hangingSignItem) {

    public static WoodenBlockSet create(String modId, String suffix, BlockBehaviour.Properties properties, WoodType woodType, BlockSetType blockSetType, int buttonPressTicks, boolean autoLang) {
        EBlockRegistry eBlockRegistry = new EBlockRegistry(modId);
        EItemRegistry eItemRegistry = new EItemRegistry(modId);
        Block log = eBlockRegistry.registerBlockWithItem(suffix + "_log", RotatedPillarBlock::new, properties, autoLang);
        Block strippedLog = eBlockRegistry.registerBlockWithItem(suffix + "_stripped_log", RotatedPillarBlock::new, properties, autoLang);
        Block wood = eBlockRegistry.registerBlockWithItem(suffix + "_wood", RotatedPillarBlock::new, properties, autoLang);
        Block strippedWood = eBlockRegistry.registerBlockWithItem(suffix + "_stripped_wood", RotatedPillarBlock::new, properties, autoLang);
        Block planks = eBlockRegistry.registerBlockWithItem(suffix + "_planks", Block::new, properties, autoLang);
        Block stairs = eBlockRegistry.registerStairs(suffix + "_stairs", properties, autoLang, true);
        Block slab = eBlockRegistry.registerSlab(suffix + "_slab", properties, autoLang, true);
        Block fence = eBlockRegistry.registerFence(suffix + "_fence", properties, autoLang, true);
        Block fenceGate = eBlockRegistry.registerFenceGate(suffix + "_fence_gate", properties, woodType, autoLang, true);
        Block door = eBlockRegistry.registerDoor(suffix + "_door", properties, blockSetType, autoLang, true);
        Block trapdoor = eBlockRegistry.registerTrapDoor(suffix + "_trapdoor", properties, blockSetType, autoLang, true);
        Block pressurePlate = eBlockRegistry.registerPressurePlate(suffix + "_pressure_plate", properties, blockSetType, autoLang, true);
        Block button = eBlockRegistry.registerButton(suffix + "_button", properties, blockSetType, buttonPressTicks, autoLang, true);
        Block shelf = eBlockRegistry.registerShelf(suffix + "_shelf", properties, autoLang, true);
        Block sign = eBlockRegistry.registerBlock(suffix + "_sign", settings -> new StandingSignBlock(woodType, settings), properties, autoLang);
        Block wallSign = eBlockRegistry.registerBlock(suffix + "_wall_sign", settings -> new WallSignBlock(woodType, settings), properties, autoLang);
        Item signItem = eItemRegistry.registerItem(suffix + "_sign", properties1 -> new SignItem(sign, wallSign, properties1),
                new Item.Properties().stacksTo(16),true, null);
        Block hangingSign = eBlockRegistry.registerBlock(suffix + "_hanging_sign", settings -> new CeilingHangingSignBlock(woodType, settings), properties, true);
        Block wallHangingSign = eBlockRegistry.registerBlock(suffix + "_wall_hanging_sign", settings -> new WallHangingSignBlock(woodType, settings), properties, true);
        Item hangingSignItem = eItemRegistry.registerItem(suffix + "_hanging_sign", properties1 -> new HangingSignItem(hangingSign, wallHangingSign, properties1),
                new Item.Properties().stacksTo(16),true, null);

        BlockEntityType.SIGN.addSupportedBlock(sign);
        BlockEntityType.SIGN.addSupportedBlock(wallSign);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(hangingSign);
        BlockEntityType.HANGING_SIGN.addSupportedBlock(wallHangingSign);
        return new WoodenBlockSet(log, strippedLog, wood, strippedWood, planks, stairs, slab, fence, fenceGate, door, trapdoor, pressurePlate, button, shelf, sign, wallSign
        ,signItem, hangingSign, wallHangingSign, hangingSignItem);
    }

    public static void addFlamables(WoodenBlockSet woodenBlockSet) {
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.log, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.strippedLog, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.wood, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.strippedWood, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.planks, 20, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.stairs, 20, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.slab, 20, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.fence, 20, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.fenceGate, 20, 5);
        FlammableBlockRegistry.getDefaultInstance().add(woodenBlockSet.shelf, 20, 5);
    }

    public static void addFuel(WoodenBlockSet woodenBlockSet) {
        FuelRegistryEvents.BUILD.register(((builder, context) -> {
            builder.add(woodenBlockSet.log, 300);
            builder.add(woodenBlockSet.strippedLog, 300);
            builder.add(woodenBlockSet.wood, 300);
            builder.add(woodenBlockSet.strippedWood, 300);
            builder.add(woodenBlockSet.planks, 300);
            builder.add(woodenBlockSet.stairs, 300);
            builder.add(woodenBlockSet.slab, 300);
            builder.add(woodenBlockSet.pressurePlate, 300);
            builder.add(woodenBlockSet.button, 300);
            builder.add(woodenBlockSet.fence, 300);
            builder.add(woodenBlockSet.fenceGate, 300);
        }));
    }

    public static void blockModelDataGen(WoodenBlockSet woodenBlockSet, BlockModelGenerators blockStateModelGenerator) {
        //Doesn't include signs
        blockStateModelGenerator.woodProvider(woodenBlockSet.log).logWithHorizontal(woodenBlockSet.log).wood(woodenBlockSet.wood);
        blockStateModelGenerator.woodProvider(woodenBlockSet.strippedLog).logWithHorizontal(woodenBlockSet.strippedLog).wood(woodenBlockSet.strippedWood);
        blockStateModelGenerator.family(woodenBlockSet.planks).stairs(woodenBlockSet.stairs).slab(woodenBlockSet.fence).fence(woodenBlockSet.fence)
                .fenceGate(woodenBlockSet.fenceGate).pressurePlate(woodenBlockSet.pressurePlate).button(woodenBlockSet.button);
        blockStateModelGenerator.createHangingSign(woodenBlockSet.strippedLog, woodenBlockSet.hangingSign, woodenBlockSet.wallHangingSign);
        blockStateModelGenerator.createTrapdoor(woodenBlockSet.trapdoor);
        blockStateModelGenerator.createDoor(woodenBlockSet.door);
        blockStateModelGenerator.createShelf(woodenBlockSet.shelf, woodenBlockSet.strippedLog);
    }

    public static void addLootTables(FabricBlockLootTableProvider fabricBlockLootTableProvider, WoodenBlockSet set) {
        //Doesn't include doors and slabs
        fabricBlockLootTableProvider.dropSelf(set.log);
        fabricBlockLootTableProvider.dropSelf(set.strippedLog);
        fabricBlockLootTableProvider.dropSelf(set.wood);
        fabricBlockLootTableProvider.dropSelf(set.strippedWood);
        fabricBlockLootTableProvider.dropSelf(set.planks);
        fabricBlockLootTableProvider.dropSelf(set.stairs);
        fabricBlockLootTableProvider.dropSelf(set.fence);
        fabricBlockLootTableProvider.dropSelf(set.fenceGate);
        fabricBlockLootTableProvider.dropSelf(set.trapdoor);
        fabricBlockLootTableProvider.dropSelf(set.button);
        fabricBlockLootTableProvider.dropSelf(set.pressurePlate);
        fabricBlockLootTableProvider.dropSelf(set.shelf);
        fabricBlockLootTableProvider.dropOther(set.sign, set.signItem);
        fabricBlockLootTableProvider.dropOther(set.wallSign, set.signItem);
        fabricBlockLootTableProvider.dropOther(set.hangingSign, set.hangingSignItem);
        fabricBlockLootTableProvider.dropOther(set.wallHangingSign, set.hangingSignItem);
    }

    public static void addRecipes(ERecipeGenerator enderlibRecipeGenerator, WoodenBlockSet set) {
        enderlibRecipeGenerator.stairs(set.planks, set.stairs);
        enderlibRecipeGenerator.sign(set.planks, set.signItem);
        enderlibRecipeGenerator.hangingSign(set.hangingSignItem, set.strippedLog);
        enderlibRecipeGenerator.slab(set.planks, set.slab);
        enderlibRecipeGenerator.woodFromLogs(set.wood, set.log);
        enderlibRecipeGenerator.woodFromLogs(set.strippedWood, set.strippedLog);
        enderlibRecipeGenerator.fence(set.planks, set.fence);
        enderlibRecipeGenerator.fenceGate(set.planks, set.fenceGate);
        enderlibRecipeGenerator.door(set.planks, set.door);
        enderlibRecipeGenerator.trapdoor(set.planks, set.trapdoor);
        enderlibRecipeGenerator.pressurePlate(set.pressurePlate, set.planks);
        enderlibRecipeGenerator.button(set.planks, set.button);
        enderlibRecipeGenerator.shelf(set.strippedLog, set.shelf);
    }

    public static void addToInventory(WoodenBlockSet woodenBlockSet, Item afterInBuildingTab, Item afterLogInNatureTable,Item afterShelf, Item afterSign) {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(afterInBuildingTab, woodenBlockSet.log, woodenBlockSet.wood, woodenBlockSet.strippedLog, woodenBlockSet.strippedWood,
                    woodenBlockSet.planks, woodenBlockSet.stairs, woodenBlockSet.slab, woodenBlockSet.fence, woodenBlockSet.fenceGate,
                    woodenBlockSet.door, woodenBlockSet.trapdoor, woodenBlockSet.pressurePlate, woodenBlockSet.button);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(afterLogInNatureTable, woodenBlockSet.log);
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.addAfter(afterShelf, woodenBlockSet.shelf);

            entries.addAfter(afterSign, woodenBlockSet.signItem, woodenBlockSet.hangingSignItem);
        });
    }

}
