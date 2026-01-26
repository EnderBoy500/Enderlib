package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.*;

public interface ScreenOverlayEvent {
    Event<ScreenOverlayEvent> EVENT = EventFactory.createArrayBacked(ScreenOverlayEvent.class, events -> player -> {
        List<ScreenOverlayEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(ScreenOverlayEvent::getPriority));
        for (ScreenOverlayEvent event : sortedEvents) {
            Identifier overlay = event.getOverlay(player);
            if (overlay != null) {
                return overlay;
            }
        }
        return null;
    });

    default int getPriority() {
        return 1000;
    }

    Identifier getOverlay(PlayerEntity player);
}
