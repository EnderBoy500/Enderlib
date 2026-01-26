package net.enderboy500.enderlib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

import javax.swing.*;

public class GlintItem extends Item {
    public GlintItem(Settings settings) {
        super(settings);
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
