package net.enderboy500.enderlib.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.interfaces.PlayerRenderStateAccessor;
import net.minecraft.client.model.effects.SpinAttackEffectModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpinAttackEffectLayer.class)
public class RiptideFeatureMixin {
    @Shadow @Final private SpinAttackEffectModel model;

    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "HEAD"), cancellable = true)
    public void enderlib$riptide(PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, int i, AvatarRenderState playerEntityRenderState, float f, float g, CallbackInfo ci) {
        if (playerEntityRenderState instanceof PlayerRenderStateAccessor accessor && accessor != null && accessor.player().getWeaponItem().has(EnderLibComponents.CUSTOM_RIPTIDE_TEXTURE) && playerEntityRenderState.isAutoSpinAttack) {
            orderedRenderCommandQueue.submitModel(this.model, playerEntityRenderState, matrixStack, this.model.renderType(accessor.player().getWeaponItem().get(EnderLibComponents.CUSTOM_RIPTIDE_TEXTURE)), i, OverlayTexture.NO_OVERLAY, playerEntityRenderState.outlineColor, (ModelFeatureRenderer.CrumblingOverlay)null);
            ci.cancel();
        }
    }
}
