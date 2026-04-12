package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface BlockEvents {
    Event<BlockEvents.Place> PLACE = EventFactory.createArrayBacked(BlockEvents.Place.class, events -> (World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) -> {
        List<BlockEvents.Place> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(BlockEvents.Place::getPriority));
        for (BlockEvents.Place event : sortedEvents) {
            event.placeBlock(world, pos, state, placer, itemStack);
        }
    });

    Event<BlockEvents.Break> BREAK = EventFactory.createArrayBacked(BlockEvents.Break.class, events -> (World world, BlockPos pos, BlockState state, PlayerEntity player) -> {
        List<BlockEvents.Break> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(BlockEvents.Break::getPriority));
        for (BlockEvents.Break event : sortedEvents) {
            event.breakBlock(world, pos, state, player);
        }
    });

    interface Place {
        void placeBlock(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack);
        default int getPriority() {
            return 1000;
        }
    }

    interface Break {
        void breakBlock(World world, BlockPos pos, BlockState state, PlayerEntity player);
        default int getPriority() {
            return 1000;
        }
    }
}
