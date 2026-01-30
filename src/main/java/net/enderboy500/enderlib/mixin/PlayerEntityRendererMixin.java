package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLibComponents;
import net.enderboy500.enderlib.misc.HideName;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {


    @Inject(method = "renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/text/Text;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V",
    at = @At("HEAD"), cancellable = true)
    public void enderlib$renderLabelIfPresent(PlayerEntityRenderState playerEntityRenderState, Text text, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {

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

    @Inject(
            method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Arm;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void skylight$twoHandedPoses(AbstractClientPlayerEntity player, Arm arm, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack stack = player.getStackInArm(arm);
        if (stack.contains(EnderLibComponents.HOLD_WITH_BOTH_HANDS)) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_CHARGE);
        }
        if (stack.contains(EnderLibComponents.POINT_TO_CAMERA)) {
            cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
        }
        if (stack.contains(EnderLibComponents.POINT_LIKE_BOW)) {
            cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
        }
    }

}
