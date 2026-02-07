package net.enderboy500.enderlib.client;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.client.armor.AdvancedArmorLeggingsRenderer;
import net.enderboy500.enderlib.client.armor.AdvancedArmorModel;
import net.enderboy500.enderlib.client.armor.AdvancedArmorRenderer;
import net.enderboy500.enderlib.test.TestInit;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;

public class EnderLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EnderLibKeyBinds.initializeKeyBinds();
        EntityModelLayerRegistry.registerModelLayer(EnderLib.ADVANCED_ARMOR, AdvancedArmorModel::getTexturedModelData);
        ArmorRenderer.register(new AdvancedArmorRenderer(), TestInit.POOP,TestInit.PEE,TestInit.PUKE);
        ArmorRenderer.register(new AdvancedArmorLeggingsRenderer(), TestInit.FART);
    }
}
