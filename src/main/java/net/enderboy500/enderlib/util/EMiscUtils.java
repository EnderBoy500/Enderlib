package net.enderboy500.enderlib.util;

import net.enderboy500.enderlib.events.PlaceFireEvent;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.fabric.impl.resource.ResourceLoaderImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EMiscUtils {
    public static void addCustomFire(Block base, Block fireBlock) {
        PlaceFireEvent.BASE_BLOCK.register((blockView, pos) -> {
            if (blockView.getBlockState(pos.below()).is(base)) {
                return fireBlock;
            }
            return null;
        });
    }

    public static void addCustomFire(TagKey<Block> base, Block fireBlock) {
        PlaceFireEvent.BASE_BLOCK.register((blockView, pos) -> {
            if (blockView.getBlockState(pos.below()).is(base)) {
                return fireBlock;
            }
            return null;
        });
    }

    public static void createParticleArea(ParticleOptions particleEffect, Level world, BlockPos pos, RandomSource random, int areaDistance, int areaHight) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int l = 0; l < 14; ++l) {
            mutable.set(i + random.nextInt(-areaDistance, areaDistance), j + random.nextInt(-areaHight,areaHight), k + random.nextInt(-areaDistance, areaDistance));
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.isCollisionShapeFullBlock(world, mutable) && Minecraft.getInstance().level != null) {
                Minecraft.getInstance().level.addParticle(particleEffect, (double) mutable.getX(), (double) mutable.getY(), (double) mutable.getZ() , 0, 0.0, 0.0);
            }
        }
    }

    public void addBuiltInDatapack(Identifier identifier, PackActivationType packActivationType) {
        FabricLoader.getInstance().getModContainer(identifier.getNamespace()).ifPresent(modContainer -> ResourceLoaderImpl.registerBuiltinPack(identifier, "datapacks/" + identifier.getPath(), modContainer, packActivationType));
    }

    public void addBuiltInResourcepack(Identifier identifier, PackActivationType packActivationType) {
        ResourceLoader.registerBuiltinPack(identifier, FabricLoader.getInstance().getModContainer(identifier.getNamespace()).get(), packActivationType);
    }
}
