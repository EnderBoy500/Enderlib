package net.enderboy500.enderlib.misc;

import net.enderboy500.enderlib.events.BlockEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BuildableEntityCreator {
    public static void create(EntityType entityType, BlockPattern pattern,BlockPos posOffset, Block headBlock) {
        BlockEvents.PLACE.register((world, pos, state, placer, itemStack) -> {
            if (world.getBlockState(pos).isOf(headBlock)) {
                BlockPattern.Result result2 = pattern.searchAround(world, pos);
                if (result2 != null) {
                    Entity entity = entityType.create(world, SpawnReason.TRIGGERED);
                    if (entity != null) {
                        spawnEntity(world, result2, entity, result2.translate(posOffset.getX(), -posOffset.getY(), posOffset.getZ()).getBlockPos());
                    }
                }
            }
        });
    }
    private static void spawnEntity(World world, BlockPattern.Result patternResult, Entity entity, BlockPos pos) {
        breakPatternBlocks(world, patternResult);
        entity.refreshPositionAndAngles((double)pos.getX() + (double)0.5F, (double)pos.getY() + 0.05, (double)pos.getZ() + (double)0.5F, 0.0F, 0.0F);
        world.spawnEntity(entity);

        for(ServerPlayerEntity serverPlayerEntity : world.getNonSpectatingEntities(ServerPlayerEntity.class, entity.getBoundingBox().expand((double)5.0F))) {
            Criteria.SUMMONED_ENTITY.trigger(serverPlayerEntity, entity);
        }

        updatePatternBlocks(world, patternResult);
    }

    private static void breakPatternBlocks(World world, BlockPattern.Result patternResult) {
        for(int i = 0; i < patternResult.getWidth(); ++i) {
            for(int j = 0; j < patternResult.getHeight(); ++j) {
                CachedBlockPosition cachedBlockPosition = patternResult.translate(i, j, 0);
                world.setBlockState(cachedBlockPosition.getBlockPos(), Blocks.AIR.getDefaultState(), 2);
                world.syncWorldEvent(2001, cachedBlockPosition.getBlockPos(), Block.getRawIdFromState(cachedBlockPosition.getBlockState()));
            }
        }

    }

    private static void updatePatternBlocks(World world, BlockPattern.Result patternResult) {
        for(int i = 0; i < patternResult.getWidth(); ++i) {
            for(int j = 0; j < patternResult.getHeight(); ++j) {
                CachedBlockPosition cachedBlockPosition = patternResult.translate(i, j, 0);
                world.updateNeighbors(cachedBlockPosition.getBlockPos(), Blocks.AIR);
            }
        }

    }
}
