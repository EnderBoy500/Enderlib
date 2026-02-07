package net.enderboy500.enderlib.test;

import net.enderboy500.enderlib.client.armor.AdvancedArmor;
import net.enderboy500.enderlib.misc.HideName;
import net.enderboy500.enderlib.misc.ScreenShaker;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class TestItem extends ArrowItem implements HideName, AdvancedArmor {

    public TestItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = user.getEntityWorld();
        ScreenShaker.addScreenShake(user, 20, 100);
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public boolean hideName(ItemStack stack) {
        return true;
    }
}