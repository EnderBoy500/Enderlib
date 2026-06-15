package net.enderboy500.enderlib.item;

import com.mojang.datafixers.util.Pair;
import net.enderboy500.enderlib.util.interfaces.ToolMaps;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface ToolFunction {
    default void harvestFunction(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        if (state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)) {
            world.destroyBlock(pos, true);
            world.setBlockAndUpdate(pos, state.getBlock().defaultBlockState());
            stack.hurtWithoutBreaking(1, player);
        }
    }

    default void shearFunction(UseOnContext context) {
        Level world = context.getLevel();
        Player playerEntity = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = context.getLevel().getBlockState(pos);
        BlockState blockState1 = ToolMaps.SHEAR.get(blockState.getBlock());
        BlockState blockState2 = null;
        if (blockState1 != null) {
            world.playSound(playerEntity, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            blockState2 = blockState1;
        }
        if (blockState2 != null) {
            world.setBlock(pos, blockState2, 11);
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(playerEntity, blockState2));
            if (playerEntity != null) {
                context.getItemInHand().hurtWithoutBreaking(1, playerEntity);
            }
            context.getItemInHand().useOn(context).consumesAction();
        }
    }

    default void tillFuntion(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = HoeItem.TILLABLES.get(
                world.getBlockState(blockPos).getBlock()
        );
        if (pair != null){
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(context)) {
                Player playerEntity = context.getPlayer();
                world.playSound(playerEntity, blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!world.isClientSide()) {
                    consumer.accept(context);
                    if (playerEntity != null) {
                        context.getItemInHand().hurtWithoutBreaking(1, playerEntity);
                    }
                }

            }
        }
    }

    default void shovelFuntion(UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
         if (context.getClickedFace() != Direction.DOWN){
            Player playerEntity = context.getPlayer();
            BlockState blockState2 = (BlockState) ShovelItem.FLATTENABLES.get(blockState.getBlock());
            BlockState blockState3 = null;
            if (blockState2 != null && world.getBlockState(blockPos.above()).isAir()) {
                world.playSound(playerEntity, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                blockState3 = blockState2;
            } else if (blockState.getBlock() instanceof CampfireBlock && (Boolean)blockState.getValue(CampfireBlock.LIT)) {
                if (!world.isClientSide()) {
                    world.levelEvent((Player)null, 1009, blockPos, 0);
                }

                CampfireBlock.dowse(context.getPlayer(), world, blockPos, blockState);
                blockState3 = (BlockState)blockState.setValue(CampfireBlock.LIT, false);
            }

            if (blockState3 != null) {
                if (!world.isClientSide()) {
                    world.setBlock(blockPos, blockState3, 11);
                    world.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(playerEntity, blockState3));
                    if (playerEntity != null) {
                        context.getItemInHand().hurtWithoutBreaking(1, playerEntity);
                    }
                }
            }
        }
    }

    default boolean toolPriority(UseOnContext context, Item item) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        if (stack.is(item) && player != null) {
            if (player.getMainHandItem().is(item)) {
                return true;
            } else if (!player.getMainHandItem().isEmpty() && player.getOffhandItem().is(item)) {
                return true;
            } else if (player.getMainHandItem().getUseAnimation() == null && player.getOffhandItem().is(item)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    default boolean canTill(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = HoeItem.TILLABLES.get(
                world.getBlockState(pos).getBlock()
        );
        return pair != null;
    }

    default boolean checkCrop(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return world.getBlockState(pos).is(BlockTags.CROPS);
    }

}
