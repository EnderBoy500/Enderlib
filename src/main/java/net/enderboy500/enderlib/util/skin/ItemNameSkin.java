package net.enderboy500.enderlib.util.skin;

import java.util.Objects;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class ItemNameSkin extends ModifierSkin {
    private final Component text;

    public ItemNameSkin(String id, Identifier modelId, Component text) {
        super(id, modelId);
        this.text = text;
    }

    @Override
    public void modify(ItemStack item) {
        if (!item.has(DataComponents.CUSTOM_NAME)) item.set(DataComponents.ITEM_NAME, text);
    }

    @Override
    public void resetDefaults(ItemStack item) {
        if (!item.has(DataComponents.CUSTOM_NAME)) item.set(DataComponents.ITEM_NAME, item.getPrototype().get(DataComponents.ITEM_NAME));
    }
}
