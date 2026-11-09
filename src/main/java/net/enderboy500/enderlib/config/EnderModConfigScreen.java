package net.enderboy500.enderlib.config;

import net.enderboy500.enderlib.options.EnderLibOptionsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class EnderModConfigScreen extends OptionsSubScreen {
    private static List<Button> buttons = new ArrayList<>();

    public EnderModConfigScreen(Screen screen) {
        super(screen, Minecraft.getInstance().options, Component.translatable("config.enderlib.mods.title"));
    }

    public static Button makeButton(String translationKey, String narrationTranslationKey, int width, Button.OnPress onPress) {
        Button.Builder button = PlainTextButton.builder(Component.translatable(translationKey), onPress).width(width).createNarration((var0) -> Component.translatable(narrationTranslationKey));
        button.build().setTooltip(Tooltip.create(Component.translatable(translationKey)));
        return button.build();
    }

    @Override
    protected void addOptions() {
        List<AbstractWidget> abstractWidgets = new ArrayList<>();
        for (Button button : buttons) {
        abstractWidgets.add(this.addRenderableWidget(button));
        }
        this.list.addSmall(abstractWidgets);
    }

    public static void addButtonToConfigScreen(Button button) {
        buttons.add(button);
    }
}
