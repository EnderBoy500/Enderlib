package net.enderboy500.enderlib.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.skin.ItemSkinRegistry;
import net.enderboy500.enderlib.util.skin.ModifierSkin;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Objects;

public class EnderlibCommands {
    public static void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("enderlib")
                            .then(CommandManager.argument("enderlib_suggestions", StringArgumentType.string())
                                    .suggests(new EnderLibCommandSuggestionProvider())
                                    .executes(context -> {
                    String typ = StringArgumentType.getString(context, "enderlib_suggestions");

                    if (Objects.equals(typ, "resetItemSkin")) {
                        ServerPlayerEntity serverPlayerEntity = context.getSource().getPlayer();
                        ItemStack stack = serverPlayerEntity.getMainHandStack();
                        if (ItemSkinRegistry.getMap().containsKey(stack.getItem()) && stack.contains(EnderLibComponents.SKIN_ID)) {
                            if (stack.get(EnderLibComponents.SKIN_ID) == ItemSkinRegistry.getMap().get(stack.getItem()).size() && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                                modifierSkin.resetDefaults(stack);
                            }
                            stack.set(DataComponentTypes.ITEM_MODEL, stack.getDefaultComponents().get(DataComponentTypes.ITEM_MODEL));
                            stack.set(EnderLibComponents.SKIN_ID, 0);
                            context.getSource().sendFeedback(() -> Text.literal("Successfully reset the skin of " + stack.getItemName().getString()), false);
                        }
                    } else {
                        context.getSource().sendFeedback(() -> Text.literal("Invalid command").formatted(Formatting.RED), false);
                    }
                    return 1;
            })));
        });
    }
}
