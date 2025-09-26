package net.enderboy500.enderlib.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EnderLibKeyBinds {
    private static KeyBinding toggleArmorPassiveAbility;
    private static KeyBinding activateArmorAbility;

    public static void initializeKeyBinds() {
        toggleArmorPassiveAbility = new KeyBinding("key.enderlib.toggle_armor_passive_ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.enderlib.enderlib");
        activateArmorAbility = new KeyBinding("key.enderlib.activate_armor_ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "category.enderlib.enderlib");

        KeyBindingHelper.registerKeyBinding(toggleArmorPassiveAbility);
        KeyBindingHelper.registerKeyBinding(activateArmorAbility);
    }

    public static boolean isToggleArmorPassiveAbilityKeyPressed() {
        return toggleArmorPassiveAbility.isPressed();
    }
    public static boolean isActivateArmorAbilityKeyPressed() {
        return activateArmorAbility.isPressed();
    }
}
