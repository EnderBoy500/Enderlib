package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface OverrideHungerSpriteEvent {
    Event<OverrideHungerSpriteEvent> EVENT = EventFactory.createArrayBacked(OverrideHungerSpriteEvent.class, events -> player -> {
        List<OverrideHungerSpriteEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(OverrideHungerSpriteEvent::getPriority));
        for (OverrideHungerSpriteEvent event : sortedEvents) {
            Identifier hunger = event.getHunger(player);
            if (hunger != null) {
                return hunger;
            }
        }
        return null;
    });

    default int getPriority() {
        return 1000;
    }

    Identifier getHunger(PlayerEntity player);
}
