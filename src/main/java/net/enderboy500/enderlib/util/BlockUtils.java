package net.enderboy500.enderlib.util;

import net.enderboy500.enderlib.events.PlaceFireEvent;
import net.fabricmc.fabric.impl.content.registry.FireBlockHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    public static List<Block> getBlocksInTag(TagKey<Block> tagKey) {
        List<Block> blocks = new ArrayList<>();
        for (Block block : ItemUtils.getAll(BuiltInRegistries.BLOCK)) {
            if (block.defaultBlockState().is(tagKey)) {
                blocks.add(block);
            }
        }
        return blocks;
    }

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
        double d = (double)i + random.nextDouble();
        double e = (double)j + 1.7;
        double f = (double)k + random.nextDouble();
        if (world instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(particleEffect, d, e, f, 1, 0, 0, 0, 0);
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for (int l = 0; l < 14; ++l) {
                mutable.set(i + Mth.nextInt(random, -areaDistance, areaDistance), j + random.nextInt(areaHight), k + Mth.nextInt(random, -areaDistance, areaDistance));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isCollisionShapeFullBlock(world, mutable)) {
                    serverWorld.sendParticles(particleEffect, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0, 0.0, 0.0, 0, 0);
                }
            }
        }
    }
}
