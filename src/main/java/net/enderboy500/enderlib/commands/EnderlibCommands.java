package net.enderboy500.enderlib.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.skin.ItemSkinRegistry;
import net.enderboy500.enderlib.util.skin.ModifierSkin;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import java.util.Objects;

public class EnderlibCommands {
    public static void loadCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("enderlib")
                            .then(Commands.argument("enderlib_suggestions", StringArgumentType.string())
                                    .suggests(new EnderLibCommandSuggestionProvider())
                                    .executes(context -> {
                    String typ = StringArgumentType.getString(context, "enderlib_suggestions");

                    if (Objects.equals(typ, "resetItemSkin")) {
                        ServerPlayer serverPlayerEntity = context.getSource().getPlayer();
                        ItemStack stack = serverPlayerEntity.getMainHandItem();
                        if (ItemSkinRegistry.getMap().containsKey(stack.getItem()) && stack.has(EnderLibComponents.SKIN_ID)) {
                            if (stack.get(EnderLibComponents.SKIN_ID) == ItemSkinRegistry.getMap().get(stack.getItem()).size() && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                                modifierSkin.resetDefaults(stack);
                            }
                            stack.set(DataComponents.ITEM_MODEL, stack.getPrototype().get(DataComponents.ITEM_MODEL));
                            stack.set(EnderLibComponents.SKIN_ID, 0);
                            context.getSource().sendSuccess(() -> Component.literal("Successfully reset the skin of " + stack.getItemName().getString()), false);
                        }
                    } else {
                        context.getSource().sendSuccess(() -> Component.literal("Invalid command").withStyle(ChatFormatting.RED), false);
                    }
                    return 1;
            })));
        });
    }
}
