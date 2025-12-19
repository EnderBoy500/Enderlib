package net.enderboy500.enderlib;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.ControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EnderLibModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return EnderLibModMenu::buildMenu;
    }

    private static Screen buildMenu(Screen parent) {
        return YetAnotherConfigLib.create(
                EnderLibConfig.HANDLER, (defaults, config, builder) -> builder.title(Text.translatable("config.enderlib.menu"))
                        .category(EnderLibModMenu.createCategory(config))
        ).generateScreen(parent);
    }

    private static <T> Option<T> enumOption(String name, T defaultValue, Supplier<T> getter, Consumer<T> setter, Function<Option<T>, ControllerBuilder<T>> builder) {
        return Option.<T>createBuilder()
                .name(Text.translatable("config.enderlib.option." + name))
                .binding(defaultValue, getter, setter)
                .description(OptionDescription.of(Text.translatable("config.enderlib.option." + name + ".desc")))
                .controller(builder)
                .build();
    }

    private static ConfigCategory createCategory(EnderLibConfig config) {

        ConfigCategory.Builder main = ConfigCategory.createBuilder().name(Text.translatable("config.enderlib.category.main"));

        addMainOptions(config, main);

        return main.build();
    }

    private static void addMainOptions(EnderLibConfig config, ConfigCategory.Builder builder) {

        Option<?> swapKey = enumOption(
                "swap_key",
                EquipmentStateCycleKeys.RightClick,
                () -> config.swapKey,
                value -> config.swapKey = value,
                opt -> EnumControllerBuilder.create(opt)
                        .enumClass(EquipmentStateCycleKeys.class)
                        .valueFormatter(v -> Text.translatable("config.enderlib.option.swap_key.text." + v.name().toLowerCase()))
        );

        builder.group(OptionGroup.createBuilder()
                .name(Text.translatable("config.enderlib.group.main"))
                        .option(swapKey)
                .build()
        );
    }
}
