package net.enderboy500.enderlib.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow protected abstract void renderPlayerArm(PoseStack matrices, SubmitNodeCollector queue, int light, float equipProgress, float swingProgress, HumanoidArm arm);

    @Inject(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z",
                    ordinal = 0
            )
    )
    public void renderFirstPersonItem(AbstractClientPlayer player, float tickProgress, float pitch, InteractionHand hand, float swingProgress, ItemStack item, float equipProgress, PoseStack matrices, SubmitNodeCollector orderedRenderCommandQueue, int light, CallbackInfo ci) {
        if (item.has(EnderLibComponents.SHOW_HAND)) {
            boolean bl = hand == InteractionHand.MAIN_HAND;
            HumanoidArm arm = bl ? player.getMainArm() : player.getMainArm().getOpposite();
            matrices.pushPose();
            this.renderPlayerArm(matrices, orderedRenderCommandQueue, light, equipProgress, swingProgress, arm);
            matrices.popPose();
        }
    }
}
