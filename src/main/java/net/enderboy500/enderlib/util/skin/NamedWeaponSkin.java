package net.enderboy500.enderlib.util.skin;

import net.enderboy500.enderlib.util.ModifiedComponent;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public class NamedWeaponSkin extends ComponentSkin{
    private final WeaponSkin.Modifier modifier;
    private final Component text;

    public NamedWeaponSkin(String id, Identifier modelId, WeaponSkin.Modifier modifier, Component text) {
        super(id, modelId);
        this.modifier = modifier;
        this.text = text;
    }

    @Override
    public <T> List<ModifiedComponent<T>> modifiedComponents(ItemStack stack) {
        return modifier.modifiedComponents();
    }

    @Override
    public void modify(ItemStack item) {
        super.modify(item);
        if (!item.has(DataComponents.CUSTOM_NAME)) item.set(DataComponents.ITEM_NAME, text);
    }

    @Override
    public void resetDefaults(ItemStack item) {
        if (!item.has(DataComponents.CUSTOM_NAME)) item.set(DataComponents.ITEM_NAME, item.getPrototype().get(DataComponents.ITEM_NAME));
    }
}
