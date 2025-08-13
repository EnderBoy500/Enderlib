package net.enderboy500.enderlib.item;

import net.minecraft.entity.player.PlayerEntity;

public class TestToolItem extends ToolFunctionItem {
    public TestToolItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean pathFunction(PlayerEntity player) {
        return true;
    }
}
