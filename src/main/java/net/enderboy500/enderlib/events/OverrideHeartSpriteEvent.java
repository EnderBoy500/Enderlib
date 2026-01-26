package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface OverrideHeartSpriteEvent {
    Event<OverrideHeartSpriteEvent> EVENT = EventFactory.createArrayBacked(OverrideHeartSpriteEvent.class, events -> player -> {
        List<OverrideHeartSpriteEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(OverrideHeartSpriteEvent::getPriority));
        for (OverrideHeartSpriteEvent event : sortedEvents) {
            Identifier heart = event.getHeart(player);
            if (heart != null) {
                return heart;
            }
        }
        return null;
    });

    default int getPriority() {
        return 1000;
    }

    Identifier getHeart(PlayerEntity player);
}
