package net.enderboy500.enderlib.util.skin;

import net.enderboy500.enderlib.util.ModifiedComponent;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class NamedWeaponSkin extends ComponentSkin{
    private final WeaponSkin.Modifier modifier;
    private final Text text;

    public NamedWeaponSkin(String id, Identifier modelId, WeaponSkin.Modifier modifier, Text text) {
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
        if (!item.contains(DataComponentTypes.CUSTOM_NAME)) item.set(DataComponentTypes.CUSTOM_NAME, text);
    }

    @Override
    public void resetDefaults(ItemStack item) {
        super.resetDefaults(item);
        if (item.getCustomName().equals(text)) item.remove(DataComponentTypes.CUSTOM_NAME);
    }
}
