package net.enderboy500.enderlib.util.skin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class ItemNameSkin extends ModifierSkin {
    private final Text text;

    public ItemNameSkin(String id, Identifier modelId, Text text) {
        super(id, modelId);
        this.text = text;
    }

    @Override
    public void modify(ItemStack item) {
        if (!item.contains(DataComponentTypes.CUSTOM_NAME)) item.set(DataComponentTypes.ITEM_NAME, text);
    }

    @Override
    public void resetDefaults(ItemStack item) {
        if (!item.contains(DataComponentTypes.CUSTOM_NAME)) item.set(DataComponentTypes.ITEM_NAME, item.getDefaultComponents().get(DataComponentTypes.ITEM_NAME));
    }
}
