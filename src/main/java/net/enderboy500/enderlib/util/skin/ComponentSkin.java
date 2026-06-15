package net.enderboy500.enderlib.util.skin;

import net.enderboy500.enderlib.util.ModifiedComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public abstract class ComponentSkin extends ModifierSkin {
    public ComponentSkin(String id, Identifier modelId) {
        super(id, modelId);
    }

    @Override
    public void modify(ItemStack item) {
        setComponents(item);
    }

    @Override
    public void resetDefaults(ItemStack item) {
        removeComponents(item);
    }


    public abstract <T> List<ModifiedComponent<T>> modifiedComponents(ItemStack stack);

    private void setComponents(ItemStack stack) {
        for (ModifiedComponent<Object> component : modifiedComponents(stack)) {
            stack.set(component.getComponentType(), component.getValue());
        }
    }

    private void removeComponents(ItemStack stack) {
        for (ModifiedComponent<Object> component : modifiedComponents(stack)) {
            if (!stack.getPrototype().has(component.getComponentType())) {
                stack.remove(component.getComponentType());
            } else {
                stack.set(component.getComponentType(), stack.getPrototype().get(component.getComponentType()));
            }
        }
    }
}
