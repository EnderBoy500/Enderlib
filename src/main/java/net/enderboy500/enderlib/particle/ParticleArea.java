package net.enderboy500.enderlib.particle;

import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;



public interface ParticleArea {
    default void createFullParticleArea(ParticleEffect particleEffect, World world, BlockPos pos, Random random, int areaDistance, int areaHight) {
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
                    serverWorld.spawnParticles(particleEffect, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() - random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0, 0.0, 0.0, 0, 0);
                }
            }
        }
    }


    default void createAboveParticleArea(ParticleEffect particleEffect, World world, BlockPos pos, Random random, int areaDistance, int areaHight) {
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

    default void createBelowParticleArea(ParticleEffect particleEffect, World world, BlockPos pos, Random random, int areaDistance, int areaHight) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d = (double) i + random.nextDouble();
        double e = (double) j + 1.7;
        double f = (double) k + random.nextDouble();
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(particleEffect, d, e, f, 1, 0, 0, 0, 0);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for (int l = 0; l < 14; ++l) {
                mutable.set(i + MathHelper.nextInt(random, -areaDistance, areaDistance), j + random.nextInt(-areaHight), k + MathHelper.nextInt(random, -areaDistance, areaDistance));
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isFullCube(world, mutable)) {
                    serverWorld.spawnParticles(particleEffect, (double) mutable.getX() + random.nextDouble(), (double) mutable.getY() - random.nextDouble(), (double) mutable.getZ() + random.nextDouble(), 0, 0.0, 0.0, 0, 0);
                }
            }
        }
    }
}
