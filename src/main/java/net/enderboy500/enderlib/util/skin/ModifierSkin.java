package net.enderboy500.enderlib.util.skin;

import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class ModifierSkin extends ItemSkin {
    public ModifierSkin(String id, Identifier modelId) {
        super(id, modelId);
    }

    public abstract void modify(ItemStack item);
    public abstract void resetDefaults(ItemStack item);
}
