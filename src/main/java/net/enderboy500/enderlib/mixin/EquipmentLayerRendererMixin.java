package net.enderboy500.enderlib.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.enderboy500.enderlib.item.TogglableEquipmentVisibility;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EquipmentLayerRenderer.class)
public class EquipmentLayerRendererMixin {

    @Inject(method = "renderLayers(Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lnet/minecraft/world/item/ItemStack;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;II)V", at = @At("HEAD"), cancellable = true)
    public <S> void enderlib$render(EquipmentClientInfo.LayerType layerType, ResourceKey<EquipmentAsset> assetKey, Model<? super S> model, S object, ItemStack itemStack, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, int i, int j, CallbackInfo ci) {
        if(itemStack.getItem() instanceof TogglableEquipmentVisibility togglableEquipmentVisibility && togglableEquipmentVisibility.isVisible(itemStack)) {
            ci.cancel();
        }
    }
}
