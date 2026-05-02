package net.enderboy500.enderlib;

import net.enderboy500.enderlib.helper.RegistryHelper;
import net.enderboy500.enderlib.util.ItemUtils;
import net.enderboy500.enderlib.util.skin.ItemSkin;
import net.enderboy500.enderlib.util.skin.ItemSkinRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class EnderLibTest {
    public static final Item TEST = RegistryHelper.registerItem("test", Item::new, new Item.Settings());
    public static final ItemSkin COMMAND_TEST = new ItemSkin("test_command", Identifier.ofVanilla("blaze_rod"));
    public static void load() {
        ItemUtils.applyShield(TEST, 140, ItemTags.REPAIRS_IRON_ARMOR, 1);
        ItemSkinRegistry.registerSkin(Items.STICK, COMMAND_TEST);
    }
}
