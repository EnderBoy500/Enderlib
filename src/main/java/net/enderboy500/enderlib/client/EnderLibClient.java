package net.enderboy500.enderlib.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class EnderLibClient implements ClientModInitializer {
    private static KeyBinding cycleHeadwearState;
    private static KeyBinding toggleArmorPassiveAbility;
    private static KeyBinding activateArmorAbility;
    @Override
    public void onInitializeClient() {
        cycleHeadwearState = new KeyBinding("key.enderlib.cycle_headwear_state", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.enderlib.enderlib");
        toggleArmorPassiveAbility = new KeyBinding("key.enderlib.toggle_armor_passive_ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.enderlib.enderlib");
        activateArmorAbility = new KeyBinding("key.enderlib.activate_armor_ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Y, "category.enderlib.enderlib");

        KeyBindingHelper.registerKeyBinding(cycleHeadwearState);
        KeyBindingHelper.registerKeyBinding(toggleArmorPassiveAbility);
        KeyBindingHelper.registerKeyBinding(activateArmorAbility);
    }
    public static boolean isCycleHeadwearStateKeyPressed() {
        return cycleHeadwearState.isPressed();
    }
    public static boolean isToggleArmorPassiveAbilityKeyPressed() {
        return toggleArmorPassiveAbility.isPressed();
    }
    public static boolean isActivateArmorAbilityKeyPressed() {
        return activateArmorAbility.isPressed();
    }
}
