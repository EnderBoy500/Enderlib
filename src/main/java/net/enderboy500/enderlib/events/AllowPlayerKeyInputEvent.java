package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface AllowPlayerKeyInputEvent {
    Event<AllowPlayerKeyInputEvent> EVENT = EventFactory.createArrayBacked(AllowPlayerKeyInputEvent.class, events -> player -> {
        List<AllowPlayerKeyInputEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(AllowPlayerKeyInputEvent::getPriority));
        for (AllowPlayerKeyInputEvent event : sortedEvents) {
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
