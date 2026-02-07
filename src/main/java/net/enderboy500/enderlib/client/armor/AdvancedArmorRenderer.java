package net.enderboy500.enderlib.client.armor;

import net.enderboy500.enderlib.EnderLib;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.equipment.EquipmentModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class AdvancedArmorRenderer implements ArmorRenderer{
    private AdvancedArmorModel<BipedEntityRenderState> model;
    public AdvancedArmorRenderer() {

    }
/*
    private AdvancedArmorModel<BipedEntityRenderState> getModel() {
        if (model == null) {

        }
        return model;
    }*/

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        if (model == null) {
            this.model = new AdvancedArmorModel(contextModel.getRootPart());
        }
            contextModel.copyTransforms(model);
            model.setVisible(false);
            switch (slot) {
                case HEAD -> {
                    model.head.visible = true;
                }
                case CHEST -> {
                    model.body.visible = true;
                    model.right_arm.visible = true;
                    model.left_arm.visible = true;
                }
                case LEGS -> {
                    model.left_leg.visible = true;
                    model.right_leg.visible = true;
                }
            }
            Identifier texture = Identifier.of(EnderLib.MOD_ID,"textures/test-.png");
            VertexConsumer vertexConsumer = MinecraftClient.getInstance().getBufferBuilders().getEffectVertexConsumers().getBuffer(RenderLayer.getArmorCutoutNoCull(texture));
            model.render(matrices, vertexConsumer, light, 0);
    }

}
