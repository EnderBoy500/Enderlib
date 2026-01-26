package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface CanPlayerModifyWorldEvent {
    Event<CanPlayerModifyWorldEvent> EVENT = EventFactory.createArrayBacked(CanPlayerModifyWorldEvent.class, events -> player -> {
        List<CanPlayerModifyWorldEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(CanPlayerModifyWorldEvent::getPriority));
        for (CanPlayerModifyWorldEvent event : sortedEvents) {
            boolean b = event.getB(player);
            return b;
        }
        return true;
    });

    default int getPriority() {
        return 1000;
    }

    boolean getB(PlayerEntity player);
}
