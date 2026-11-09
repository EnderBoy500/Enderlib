package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.config.EnderModConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin extends OptionsSubScreen {
    public ControlsScreenMixin(Screen screen, Options options, Component component) {
        super(screen, options, component);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    public void enderlib$configs(CallbackInfo ci) {
        List<AbstractWidget> widgets = new ArrayList();
        widgets.add(this.addRenderableWidget(EnderModConfigScreen.makeButton("config.enderlib.mods.title", "config.enderlib.mods.title",
                150, (var) -> Minecraft.getInstance().setScreenAndShow(new EnderModConfigScreen(this)))));
        list.addSmall(widgets);
    }
}
