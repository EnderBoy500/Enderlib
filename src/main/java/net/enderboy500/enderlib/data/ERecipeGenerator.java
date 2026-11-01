package net.enderboy500.enderlib.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public class ERecipeGenerator extends RecipeProvider {
    public ERecipeGenerator(HolderLookup.Provider registries, RecipeOutput recipeOutput) {
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
    public void pane(ItemLike ingredient, ItemLike outputItem) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, outputItem, 16)
                .define('#', ingredient)
                .pattern("###")
                .pattern("###")
                .unlockedBy(getHasName(outputItem), has(ingredient))
                .save(output);
    }
    public void offerPlankRecipe(ItemLike log, ItemLike wood, ItemLike strippedLog, ItemLike strippedWood, ItemLike planks) {
        shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4).requires(Ingredient.of(log, wood, strippedLog, strippedWood)).unlockedBy(getHasName(planks), has(log)).save(output);
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
        this.shaped(RecipeCategory.BUILDING_BLOCKS, shelf, 6)
                .define('#', ingredient)
                .pattern("###")
                .pattern("   ")
                .pattern("###")
                .unlockedBy(getHasName(shelf), has(ingredient))
                .save(output);
    }
    public void stairs(ItemLike ingredient, ItemLike stair) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, stair, 4)
                .define('#', ingredient)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy(getHasName(stair), has(ingredient))
                .save(output);
    }
    public void slab(ItemLike ingredient, ItemLike slab) {
        this.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                .define('#', ingredient)
                .pattern("###")
                .unlockedBy(getHasName(slab), has(ingredient))
                .save(output);
    }
    public void door(ItemLike ingredient, ItemLike door) {
        this.shaped(RecipeCategory.REDSTONE, door, 3).define('#', ingredient).pattern("##").pattern("##").pattern("##").unlockedBy(getHasName(door), has(ingredient))
                .save(output);
    }
    public void trapdoor(ItemLike ingredient, ItemLike trapdoor) {
        this.shaped(RecipeCategory.REDSTONE, trapdoor, 2).define('#', ingredient).pattern("###").pattern("###").unlockedBy(getHasName(trapdoor), has(ingredient))
                .save(output);
    }
    public void fence(ItemLike ingredient, ItemLike fence) {
        this.shaped(RecipeCategory.DECORATIONS, fence, 3).define('W', ingredient).define('#', Items.STICK).pattern("W#W").pattern("W#W").unlockedBy(getHasName(fence), has(ingredient))
                .save(output);
    }
    public void fenceGate(ItemLike ingredient, ItemLike fenceGate) {
        this.shaped(RecipeCategory.REDSTONE, fenceGate).define('#', Items.STICK).define('W', ingredient).pattern("#W#").pattern("#W#").unlockedBy(getHasName(fenceGate), has(ingredient))
                .save(output);
    }
    public void sign(ItemLike ingredient, ItemLike sign) {
        this.shaped(RecipeCategory.DECORATIONS, sign, 3).group("sign").define('#', ingredient).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ").unlockedBy(getHasName(sign), has(ingredient))
                .save(output);
    }
    public void button(ItemLike ingredient, ItemLike button) {
        this.shapeless(RecipeCategory.REDSTONE, button).requires(ingredient).unlockedBy(getHasName(button), has(ingredient))
                .save(output);
    }
    public void offerStairsRecipeWithStonecutting(ItemLike ingredient, ItemLike stairs) {
        stairs(ingredient, stairs);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, stairs, ingredient);
    }
    public void offerButtonRecipeWithStonecutting(ItemLike ingredient, ItemLike button) {
        button(ingredient, button);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, button, ingredient);
    }
    public void offerSlabRecipeWithStonecutting(ItemLike ingredient, ItemLike slab) {
        slab(ingredient, slab);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, slab, ingredient, 2);
    }
    public void offerPressureRecipeWithStonecutting(ItemLike ingredient, ItemLike slab) {
        pressurePlate(ingredient, slab);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, slab, ingredient, 2);
    }
    public void offerWallRecipeWithStonecutting(ItemLike ingredient, ItemLike wall) {
        wall(RecipeCategory.DECORATIONS,wall, ingredient);
        stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, wall, ingredient);
    }
}
