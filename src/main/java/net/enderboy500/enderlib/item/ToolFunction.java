package net.enderboy500.enderlib.item;

import com.mojang.datafixers.util.Pair;
import net.enderboy500.enderlib.misc.ToolMaps;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface ToolFunction {
    default void harvestFunction(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        if (state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMature(state)) {
            world.breakBlock(pos, true);
            world.setBlockState(pos, state.getBlock().getDefaultState());
            stack.damage(1, player);
        }
    }

    default void shearFunction(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity playerEntity = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        BlockState blockState = context.getWorld().getBlockState(pos);
        BlockState blockState1 = ToolMaps.SHEAR.get(blockState.getBlock());
        BlockState blockState2 = null;
        if (blockState1 != null) {
            world.playSound(playerEntity, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            blockState2 = blockState1;
        }
        if (blockState2 != null) {
            world.setBlockState(pos, blockState2, 11);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(playerEntity, blockState2));
            if (playerEntity != null) {
                context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
            }
            context.getStack().useOnBlock(context).isAccepted();
        }
    }

    default void tillFuntion(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = HoeItem.TILLING_ACTIONS.get(
                world.getBlockState(blockPos).getBlock()
        );
        if (pair != null){
            Predicate<ItemUsageContext> predicate = pair.getFirst();
            Consumer<ItemUsageContext> consumer = pair.getSecond();
            if (predicate.test(context)) {
                PlayerEntity playerEntity = context.getPlayer();
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!world.isClient) {
                    consumer.accept(context);
                    if (playerEntity != null) {
                        context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                    }
                }

            }
        }
    }

    default void shovelFuntion(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
         if (context.getSide() != Direction.DOWN){
            PlayerEntity playerEntity = context.getPlayer();
            BlockState blockState2 = (BlockState) ShovelItem.PATH_STATES.get(blockState.getBlock());
            BlockState blockState3 = null;
            if (blockState2 != null && world.getBlockState(blockPos.up()).isAir()) {
                world.playSound(playerEntity, blockPos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                blockState3 = blockState2;
            } else if (blockState.getBlock() instanceof CampfireBlock && (Boolean)blockState.get(CampfireBlock.LIT)) {
                if (!world.isClient()) {
                    world.syncWorldEvent((PlayerEntity)null, 1009, blockPos, 0);
                }

                CampfireBlock.extinguish(context.getPlayer(), world, blockPos, blockState);
                blockState3 = (BlockState)blockState.with(CampfireBlock.LIT, false);
            }

            if (blockState3 != null) {
                if (!world.isClient) {
                    world.setBlockState(blockPos, blockState3, 11);
                    world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(playerEntity, blockState3));
                    if (playerEntity != null) {
                        context.getStack().damage(1, playerEntity, LivingEntity.getSlotForHand(context.getHand()));
                    }
                }
            }
        }
    }

    default boolean toolPriority(ItemUsageContext context, Item item) {
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();
        if (stack.isOf(item) && player != null) {
            if (player.getMainHandStack().isOf(item)) {
                return true;
            } else if (!player.getMainHandStack().isEmpty() && player.getOffHandStack().isOf(item)) {
                return true;
            } else if (player.getMainHandStack().getUseAction() == null && player.getOffHandStack().isOf(item)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    default boolean canTill(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = HoeItem.TILLING_ACTIONS.get(
                world.getBlockState(pos).getBlock()
        );
        return pair != null;
    }

    default boolean checkCrop(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        return world.getBlockState(pos).isIn(BlockTags.CROPS);
    }

}
