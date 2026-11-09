package net.enderboy500.enderlib.options;

import net.enderboy500.enderlib.EnderLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

public class EnderLibOptionsScreen extends OptionsSubScreen {
    public EnderLibOptionsScreen(Screen lastScreen) {
        super(lastScreen, Minecraft.getInstance().options, Component.translatable("options.enderlib.title"));
    }

    @Override
    public void onClose() {
        super.onClose();
        EnderLib.options.save();
    }

    @Override
    protected void addOptions() {
        this.list.addSmall(EnderLib.options.equipmentStateCycleKey);
    }
}
