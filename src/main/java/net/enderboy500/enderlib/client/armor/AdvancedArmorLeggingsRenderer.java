package net.enderboy500.enderlib.client.armor;

import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.test.TestInit;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class AdvancedArmorLeggingsRenderer implements ArmorRenderer {
    private AdvancedArmorLeggingsModel model;
    public AdvancedArmorLeggingsRenderer() {

    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue orderedRenderCommandQueue, ItemStack stack, BipedEntityRenderState bipedEntityRenderState, EquipmentSlot slot, int light, BipedEntityModel<BipedEntityRenderState> contextModel) {
        if (model == null) {
            this.model = new AdvancedArmorLeggingsModel(contextModel.getRootPart());
        }
        contextModel.copyTransforms(model);
        model.setVisible(false);
        if (EquipmentSlot.LEGS.isArmorSlot()) {
            model.leftLeg.visible = true;
            model.rightLeg.visible = true;
        }
        Identifier texture = Identifier.of(EnderLib.MOD_ID,"textures/test-.png");
        VertexConsumer vertexConsumer = MinecraftClient.getInstance().getBufferBuilders().getEffectVertexConsumers().getBuffer(RenderLayer.getArmorCutoutNoCull(texture));
        model.render(matrices, vertexConsumer, light, 0);
    }
}
