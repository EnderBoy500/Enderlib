package net.enderboy500.enderlib.options;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.inventory.ClickType;

public enum EquipmentStateCycleKeys implements StringRepresentable {
    SWAP("swap", ClickType.SWAP),
    QUICK_MOVE("quick_move", ClickType.QUICK_MOVE),
    RIGHT_CLICK("right_click", ClickType.CLONE)
    ;

    public static final Codec<EquipmentStateCycleKeys> CODEC = StringRepresentable.fromEnum(EquipmentStateCycleKeys::values);
    private String name;
    private ClickType clickType;
    private final Component translatable;
    private final Component tooltip;

    EquipmentStateCycleKeys(String name, ClickType clickType) {
        this.name = name;
        this.clickType = clickType;
        this.translatable = Component.translatable("option.enderlib.equipmentStateCycleKeys." + name);
        this.tooltip = Component.translatable("options.enderlib.equipmentStateCycleKeys").append(": ").append(Component.translatable("option.enderlib.equipmentStateCycleKeys." + name));
    }

    public ClickType getClickType() {
        return clickType;
    }

    public String getName() {
        return name;
    }

    public Component getTooltip() {
        return tooltip;
    }

    public Component getTranslatable() {
        return translatable;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
