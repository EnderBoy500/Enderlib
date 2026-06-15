package net.enderboy500.enderlib.util.skin;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public abstract class ModifierSkin extends ItemSkin {
    public ModifierSkin(String id, Identifier modelId) {
        super(id, modelId);
    }

    public abstract void modify(ItemStack item);
    public abstract void resetDefaults(ItemStack item);
}
