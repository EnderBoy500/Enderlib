package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.misc.HideName;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(method = "renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
    at = @At("HEAD"), cancellable = true)
    public void renderLabelIfPresent(PlayerEntityRenderState playerEntityRenderState, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (playerEntityRenderState.equippedHeadStack.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.equippedHeadStack)) {
            ci.cancel();
        }
        if (playerEntityRenderState.equippedChestStack.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.equippedChestStack)) {
            ci.cancel();
        }
        if (playerEntityRenderState.equippedLegsStack.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.equippedLegsStack)) {
            ci.cancel();
        }
        if (playerEntityRenderState.equippedFeetStack.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.equippedFeetStack)) {
            ci.cancel();
        }
    }
}
