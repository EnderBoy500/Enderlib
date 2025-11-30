package net.enderboy500.enderlib.test;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.helper.NameStyleHelper;
import net.enderboy500.enderlib.item.CycleEquipmentStateBool;
import net.enderboy500.enderlib.item.RightClickEquipmentCycleItem;
import net.enderboy500.enderlib.item.SlotChangeFunction;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

public class TestItem extends Item implements SlotChangeFunction {

    public TestItem(Settings settings) {
        super(settings);
    }

    @Override
    public void slotChangeFunction(ItemStack stack, boolean bl) {
        EnderLib.LOGGER.info("3");
    }

    @Override
    public Text getName(ItemStack stack) {
        return NameStyleHelper.styledColoredNameWithFont("item.enderlib.text", TextColor.fromFormatting(Formatting.GOLD),false,false,false,false, false, Identifier.ofVanilla("illageralt"));
    }
}
