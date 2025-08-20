package net.enderboy500.enderlib.helper;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public interface BlockEntityRenderHelper {

    default void renderPedestalItem(BlockEntity entity, ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float rotationDeg, float scale, float yOffset) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            matrices.push();
            matrices.translate(0.5, yOffset, 0.5);
            matrices.scale(scale, scale, scale);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotationDeg));
            itemRenderer.renderItem(stack, ItemDisplayContext.GUI, light, OverlayTexture.DEFAULT_UV, matrices,
                    vertexConsumers, entity.getWorld(), 1);
            matrices.pop();
    }

    default void renderFakeEntity(BlockEntity entity, EntityRenderDispatcher entityRenderDispatcher,EntityType<?> displayedEntity ,float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float rotation, float scale, float xOffset, float yOffset, float zOffset) {
        World world = entity.getWorld();
        EntityType<?> entityType = displayedEntity;
        Entity fakeEntity = entityType.create(world, SpawnReason.EVENT);

        if (fakeEntity == null) return;
        fakeEntity.setBodyYaw(fakeEntity.getYaw());

        matrices.push();
        matrices.translate(xOffset, yOffset, zOffset);
        matrices.scale(scale, scale, scale);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        entityRenderDispatcher.render(fakeEntity, 0,0,0, tickProgress, matrices, vertexConsumers, light);
        matrices.pop();
    }

    default int getLightLevel(World world, BlockPos pos) {
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight, skyLight);
    }
}
