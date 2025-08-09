package net.enderboy500.enderlib.item;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ToolFunctionItem extends Item {

    public ToolFunctionItem(Settings settings) {
        super(settings);
    }

    public static final Map<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>> TILLING_ACTIONS = Maps.<Block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>>newHashMap(
            ImmutableMap.of(
                    Blocks.GRASS_BLOCK,
                    Pair.of(ToolFunctionItem::canTillFarmland, createTillAction(Blocks.FARMLAND.getDefaultState())),
                    Blocks.DIRT_PATH,
                    Pair.of(ToolFunctionItem::canTillFarmland, createTillAction(Blocks.FARMLAND.getDefaultState())),
                    Blocks.DIRT,
                    Pair.of(ToolFunctionItem::canTillFarmland, createTillAction(Blocks.FARMLAND.getDefaultState())),
                    Blocks.COARSE_DIRT,
                    Pair.of(ToolFunctionItem::canTillFarmland, createTillAction(Blocks.DIRT.getDefaultState())),
                    Blocks.ROOTED_DIRT,
                    Pair.of(itemUsageContext -> true, createTillAndDropAction(Blocks.DIRT.getDefaultState(), Items.HANGING_ROOTS))
            )
    );

    public static Consumer<ItemUsageContext> createTillAction(BlockState result) {
        return context -> {
            context.getWorld().setBlockState(context.getBlockPos(), result, Block.NOTIFY_ALL_AND_REDRAW);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), result));
        };
    }

    /**
     * {@return a tilling action that sets a block state and drops an item}
     *
     * @param droppedItem the item to drop
     * @param result the tilled block state
     */
    public static Consumer<ItemUsageContext> createTillAndDropAction(BlockState result, ItemConvertible droppedItem) {
        return context -> {
            context.getWorld().setBlockState(context.getBlockPos(), result, Block.NOTIFY_ALL_AND_REDRAW);
            context.getWorld().emitGameEvent(GameEvent.BLOCK_CHANGE, context.getBlockPos(), GameEvent.Emitter.of(context.getPlayer(), result));
            Block.dropStack(context.getWorld(), context.getBlockPos(), context.getSide(), new ItemStack(droppedItem));
        };
    }

    /**
     * {@return whether the used block can be tilled into farmland}
     * This method is used as the tilling predicate for most vanilla blocks except rooted dirt.
     */
    public static boolean canTillFarmland(ItemUsageContext context) {
        return context.getSide() != Direction.DOWN && context.getWorld().getBlockState(context.getBlockPos().up()).isAir();
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        if (dirtTillFunction(player)) {
            Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair = (Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>>) TILLING_ACTIONS.get(
                    world.getBlockState(blockPos).getBlock()
            );

            if (pair == null) {
                return ActionResult.PASS;
            } else {
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

                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.PASS;
                }
            }
        }
        if (pathFunction(player)) {
            BlockState blockState = world.getBlockState(blockPos);
            if (context.getSide() == Direction.DOWN) {
                return ActionResult.PASS;
            } else {
                PlayerEntity playerEntity = context.getPlayer();
                BlockState blockState2 = (BlockState)PATH_STATES.get(blockState.getBlock());
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

                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.PASS;
                }
            }
        }

        return super.useOnBlock(context);
    }

    public static final Map<Block, BlockState> PATH_STATES;



    static {
        PATH_STATES = Maps.newHashMap((new ImmutableMap.Builder()).put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.DIRT, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.PODZOL, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.MYCELIUM, Blocks.DIRT_PATH.getDefaultState()).put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.getDefaultState()).build());
    }

    public boolean dirtTillFunction(PlayerEntity player) {
        return this.dirtTillFunction(player);
    }

    public boolean pathFunction(PlayerEntity player) {
        return this.pathFunction(player);
    }

}
