package net.enderboy500.enderlib.mixin;

import com.google.common.collect.BiMap;
import com.mojang.datafixers.util.Pair;
import net.enderboy500.enderlib.EnderLib;
import net.enderboy500.enderlib.item.component.EnderLibComponents;
import net.enderboy500.enderlib.item.InventoryInteraction;
import net.enderboy500.enderlib.util.EnderlibTags;
import net.enderboy500.enderlib.util.skin.ItemSkinRegistry;
import net.enderboy500.enderlib.util.skin.ModifierSkin;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "overrideOtherStackedOnMe", at = @At("HEAD"), cancellable = true)
    public void enderlib$itemSwap(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference, CallbackInfoReturnable<Boolean> cir) {
        if (clickType == ClickAction.SECONDARY && EnderLib.canRightClickToCycle()) {
            if (this instanceof InventoryInteraction slotChangeFunction) {
                slotChangeFunction.onSlotInteraction(stack, player,true);
                cir.setReturnValue(true);
            } else {
                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$smith(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();


        if (player != null && player.isShiftKeyDown() && context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.SMITHING_TABLE) && ItemSkinRegistry.getMap().containsKey(stack.getItem()) && !stack.is(EnderlibTags.SKIN_INCOMPATIBILITY)) {

            if (!stack.has(EnderLibComponents.SKIN_ID)) stack.set(EnderLibComponents.SKIN_ID, 0);

            if (ItemSkinRegistry.getMap().containsKey(stack.getItem()) && stack.has(EnderLibComponents.SKIN_ID)) {
                if (stack.get(EnderLibComponents.SKIN_ID) < ItemSkinRegistry.getMap().get(stack.getItem()).size()) {

                    if (stack.get(EnderLibComponents.SKIN_ID) > 0 && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                        System.out.println("1");
                        modifierSkin.resetDefaults(stack);
                    }
                    if (ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID)) instanceof ModifierSkin modifierSkin) {
                        modifierSkin.modify(stack);
                    }
                    stack.set(DataComponents.ITEM_MODEL, ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID)).getModelId());
                    stack.set(EnderLibComponents.SKIN_ID, stack.get(EnderLibComponents.SKIN_ID) + 1);
                } else {
                    if (stack.get(EnderLibComponents.SKIN_ID) == ItemSkinRegistry.getMap().get(stack.getItem()).size() && ItemSkinRegistry.getMap().get(stack.getItem()).get(stack.get(EnderLibComponents.SKIN_ID) - 1) instanceof ModifierSkin modifierSkin) {
                        modifierSkin.resetDefaults(stack);
                    }
                    stack.set(DataComponents.ITEM_MODEL, stack.getPrototype().get(DataComponents.ITEM_MODEL));
                    stack.set(EnderLibComponents.SKIN_ID, 0);
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$axe(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();
        if (stack.has(EnderLibComponents.CAN_STRIP) && AxeItem.STRIPPABLES.containsKey(level.getBlockState(blockPos).getBlock())) {
            if (playerWantsToBlock(context)) {
                cir.setReturnValue(InteractionResult.PASS);
            } else {
                Optional<BlockState> optional = this.evaluateNewBlockState(level, blockPos, player, level.getBlockState(blockPos));
                if (optional.isEmpty()) {
                    cir.setReturnValue(InteractionResult.PASS);
                } else {
                    ItemStack itemStack = context.getItemInHand();
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
                    }

                    level.setBlock(blockPos, (BlockState)optional.get(), 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, (BlockState)optional.get()));
                    if (player != null) {
                        itemStack.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
                    }

                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$shovel(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        if (useOnContext.getItemInHand().has(EnderLibComponents.CAN_FLATTEN_TO_PATH) && ShovelItem.FLATTENABLES.containsKey(level.getBlockState(blockPos).getBlock())) {
            if (useOnContext.getClickedFace() == Direction.DOWN) {
                cir.setReturnValue(InteractionResult.PASS);
            } else {
                Player player = useOnContext.getPlayer();
                BlockState blockState2 = (BlockState) ShovelItem.FLATTENABLES.get(blockState.getBlock());
                BlockState blockState3 = null;
                if (blockState2 != null && level.getBlockState(blockPos.above()).isAir()) {
                    level.playSound(player, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
                    blockState3 = blockState2;
                } else if (blockState.getBlock() instanceof CampfireBlock && (Boolean) blockState.getValue(CampfireBlock.LIT)) {
                    if (!level.isClientSide()) {
                        level.levelEvent((Entity) null, 1009, blockPos, 0);
                    }

                    CampfireBlock.dowse(useOnContext.getPlayer(), level, blockPos, blockState);
                    blockState3 = (BlockState) blockState.setValue(CampfireBlock.LIT, false);
                }

                if (blockState3 != null) {
                    if (!level.isClientSide()) {
                        level.setBlock(blockPos, blockState3, 11);
                        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(player, blockState3));
                        if (player != null) {
                            useOnContext.getItemInHand().hurtAndBreak(1, player, useOnContext.getHand().asEquipmentSlot());
                        }
                    }

                    cir.setReturnValue(InteractionResult.SUCCESS);
                } else {
                    cir.setReturnValue(InteractionResult.PASS);
                }
            }
        }
    }

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$hoe(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = (Pair)HoeItem.TILLABLES.get(level.getBlockState(blockPos).getBlock());
        if (useOnContext.getItemInHand().has(EnderLibComponents.CAN_TILL) && pair != null) {
            Predicate<UseOnContext> predicate = (Predicate)pair.getFirst();
            Consumer<UseOnContext> consumer = (Consumer)pair.getSecond();
            if (predicate.test(useOnContext)) {
                Player player = useOnContext.getPlayer();
                level.playSound(player, blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (!level.isClientSide()) {
                    consumer.accept(useOnContext);
                    if (player != null) {
                        useOnContext.getItemInHand().hurtAndBreak(1, player, useOnContext.getHand().asEquipmentSlot());
                    }
                }

                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                cir.setReturnValue(InteractionResult.PASS);
            }
        }
    }

    /*@Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void enderlib$shear(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (useOnContext.getItemInHand().has(EnderLibComponents.CAN_SHEAR)) {
            if (block instanceof GrowingPlantHeadBlock growingPlantHeadBlock) {
                if (!growingPlantHeadBlock.isMaxAge(blockState)) {
                    Player player = useOnContext.getPlayer();
                    ItemStack itemStack = useOnContext.getItemInHand();
                    if (player instanceof ServerPlayer) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
                    }

                    level.playSound(player, blockPos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
                    BlockState blockState2 = growingPlantHeadBlock.getMaxAgeState(blockState);
                    level.setBlockAndUpdate(blockPos, blockState2);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(useOnContext.getPlayer(), blockState2));
                    if (player != null) {
                        itemStack.hurtAndBreak(1, player, useOnContext.getHand().asEquipmentSlot());
                    }

                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            } else if (ToolMaps.SHEAR.containsKey(block)) {
                Player playerEntity = useOnContext.getPlayer();
                BlockState blockState1 = ToolMaps.SHEAR.get(blockState.getBlock());
                BlockState blockState2 = null;
                if (blockState1 != null) {
                    level.playSound(playerEntity, blockPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
                    blockState2 = blockState1;
                }
                if (blockState2 != null) {
                    level.setBlock(blockPos, blockState2, 11);
                    level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(playerEntity, blockState2));
                    if (playerEntity != null) {
                        useOnContext.getItemInHand().hurtWithoutBreaking(1, playerEntity);
                    }
                    useOnContext.getItemInHand().useOn(useOnContext).consumesAction();
                }
            } else {
                cir.getReturnValue();
            }
        }
    }*/

    private static boolean playerWantsToBlock(UseOnContext useOnContext) {
        Player player = useOnContext.getPlayer();
        return useOnContext.getHand().equals(InteractionHand.MAIN_HAND) && player.getOffhandItem().has(DataComponents.BLOCKS_ATTACKS) && !player.isSecondaryUseActive();
    }

    private Optional<BlockState> evaluateNewBlockState(Level level, BlockPos blockPos, @Nullable Player player, BlockState blockState) {
        Optional<BlockState> optional = Optional.ofNullable((Block)AxeItem.STRIPPABLES.get(blockState.getBlock())).map((block) -> (BlockState)block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, (Direction.Axis)blockState.getValue(RotatedPillarBlock.AXIS)));;
        if (optional.isPresent()) {
            level.playSound(player, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional2 = WeatheringCopper.getPrevious(blockState);
            if (optional2.isPresent()) {
                spawnSoundAndParticle(level, blockPos, player, blockState, SoundEvents.AXE_SCRAPE, 3005);
                return optional2;
            } else {
                Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap) HoneycombItem.WAX_OFF_BY_BLOCK.get()).get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
                if (optional3.isPresent()) {
                    spawnSoundAndParticle(level, blockPos, player, blockState, SoundEvents.AXE_WAX_OFF, 3004);
                    return optional3;
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    private static void spawnSoundAndParticle(Level level, BlockPos blockPos, @Nullable Player player, BlockState blockState, SoundEvent soundEvent, int i) {
        level.playSound(player, blockPos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
        level.levelEvent(player, i, blockPos, 0);
        if (blockState.getBlock() instanceof ChestBlock && blockState.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
            BlockPos blockPos2 = ChestBlock.getConnectedBlockPos(blockPos, blockState);
            level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos2, GameEvent.Context.of(player, level.getBlockState(blockPos2)));
            level.levelEvent(player, i, blockPos2, 0);
        }

    }
}
