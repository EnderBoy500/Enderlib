package net.enderboy500.enderlib.helper;

import java.util.List;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class EnderlibRecipeGenerator extends RecipeProvider {
    public EnderlibRecipeGenerator(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
        super(registries, recipeOutput);
    }

    @Override
    public void buildRecipes() {
    }

    public void offer2x2CompactingRecipe(RecipeCategory category, ItemLike input, ItemLike outputItem, int count) {
        this.shaped(category, outputItem, count)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(outputItem), has(input))
                .save(output);
    }

    public void offerReversible2x2CompactingRecipe(RecipeCategory category, ItemLike input, int count, RecipeCategory reverseCategory, ItemLike reverseInput, int reverseCount) {
        this.shaped(category, reverseInput, count)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(reverseInput), has(input))
                .save(output);
        this.shapeless(reverseCategory, input, reverseCount)
                .requires(reverseInput)
                .unlockedBy(getHasName(input), has(reverseInput))
                .save(output);
    }

    public void offerHelmetRecipe(ItemLike ingredient, ItemLike helmet) {
        this.shaped(RecipeCategory.COMBAT, helmet)
                .define('#', ingredient)
                .pattern("###")
                .pattern("# #")
                .unlockedBy(getHasName(helmet), has(ingredient))
                .save(output);
    }
    public void offerChestplateRecipe(ItemLike ingredient, ItemLike chestplate) {
        this.shaped(RecipeCategory.COMBAT, chestplate)
                .define('#', ingredient)
                .pattern("# #")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(chestplate), has(ingredient))
                .save(output);
    }
    public void offerLeggingsRecipe(ItemLike ingredient, ItemLike leggings) {
        this.shaped(RecipeCategory.COMBAT, leggings)
                .define('#', ingredient)
                .pattern("###")
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(leggings), has(ingredient))
                .save(output);
    }
    public void offerBootsRecipe(ItemLike ingredient, ItemLike boots) {
        this.shaped(RecipeCategory.COMBAT, boots)
                .define('#', ingredient)
                .pattern("# #")
                .pattern("# #")
                .unlockedBy(getHasName(boots), has(ingredient))
                .save(output);
    }
    public void createFullArmorRecipes(ItemLike ingredient, ItemLike helmet, ItemLike chestplate, ItemLike leggings, ItemLike boots) {
        offerHelmetRecipe(ingredient, helmet);
        offerChestplateRecipe(ingredient, chestplate);
        offerLeggingsRecipe(ingredient, leggings);
        offerBootsRecipe(ingredient, boots);
    }

    public void offerSwordRecipe(ItemLike ingredient, ItemLike handle, ItemLike sword) {
        this.shaped(RecipeCategory.COMBAT, sword)
                .define('#', ingredient)
                .define('/', handle)
                .pattern("#")
                .pattern("#")
                .pattern("/")
                .unlockedBy(getHasName(sword), has(ingredient))
                .save(output);
    }
    public void offerShovelRecipe(ItemLike ingredient, ItemLike handle, ItemLike shovel) {
        this.shaped(RecipeCategory.TOOLS, shovel)
                .define('#', ingredient)
                .define('/', handle)
                .pattern("#")
                .pattern("/")
                .pattern("/")
                .unlockedBy(getHasName(shovel), has(ingredient))
                .save(output);
    }
    public void offerPickaxeRecipe(ItemLike ingredient, ItemLike handle, ItemLike pickaxe) {
        this.shaped(RecipeCategory.TOOLS, pickaxe)
                .define('#', ingredient)
                .define('/', handle)
                .pattern("###")
                .pattern(" / ")
                .pattern(" / ")
                .unlockedBy(getHasName(pickaxe), has(ingredient))
                .save(output);
    }
    public void offerAxeRecipe(ItemLike ingredient, ItemLike handle, ItemLike axe) {
        this.shaped(RecipeCategory.TOOLS, axe)
                .define('#', ingredient)
                .define('/', handle)
                .pattern("##")
                .pattern("#/")
                .pattern(" /")
                .unlockedBy(getHasName(axe), has(ingredient))
                .save(output);
    }
    public void offerHoeRecipe(ItemLike ingredient, ItemLike handle, ItemLike hoe) {
        this.shaped(RecipeCategory.TOOLS, hoe)
                .define('#', ingredient)
                .define('/', handle)
                .pattern("##")
                .pattern(" /")
                .pattern(" /")
                .unlockedBy(getHasName(hoe), has(ingredient))
                .save(output);
    }
    public void createToolRecipes(ItemLike ingredient, ItemLike handle, ItemLike sword, ItemLike shovel, ItemLike pickaxe, ItemLike axe, ItemLike hoe) {
        offerSwordRecipe(ingredient, handle, sword);
        offerShovelRecipe(ingredient, handle, shovel);
        offerPickaxeRecipe(ingredient, handle, pickaxe);
        offerAxeRecipe(ingredient, handle, axe);
        offerHoeRecipe(ingredient, handle, hoe);
    }

    public void offerStairsRecipe(ItemLike ingredient, ItemLike stairs) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 4)
                .define('#', ingredient)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy(getHasName(stairs), has(ingredient))
                .save(output);
    }
    public void offerSlabRecipe(ItemLike ingredient, ItemLike slab) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .define('#', ingredient)
                .pattern("###")
                .unlockedBy(getHasName(slab), has(ingredient))
                .save(output);
    }
    public void offerFenceAndGateRecipes(ItemLike ingredient, ItemLike fence, ItemLike fenceGate) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                .define('#', ingredient)
                .define('/', Items.STICK)
                .pattern("#/#")
                .pattern("#/#")
                .unlockedBy(getHasName(fence), has(ingredient))
                .save(output);
        this.shaped(RecipeCategory.BUILDING_BLOCKS, fenceGate, 1)
                .define('#', ingredient)
                .define('/', Items.STICK)
                .pattern("/#/")
                .pattern("/#/")
                .unlockedBy(getHasName(fenceGate), has(ingredient))
                .save(output);
    }
    public void offerWallRecipe(ItemLike ingredient, ItemLike wall) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                .define('#', ingredient)
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(wall), has(ingredient))
                .save(output);
    }

    public void offerBarOrPaneRecipe(ItemLike ingredient, ItemLike outputItem) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, outputItem, 16)
                .define('#', ingredient)
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(outputItem), has(ingredient))
                .save(output);
    }
    public void offerDoorAndTrapdoorRecipes(ItemLike ingredient, ItemLike door, ItemLike trapdoor) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, door, 3)
                .define('#', ingredient)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(door), has(ingredient))
                .save(output);
        this.shaped(RecipeCategory.BUILDING_BLOCKS, trapdoor, 6)
                .define('#', ingredient)
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(trapdoor), has(ingredient))
                .save(output);
    }
    public void offerPressurePlateAndButtonRecipes(ItemLike ingredient, ItemLike pressurePlate, ItemLike button) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, pressurePlate)
                .define('#', ingredient)
                .pattern("##")
                .unlockedBy(getHasName(pressurePlate), has(ingredient))
                .save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, button)
                .requires(ingredient)
                .unlockedBy(getHasName(button), has(ingredient))
                .save(output);
    }
    public void offerWoodRecipe(ItemLike log, ItemLike wood, ItemLike strippedLog, ItemLike strippedWood) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, wood, log, 3);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, strippedWood, strippedLog, 3);
    }
    public void offerPlankRecipe(ItemLike log, ItemLike wood, ItemLike strippedLog, ItemLike strippedWood, ItemLike planks) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, log, planks, 4);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, wood, planks, 4);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, strippedLog, planks, 4);
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, strippedWood, planks, 4);
    }
    public void offerSignsRecipe(ItemLike planks, ItemLike strippedLog, ItemLike sign, ItemLike hangingSign) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, sign, 3)
                .define('#', planks)
                .define('/', Items.STICK)
                .pattern("###")
                .pattern("###")
                .pattern(" / ")
                .unlockedBy(getHasName(sign), has(planks))
                .save(output);
        this.shaped(RecipeCategory.BUILDING_BLOCKS, hangingSign, 6)
                .define('#', strippedLog)
                .define('|', Items.IRON_CHAIN)
                .pattern("| |")
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(hangingSign), has(strippedLog))
                .save(output);
    }
    public void offerBoatRecipes(ItemLike ingredient, ItemLike boat, ItemLike chestBoat) {
        this.shaped(RecipeCategory.MISC, boat)
                .define('#', ingredient)
                .pattern("# #")
                .pattern("###")
                .unlockedBy(getHasName(boat), has(ingredient))
                .save(output);
        this.shapeless(RecipeCategory.MISC, chestBoat)
                .requires(boat)
                .requires(Items.CHEST)
                .unlockedBy(getHasName(chestBoat), has(boat))
                .save(output);
    }
    public void shelf(ItemLike ingredient, ItemLike shelf) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, shelf)
                .define('#', ingredient)
                .pattern("###")
                .pattern("   ")
                .pattern("###")
                .unlockedBy(getHasName(shelf), has(ingredient))
                .save(output);
    }
    public void createWoodSetRecipes(ItemLike planks, ItemLike log, ItemLike wood,
                                     ItemLike strippedLog, ItemLike strippedWood,
                                     ItemLike stairs, ItemLike slab,
                                     ItemLike fence, ItemLike fenceGate, ItemLike door,
                                     ItemLike trapdoor, ItemLike pressurePlate,
                                     ItemLike button, ItemLike shelf) {
        offerWoodRecipe(log, wood, strippedLog, strippedWood);
        offerPlankRecipe(log, wood, strippedLog, strippedWood, planks);
        offerStairsRecipe(planks, stairs);
        offerSlabRecipe(planks, slab);
        offerFenceAndGateRecipes(planks, fence, fenceGate);
        offerDoorAndTrapdoorRecipes(planks, door, trapdoor);
        offerPressurePlateAndButtonRecipes(planks, pressurePlate, button);
        shelf(strippedLog, shelf);
    }
    public void createCompleteFullWoodSetRecipes(ItemLike planks, ItemLike log, ItemLike wood,
                                                 ItemLike strippedLog, ItemLike strippedWood,
                                                 ItemLike stairs, ItemLike slab,
                                                 ItemLike fence, ItemLike fenceGate,
                                                 ItemLike door, ItemLike trapdoor,
                                                 ItemLike pressurePlate, ItemLike button,
                                                 ItemLike shelf, ItemLike sign,
                                                 ItemLike hangingSign, ItemLike boat,
                                                 ItemLike chestBoat) {
        createWoodSetRecipes(planks, log, wood, strippedLog, strippedWood, stairs, slab, fence, fenceGate,
                door, trapdoor, pressurePlate, button, shelf);
        offerSignsRecipe(planks, strippedLog, sign, hangingSign);
        offerBoatRecipes(planks, boat, chestBoat);
    }
    public void offerStairsRecipeWithStonecutting(ItemLike ingredient, ItemLike stairs) {
        offerStairsRecipe(ingredient, stairs);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stairs, ingredient);
    }
    public void offerSlabRecipeWithStonecutting(ItemLike ingredient, ItemLike slab) {
        offerSlabRecipe(ingredient, slab);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, slab, ingredient, 2);
    }
    public void offerWallRecipeWithStonecutting(ItemLike ingredient, ItemLike wall) {
        offerWallRecipe(ingredient, wall);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, wall, ingredient);
    }
    public void createBrickRecipes(ItemLike baseStone, ItemLike bricks,
                                   ItemLike brickStairs, ItemLike brickSlab,
                                   ItemLike brickWall) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, baseStone, bricks, 4);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, bricks, baseStone);
        offerStairsRecipeWithStonecutting(bricks, brickStairs);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, brickStairs, baseStone);
        offerSlabRecipeWithStonecutting(bricks, brickSlab);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, brickSlab, baseStone, 2);
        offerWallRecipeWithStonecutting(bricks, brickWall);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, brickWall, baseStone);
    }
    public void createBaseStoneRecipes(ItemLike stone,
                                       ItemLike stoneStairs, ItemLike stoneSlab,
                                       ItemLike stoneWall) {
        offerStairsRecipeWithStonecutting(stone, stoneStairs);
        offerSlabRecipeWithStonecutting(stone, stoneSlab);
        offerWallRecipeWithStonecutting(stone, stoneWall);
    }
    public void createBasicStoneRecipes(ItemLike stone, ItemLike stairs, ItemLike slab,
                                      ItemLike wall, ItemLike chiseledBricks, ItemLike bricks,
                                      ItemLike brickStairs, ItemLike brickSlab,
                                      ItemLike brickWall) {
        offerStairsRecipeWithStonecutting(stone, stairs);
        offerSlabRecipeWithStonecutting(stone, slab);
        offerWallRecipeWithStonecutting(stone, wall);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stone, chiseledBricks);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, bricks, chiseledBricks);
        this.shaped(RecipeCategory.BUILDING_BLOCKS, chiseledBricks)
                        .define('#', brickSlab)
                        .pattern("#")
                        .pattern("#")
                        .unlockedBy(getHasName(chiseledBricks), has(brickSlab))
                        .save(output);
        createBrickRecipes(stone, bricks, brickStairs, brickSlab, brickWall);
    }
    public void createTuffStoneRecipes(ItemLike stone, ItemLike stairs, ItemLike slab,
                                        ItemLike wall, ItemLike chiseledStone, ItemLike polished,
                                       ItemLike polishedStairs, ItemLike polishedSlab,
                                       ItemLike polishedWall, ItemLike chiseledBricks, ItemLike bricks,
                                        ItemLike brickStairs, ItemLike brickSlab,
                                        ItemLike brickWall) {
        offerStairsRecipeWithStonecutting(stone, stairs);
        offerSlabRecipeWithStonecutting(stone, slab);
        offerWallRecipeWithStonecutting(stone, wall);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stone, chiseledBricks);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, bricks, chiseledBricks);
        this.shaped(RecipeCategory.BUILDING_BLOCKS, chiseledBricks)
                .define('#', brickSlab)
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(chiseledBricks), has(brickSlab))
                .save(output);
        this.shaped(RecipeCategory.BUILDING_BLOCKS, chiseledStone)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(chiseledStone), has(slab))
                .save(output);
        createBrickRecipes(stone, polished, polishedStairs, polishedSlab, polishedWall);
        createBrickRecipes(polished, bricks, brickStairs, brickSlab, brickWall);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stone, bricks);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stone, brickSlab);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stone, brickStairs);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stone, brickWall);
    }
    public void createCutMetalRecipes(ItemLike baseMetalBlock, ItemLike cutBlock,
                                      ItemLike cutStairs, ItemLike cutSlab) {
        offer2x2CompactingRecipe(RecipeCategory.BUILDING_BLOCKS, baseMetalBlock, cutBlock, 4);
        offerStairsRecipeWithStonecutting(cutBlock, cutStairs);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, cutStairs, baseMetalBlock);
        offerSlabRecipeWithStonecutting(cutBlock, cutSlab);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, cutSlab, baseMetalBlock);
    }

    public void createFullMetalSmeltGenerator(ItemLike ore, ItemLike deepslateOre,
                                         ItemLike ingot,
                                         ItemLike raw) {
        List<ItemLike> RAW = List.of(raw, ore, deepslateOre);
        oreSmelting(RAW, RecipeCategory.MISC, ingot, 0.7f,200, ingot.toString());
    }

    public void createAllDyingRecipes(ItemLike base, ItemLike white, ItemLike lightGray,
                                      ItemLike gray, ItemLike black, ItemLike brown,
                                      ItemLike red, ItemLike orange, ItemLike yellow,
                                      ItemLike lime, ItemLike green, ItemLike cyan,
                                      ItemLike lightBlue, ItemLike blue, ItemLike purple,
                                      ItemLike magenta, ItemLike pink) {
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, white).requires(base).requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(white), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, lightGray).requires(base).requires(Items.LIGHT_GRAY_DYE)
                .unlockedBy(getHasName(lightGray), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, gray).requires(base).requires(Items.GRAY_DYE)
                .unlockedBy(getHasName(gray), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, black).requires(base).requires(Items.BLACK_DYE)
                .unlockedBy(getHasName(black), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, brown).requires(base).requires(Items.BROWN_DYE)
                .unlockedBy(getHasName(brown), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, red).requires(base).requires(Items.RED_DYE)
                .unlockedBy(getHasName(red), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, orange).requires(base).requires(Items.ORANGE_DYE)
                .unlockedBy(getHasName(orange), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, yellow).requires(base).requires(Items.YELLOW_DYE)
                .unlockedBy(getHasName(yellow), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, lime).requires(base).requires(Items.LIME_DYE)
                .unlockedBy(getHasName(lime), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, green).requires(base).requires(Items.GREEN_DYE)
                .unlockedBy(getHasName(green), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, cyan).requires(base).requires(Items.CYAN_DYE)
                .unlockedBy(getHasName(cyan), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, lightBlue).requires(base).requires(Items.LIGHT_BLUE_DYE)
                .unlockedBy(getHasName(lightBlue), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, blue).requires(base).requires(Items.BLUE_DYE)
                .unlockedBy(getHasName(blue), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, purple).requires(base).requires(Items.PURPLE_DYE)
                .unlockedBy(getHasName(purple), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, magenta).requires(base).requires(Items.MAGENTA_DYE)
                .unlockedBy(getHasName(magenta), has(base)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, pink).requires(base).requires(Items.PINK_DYE)
                .unlockedBy(getHasName(pink), has(base)).save(output);
    }

    public void createAllUniversalDyingRecipes(TagKey<Item> base, ItemLike white, ItemLike lightGray,
                                               ItemLike gray, ItemLike black, ItemLike brown,
                                               ItemLike red, ItemLike orange, ItemLike yellow,
                                               ItemLike lime, ItemLike green, ItemLike cyan,
                                               ItemLike lightBlue, ItemLike blue, ItemLike purple,
                                               ItemLike magenta, ItemLike pink) {
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, white).requires(base).requires(Items.WHITE_DYE)
                .unlockedBy(getHasName(white), has(white)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, lightGray).requires(base).requires(Items.LIGHT_GRAY_DYE)
                .unlockedBy(getHasName(lightGray), has(lightGray)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, gray).requires(base).requires(Items.GRAY_DYE)
                .unlockedBy(getHasName(gray), has(gray)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, black).requires(base).requires(Items.BLACK_DYE)
                .unlockedBy(getHasName(black), has(black)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, brown).requires(base).requires(Items.BROWN_DYE)
                .unlockedBy(getHasName(brown), has(brown)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, red).requires(base).requires(Items.RED_DYE)
                .unlockedBy(getHasName(red), has(red)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, orange).requires(base).requires(Items.ORANGE_DYE)
                .unlockedBy(getHasName(orange), has(orange)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, yellow).requires(base).requires(Items.YELLOW_DYE)
                .unlockedBy(getHasName(yellow), has(yellow)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, lime).requires(base).requires(Items.LIME_DYE)
                .unlockedBy(getHasName(lime), has(lime)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, green).requires(base).requires(Items.GREEN_DYE)
                .unlockedBy(getHasName(green), has(green)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, cyan).requires(base).requires(Items.CYAN_DYE)
                .unlockedBy(getHasName(cyan), has(cyan)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, lightBlue).requires(base).requires(Items.LIGHT_BLUE_DYE)
                .unlockedBy(getHasName(lightBlue), has(lightBlue)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, blue).requires(base).requires(Items.BLUE_DYE)
                .unlockedBy(getHasName(blue), has(blue)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, purple).requires(base).requires(Items.PURPLE_DYE)
                .unlockedBy(getHasName(purple), has(purple)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, magenta).requires(base).requires(Items.MAGENTA_DYE)
                .unlockedBy(getHasName(magenta), has(magenta)).save(output);
        this.shapeless(RecipeCategory.BUILDING_BLOCKS, pink).requires(base).requires(Items.PINK_DYE)
                .unlockedBy(getHasName(pink), has(pink)).save(output);
    }
}
