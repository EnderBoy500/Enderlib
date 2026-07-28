package net.enderboy500.enderlib.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.util.interfaces.HideName;
import net.enderboy500.enderlib.util.interfaces.PlayerRenderStateAccessor;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin<AvatarlikeEntity extends Avatar & ClientAvatarEntity> extends LivingEntityRenderer<AvatarlikeEntity, AvatarRenderState, PlayerModel> {

    public AvatarRendererMixin(EntityRendererProvider.Context context, PlayerModel entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "submitNameTag(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
    at = @At("HEAD"), cancellable = true)
    public void enderlib$renderLabelIfPresent(AvatarRenderState playerEntityRenderState, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, CameraRenderState cameraRenderState, CallbackInfo ci) {

        if (playerEntityRenderState.headEquipment.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.headEquipment)) {
            ci.cancel();
        }
        if (playerEntityRenderState.chestEquipment.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.chestEquipment)) {
            ci.cancel();
        }
        if (playerEntityRenderState.legsEquipment.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.legsEquipment)) {
            ci.cancel();
        }
        if (playerEntityRenderState.feetEquipment.getItem() instanceof HideName hideName && hideName.hideName(playerEntityRenderState.feetEquipment)) {
            ci.cancel();
        }
    }

    @Inject(
            method = "getArmPose(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void twoHandedPoses(Avatar player, ItemStack stack, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (stack.has(EnderLibComponents.HOLD_WITH_BOTH_HANDS)) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_CHARGE);
        }
        if (stack.has(EnderLibComponents.POINT_TO_CAMERA)) {
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
        if (stack.has(EnderLibComponents.POINT_LIKE_BOW)) {
            cir.setReturnValue(HumanoidModel.ArmPose.BOW_AND_ARROW);
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V", at = @At("HEAD"))
    public void update(AvatarlikeEntity playerLikeEntity, AvatarRenderState playerEntityRenderState, float f, CallbackInfo ci) {
        if (playerEntityRenderState instanceof PlayerRenderStateAccessor stateAccessor && stateAccessor != null) {
            stateAccessor.setPlayer(playerLikeEntity);
        }
    }
}
