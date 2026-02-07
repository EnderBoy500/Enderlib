package net.enderboy500.enderlib.mixin;

import net.enderboy500.enderlib.EnderLibComponents;
import net.enderboy500.enderlib.client.armor.AdvancedArmorFeatureRenderer;
import net.enderboy500.enderlib.client.armor.AdvancedArmorModel;
import net.enderboy500.enderlib.misc.HideName;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerLikeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EquipmentModelData;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.PlayerLikeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
/*    @Inject(method = "<init>", at = @At("TAIL"))
    private void ender$init(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        addFeature(new AdvancedArmorFeatureRenderer(this, EquipmentModelData.mapToEntityModel(slim ? EntityModelLayers.PLAYER_EQUIPMENT_SLIM : EntityModelLayers.PLAYER_EQUIPMENT, ctx.getEntityModels(), (modelPart) -> new AdvancedArmorModel(modelPart)), ctx.getEquipmentRenderer()));
    }*/

    @Inject(method = "renderLabelIfPresent(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V",
    at = @At("HEAD"), cancellable = true)
    public void enderlib$renderLabelIfPresent(PlayerEntityRenderState playerEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {

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
            method = "getArmPose(Lnet/minecraft/entity/PlayerLikeEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Hand;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void skylight$twoHandedPoses(PlayerLikeEntity player, ItemStack stack, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
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
