package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface PlaceFireEvent {
    Event<PlaceFireEvent.BaseBlock> BASE_BLOCK = EventFactory.createArrayBacked(PlaceFireEvent.BaseBlock.class, events -> (blockView, pos) -> {
        List<PlaceFireEvent.BaseBlock> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(PlaceFireEvent.BaseBlock::getPriority));
        for (PlaceFireEvent.BaseBlock event : sortedEvents) {
            return event.baseBlock(blockView, pos);
        }
        return null;
    });

    interface BaseBlock {
        Block baseBlock(BlockView blockView, BlockPos pos);
        default int getPriority() {
            return 1000;
        }
    }
}
