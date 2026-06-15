package net.enderboy500.enderlib.util;

import com.ibm.icu.impl.UResource;
import net.minecraft.core.component.DataComponentType;

public class ModifiedComponent<T> {
    private final DataComponentType<T> componentType;
    private final T v;

    public ModifiedComponent(DataComponentType<T> type, T value) {
        componentType = type;
        v = value;
    }

    public static <S> ModifiedComponent create(DataComponentType<S> type, S val) {
        return new ModifiedComponent(type, val);
    }

    public DataComponentType<T> getComponentType() {
        return componentType;
    }

    public T getValue() {
        return v;
    }
}
