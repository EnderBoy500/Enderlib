package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.interfaces.PlayerRenderStateAccessor;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.render.entity.model.TridentRiptideEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentRiptideFeatureRenderer.class)
public class RiptideFeatureMixin {
    @Shadow @Final private TridentRiptideEntityModel model;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V", at = @At(value = "HEAD"), cancellable = true)
    public void enderlib$riptide(MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, int i, PlayerEntityRenderState playerEntityRenderState, float f, float g, CallbackInfo ci) {
        if (playerEntityRenderState instanceof PlayerRenderStateAccessor accessor && accessor.player().getWeaponStack().contains(EnderLibComponents.CUSTOM_RIPTIDE_TEXTURE) && playerEntityRenderState.usingRiptide) {
            orderedRenderCommandQueue.submitModel(this.model, playerEntityRenderState, matrixStack, this.model.getLayer(accessor.player().getWeaponStack().get(EnderLibComponents.CUSTOM_RIPTIDE_TEXTURE)), i, OverlayTexture.DEFAULT_UV, playerEntityRenderState.outlineColor, (ModelCommandRenderer.CrumblingOverlayCommand)null);
            ci.cancel();
        }
    }
}
