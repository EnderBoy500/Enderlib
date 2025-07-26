package net.enderboy500.enderlib.helper;

import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

public class RecipeGeneratorHelper extends RecipeGenerator {
    public RecipeGeneratorHelper(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
        super(registries, exporter);
    }

    @Override
    public void generate() {

    }

    public void offer2x2CompactingRecipe(RecipeCategory category, ItemConvertible input, ItemConvertible output, int count) {
        this.createShaped(category, output, count)
                .input('#', input)
                .pattern("##")
                .pattern("##")
                .criterion(hasItem(output), conditionsFromItem(input))
                .offerTo(exporter);
    }

    public void offerReversible2x2CompactingRecipe(RecipeCategory category, ItemConvertible input, int count, RecipeCategory reverseCategory, ItemConvertible reverseInput, int reverseCount) {
        this.createShaped(category, reverseInput, count)
                .input('#', input)
                .pattern("##")
                .pattern("##")
                .criterion(hasItem(input), conditionsFromItem(input))
                .offerTo(exporter);
        this.createShapeless(reverseCategory, input, reverseCount)
                .input(reverseInput)
                .criterion(hasItem(reverseInput), conditionsFromItem(reverseInput))
                .offerTo(exporter);
    }

    public void offerHelmetRecipe(ItemConvertible ingredient, ItemConvertible helmet) {
        this.createShaped(RecipeCategory.COMBAT, helmet)
                .input('#', ingredient)
                .pattern("###")
                .pattern("# #")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerChestplateRecipe(ItemConvertible ingredient, ItemConvertible chestplate) {
        this.createShaped(RecipeCategory.COMBAT, chestplate)
                .input('#', ingredient)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerLeggingsRecipe(ItemConvertible ingredient, ItemConvertible leggings) {
        this.createShaped(RecipeCategory.COMBAT, leggings)
                .input('#', ingredient)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerBootsRecipe(ItemConvertible ingredient, ItemConvertible boots) {
        this.createShaped(RecipeCategory.COMBAT, boots)
                .input('#', ingredient)
                .pattern("# #")
                .pattern("# #")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void createFullArmorRecipes(ItemConvertible ingredient, ItemConvertible helmet, ItemConvertible chestplate, ItemConvertible leggings, ItemConvertible boots) {
        offerHelmetRecipe(ingredient, helmet);
        offerChestplateRecipe(ingredient, chestplate);
        offerLeggingsRecipe(ingredient, leggings);
        offerBootsRecipe(ingredient, boots);
    }

    public void offerSwordRecipe(ItemConvertible ingredient, ItemConvertible handle, ItemConvertible sword) {
        this.createShaped(RecipeCategory.COMBAT, sword)
                .input('#', ingredient)
                .input('/', handle)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerShovelRecipe(ItemConvertible ingredient, ItemConvertible handle, ItemConvertible shovel) {
        this.createShaped(RecipeCategory.TOOLS, shovel)
                .input('#', ingredient)
                .input('/', handle)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerPickaxeRecipe(ItemConvertible ingredient, ItemConvertible handle, ItemConvertible pickaxe) {
        this.createShaped(RecipeCategory.TOOLS, pickaxe)
                .input('#', ingredient)
                .input('/', handle)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerAxeRecipe(ItemConvertible ingredient, ItemConvertible handle, ItemConvertible axe) {
        this.createShaped(RecipeCategory.TOOLS, axe)
                .input('#', ingredient)
                .input('/', handle)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerHoeRecipe(ItemConvertible ingredient, ItemConvertible handle, ItemConvertible hoe) {
        this.createShaped(RecipeCategory.TOOLS, hoe)
                .input('#', ingredient)
                .input('/', handle)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .criterion(hasItem(ingredient), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void createToolRecipes(ItemConvertible ingredient, ItemConvertible handle, ItemConvertible sword, ItemConvertible shovel, ItemConvertible pickaxe, ItemConvertible axe, ItemConvertible hoe) {
        offerSwordRecipe(ingredient, handle, sword);
        offerShovelRecipe(ingredient, handle, shovel);
        offerPickaxeRecipe(ingredient, handle, pickaxe);
        offerAxeRecipe(ingredient, handle, axe);
        offerHoeRecipe(ingredient, handle, hoe);
    }

    public void offerStairsRecipe(ItemConvertible ingredient, ItemConvertible stairs) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .input('#', ingredient)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .criterion(hasItem(stairs), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerSlabRecipe(ItemConvertible ingredient, ItemConvertible slab) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .input('#', ingredient)
                .pattern("###")
                .criterion(hasItem(slab), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerFenceAndGateRecipes(ItemConvertible ingredient, ItemConvertible fence, ItemConvertible fenceGate) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .input('#', ingredient)
                .input('/', Items.STICK)
                .pattern("#/#")
                .pattern("#/#")
                .criterion(hasItem(fence), conditionsFromItem(ingredient))
                .offerTo(exporter);
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, fenceGate, 1)
                .input('#', ingredient)
                .input('/', Items.STICK)
                .pattern("/#/")
                .pattern("/#/")
                .criterion(hasItem(fenceGate), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerWallRecipe(ItemConvertible ingredient, ItemConvertible wall) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .input('#', ingredient)
                .pattern("###")
                .pattern("###")
                .criterion(hasItem(wall), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerDoorAndTrapdoorRecipes(ItemConvertible ingredient, ItemConvertible door, ItemConvertible trapdoor) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, door, 3)
                .input('#', ingredient)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .criterion(hasItem(door), conditionsFromItem(ingredient))
                .offerTo(exporter);
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, trapdoor, 6)
                .input('#', ingredient)
                .pattern("###")
                .pattern("###")
                .criterion(hasItem(trapdoor), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerPressurePlateAndButtonRecipes(ItemConvertible ingredient, ItemConvertible pressurePlate, ItemConvertible button) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, pressurePlate)
                .input('#', ingredient)
                .pattern("##")
                .criterion(hasItem(pressurePlate), conditionsFromItem(ingredient))
                .offerTo(exporter);
        this.createShapeless(RecipeCategory.BUILDING_BLOCKS, button)
                .input(ingredient)
                .criterion(hasItem(button), conditionsFromItem(ingredient))
                .offerTo(exporter);
    }
    public void offerWoodRecipe(ItemConvertible log, ItemConvertible wood, ItemConvertible strippedLog, ItemConvertible strippedWood) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, wood, log, 3);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, strippedWood, strippedLog, 3);
    }
    public void offerPlankRecipe(ItemConvertible log, ItemConvertible wood, ItemConvertible strippedLog, ItemConvertible strippedWood, ItemConvertible planks) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, log, planks, 4);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, wood, planks, 4);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, strippedLog, planks, 4);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, strippedWood, planks, 4);
    }
    public void offerSignsRecipe(ItemConvertible planks, ItemConvertible strippedLog, ItemConvertible sign, ItemConvertible hangingSign) {
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, sign, 3)
                .input('#', planks)
                .input('/', Items.STICK)
                .pattern("###")
                .pattern("###")
                .pattern(" / ")
                .criterion(hasItem(sign), conditionsFromItem(planks))
                .offerTo(exporter);
        this.createShaped(RecipeCategory.BUILDING_BLOCKS, hangingSign, 6)
                .input('#', strippedLog)
                .input('|', Items.CHAIN)
                .pattern("| |")
                .pattern("###")
                .pattern("###")
                .criterion(hasItem(hangingSign), conditionsFromItem(strippedLog))
                .offerTo(exporter);
    }
    public void offerBoatRecipes(ItemConvertible ingredient, ItemConvertible boat, ItemConvertible chestBoat) {
        this.createShaped(RecipeCategory.MISC, boat)
                .input('#', ingredient)
                .pattern("# #")
                .pattern("###")
                .criterion(hasItem(boat), conditionsFromItem(ingredient))
                .offerTo(exporter);
        this.createShapeless(RecipeCategory.MISC, chestBoat)
                .input(boat)
                .input(Items.CHEST)
                .criterion(hasItem(chestBoat), conditionsFromItem(boat))
                .offerTo(exporter);
    }
    public void createWoodSetRecipes(ItemConvertible planks, ItemConvertible log, ItemConvertible wood,
                                     ItemConvertible strippedLog, ItemConvertible strippedWood,
                                     ItemConvertible stairs, ItemConvertible slab,
                                     ItemConvertible fence, ItemConvertible fenceGate, ItemConvertible door,
                                     ItemConvertible trapdoor, ItemConvertible pressurePlate,
                                     ItemConvertible button) {
        offerWoodRecipe(log, wood, strippedLog, strippedWood);
        offerPlankRecipe(log, wood, strippedLog, strippedWood, planks);
        offerStairsRecipe(planks, stairs);
        offerSlabRecipe(planks, slab);
        offerFenceAndGateRecipes(planks, fence, fenceGate);
        offerDoorAndTrapdoorRecipes(planks, door, trapdoor);
        offerPressurePlateAndButtonRecipes(planks, pressurePlate, button);
    }
    public void createCompleteFullWoodSetRecipes(ItemConvertible planks, ItemConvertible log, ItemConvertible wood,
                                                 ItemConvertible strippedLog, ItemConvertible strippedWood,
                                                 ItemConvertible stairs, ItemConvertible slab,
                                                 ItemConvertible fence, ItemConvertible fenceGate,
                                                 ItemConvertible door, ItemConvertible trapdoor,
                                                 ItemConvertible pressurePlate, ItemConvertible button,
                                                 ItemConvertible sign, ItemConvertible hangingSign,
                                                 ItemConvertible boat, ItemConvertible chestBoat) {
        createWoodSetRecipes(planks, log, wood, strippedLog, strippedWood, stairs, slab, fence, fenceGate,
                door, trapdoor, pressurePlate, button);
        offerSignsRecipe(planks, strippedLog, sign, hangingSign);
        offerBoatRecipes(planks, boat, chestBoat);
    }
    public void offerStairsRecipeWithStonecutting(ItemConvertible ingredient, ItemConvertible stairs) {
        offerStairsRecipe(ingredient, stairs);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, stairs, ingredient);
    }
    public void offerSlabRecipeWithStonecutting(ItemConvertible ingredient, ItemConvertible slab) {
        offerStairsRecipe(ingredient, slab);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, slab, ingredient, 2);
    }
    public void offerWallRecipeWithStonecutting(ItemConvertible ingredient, ItemConvertible wall) {
        offerStairsRecipe(ingredient, wall);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, wall, ingredient);
    }
    public void createBrickRecipes(ItemConvertible baseStone, ItemConvertible bricks,
                                   ItemConvertible brickStairs, ItemConvertible brickSlab,
                                   ItemConvertible brickWall) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, baseStone, bricks, 4);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, bricks, baseStone);
        offerStairsRecipeWithStonecutting(bricks, brickStairs);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, brickStairs, baseStone);
        offerSlabRecipeWithStonecutting(bricks, brickSlab);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, brickSlab, baseStone, 2);
        offerWallRecipeWithStonecutting(bricks, brickWall);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, brickWall, baseStone);
    }
    public void createAllStoneRecipes(ItemConvertible stone, ItemConvertible stairs, ItemConvertible slab,
                                      ItemConvertible wall, ItemConvertible bricks,
                                      ItemConvertible brickStairs, ItemConvertible brickSlab,
                                      ItemConvertible brickWall) {
        offerStairsRecipeWithStonecutting(stone, stairs);
        offerSlabRecipeWithStonecutting(stone, slab);
        offerWallRecipeWithStonecutting(stone, wall);
        createBrickRecipes(stone, bricks, brickStairs, brickSlab, brickWall);
    }
    public void createCutMetalRecipes(ItemConvertible baseMetalBlock, ItemConvertible cutBlock,
                                      ItemConvertible cutStairs, ItemConvertible cutSlab) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, baseMetalBlock, cutBlock, 4);
        offerStairsRecipeWithStonecutting(cutBlock, cutStairs);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, cutStairs, baseMetalBlock);
        offerSlabRecipeWithStonecutting(cutBlock, cutSlab);
        offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, cutSlab, baseMetalBlock);
    }
}
