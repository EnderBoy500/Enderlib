package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface BlockEvents {
    Event<BlockEvents.Place> PLACE = EventFactory.createArrayBacked(BlockEvents.Place.class, events -> (Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) -> {
        List<BlockEvents.Place> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(BlockEvents.Place::getPriority));
        for (BlockEvents.Place event : sortedEvents) {
            event.placeBlock(world, pos, state, placer, itemStack);
        }
    });

    Event<BlockEvents.Break> BREAK = EventFactory.createArrayBacked(BlockEvents.Break.class, events -> (Level world, BlockPos pos, BlockState state, Player player) -> {
        List<BlockEvents.Break> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(BlockEvents.Break::getPriority));
        for (BlockEvents.Break event : sortedEvents) {
            event.breakBlock(world, pos, state, player);
        }
    });

    interface Place {
        void placeBlock(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack);
        default int getPriority() {
            return 1000;
        }
    }

    interface Break {
        void breakBlock(Level world, BlockPos pos, BlockState state, Player player);
        default int getPriority() {
            return 1000;
        }
    }
}
