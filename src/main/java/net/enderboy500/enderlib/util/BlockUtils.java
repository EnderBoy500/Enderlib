package net.enderboy500.enderlib.util;

import net.enderboy500.enderlib.events.PlaceFireEvent;
import net.fabricmc.fabric.impl.content.registry.FireBlockHooks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {
    public static List<Block> getBlocksInTag(TagKey<Block> tagKey) {
        List<Block> blocks = new ArrayList<>();
        for (Block block : ItemUtils.getAll(Registries.BLOCK)) {
            if (block.getDefaultState().isIn(tagKey)) {
                blocks.add(block);
            }
        }
        return blocks;
    }

    public static void addCustomFire(Block base, Block fireBlock) {
        PlaceFireEvent.BASE_BLOCK.register((blockView, pos) -> {
            if (blockView.getBlockState(pos.down()).isOf(base)) {
                return fireBlock;
            }
            return null;
        });
    }

    public static void addCustomFire(TagKey<Block> base, Block fireBlock) {
        PlaceFireEvent.BASE_BLOCK.register((blockView, pos) -> {
            if (blockView.getBlockState(pos.down()).isIn(base)) {
                return fireBlock;
            }
            return null;
        });
    }

    public static void createParticleArea(ParticleEffect particleEffect, World world, BlockPos pos, Random random, int areaDistance, int areaHight) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d = (double)i + random.nextDouble();
        double e = (double)j + 1.7;
        double f = (double)k + random.nextDouble();
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(particleEffect, d, e, f, 1, 0, 0, 0, 0);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int l = 0; l < 14; ++l) {
                mutable.set(i + MathHelper.nextInt(random, -areaDistance, areaDistance), j + random.nextInt(areaHight), k + MathHelper.nextInt(random, -areaDistance, areaDistance));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isFullCube(world, mutable)) {
                    serverWorld.spawnParticles(particleEffect, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() + random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0, 0.0, 0.0, 0, 0);
                }
            }
        }
    }
}
