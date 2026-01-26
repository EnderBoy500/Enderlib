package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface BedInteractionEvent {
    Event<BedInteractionEvent.Sleep> SLEEP = EventFactory.createArrayBacked(BedInteractionEvent.Sleep.class, events -> (player, pos) -> {
        List<BedInteractionEvent.Sleep> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(BedInteractionEvent.Sleep::getPriority));
        for (BedInteractionEvent.Sleep event : sortedEvents) {
            event.sleep(player, pos);
        }
    });
    Event<BedInteractionEvent.TrySleep> TRY_SLEEP = EventFactory.createArrayBacked(BedInteractionEvent.TrySleep.class, events -> (player, pos) -> {
        List<BedInteractionEvent.TrySleep> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(BedInteractionEvent.TrySleep::getPriority));
        for (BedInteractionEvent.TrySleep event : sortedEvents) {
            event.sleep(player, pos);
        }
    });

    Event<BedInteractionEvent.WakeUp> WAKE_UP = EventFactory.createArrayBacked(BedInteractionEvent.WakeUp.class, events -> (player) -> {
        List<BedInteractionEvent.WakeUp> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(BedInteractionEvent.WakeUp::getPriority));
        for (BedInteractionEvent.WakeUp event : sortedEvents) {
            event.sleep(player);
        }
    });
    interface Sleep {
        void sleep(PlayerEntity player, BlockPos pos);
        default int getPriority() {
            return 1000;
        }
    }
    interface TrySleep {
        void sleep(PlayerEntity player, BlockPos pos);
        default int getPriority() {
            return 1000;
        }
    }
    interface WakeUp {
        void sleep(PlayerEntity player);
        default int getPriority() {
            return 1000;
        }
    }
}
