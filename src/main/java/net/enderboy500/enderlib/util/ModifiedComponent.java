package net.enderboy500.enderlib.util;

import com.ibm.icu.impl.UResource;
import net.minecraft.component.ComponentType;

public class ModifiedComponent<T> {
    private final ComponentType<T> componentType;
    private final T v;

    public ModifiedComponent(ComponentType<T> type, T value) {
        componentType = type;
        v = value;
    }

    public static <S> ModifiedComponent create(ComponentType<S> type, S val) {
        return new ModifiedComponent(type, val);
    }

    public ComponentType<T> getComponentType() {
        return componentType;
    }

    public T getValue() {
        return v;
    }
}
