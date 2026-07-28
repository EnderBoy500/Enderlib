package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface DisablePlayerKeyInputEvent {
    Event<DisablePlayerKeyInputEvent> EVENT = EventFactory.createArrayBacked(DisablePlayerKeyInputEvent.class, events -> player -> {
        List<DisablePlayerKeyInputEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(DisablePlayerKeyInputEvent::getPriority));
        for (DisablePlayerKeyInputEvent event : sortedEvents) {
            boolean b = event.getB(player);
            return b;
        }
        return false;
    });

    default int getPriority() {
        return 1000;
    }

    boolean getB(LivingEntity player);
}
