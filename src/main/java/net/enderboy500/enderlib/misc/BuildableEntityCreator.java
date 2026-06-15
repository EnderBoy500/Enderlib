package net.enderboy500.enderlib.misc;

import net.enderboy500.enderlib.events.BlockEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

public class BuildableEntityCreator {
    public static void create(EntityType entityType, BlockPattern pattern,BlockPos posOffset, Block headBlock) {
        BlockEvents.PLACE.register((world, pos, state, placer, itemStack) -> {
            if (world.getBlockState(pos).is(headBlock)) {
                BlockPattern.BlockPatternMatch result2 = pattern.find(world, pos);
                if (result2 != null) {
                    Entity entity = entityType.create(world, EntitySpawnReason.TRIGGERED);
                    if (entity != null) {
                        spawnEntity(world, result2, entity, result2.getBlock(posOffset.getX(), -posOffset.getY(), posOffset.getZ()).getPos());
                    }
                }
            }
        });
    }
    private static void spawnEntity(Level world, BlockPattern.BlockPatternMatch patternResult, Entity entity, BlockPos pos) {
        breakPatternBlocks(world, patternResult);
        entity.snapTo((double)pos.getX() + (double)0.5F, (double)pos.getY() + 0.05, (double)pos.getZ() + (double)0.5F, 0.0F, 0.0F);
        world.addFreshEntity(entity);

        for(ServerPlayer serverPlayerEntity : world.getEntitiesOfClass(ServerPlayer.class, entity.getBoundingBox().inflate((double)5.0F))) {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, entity);
        }

        updatePatternBlocks(world, patternResult);
    }

    private static void breakPatternBlocks(Level world, BlockPattern.BlockPatternMatch patternResult) {
        for(int i = 0; i < patternResult.getWidth(); ++i) {
            for(int j = 0; j < patternResult.getHeight(); ++j) {
                BlockInWorld cachedBlockPosition = patternResult.getBlock(i, j, 0);
                world.setBlock(cachedBlockPosition.getPos(), Blocks.AIR.defaultBlockState(), 2);
                world.levelEvent(2001, cachedBlockPosition.getPos(), Block.getId(cachedBlockPosition.getState()));
            }
        }

    }

    private static void updatePatternBlocks(Level world, BlockPattern.BlockPatternMatch patternResult) {
        for(int i = 0; i < patternResult.getWidth(); ++i) {
            for(int j = 0; j < patternResult.getHeight(); ++j) {
                BlockInWorld cachedBlockPosition = patternResult.getBlock(i, j, 0);
                world.updateNeighborsAt(cachedBlockPosition.getPos(), Blocks.AIR);
            }
        }

    }
}
