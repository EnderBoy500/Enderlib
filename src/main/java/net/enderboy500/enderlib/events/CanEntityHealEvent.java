package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface CanEntityHealEvent {
    Event<CanEntityHealEvent> EVENT = EventFactory.createArrayBacked(CanEntityHealEvent.class, events -> player -> {
        List<CanEntityHealEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(CanEntityHealEvent::getPriority));
        for (CanEntityHealEvent event : sortedEvents) {
            boolean b = event.getB(player);
            return b;
        }
        return true;
    });

    default int getPriority() {
        return 1000;
    }

    boolean getB(LivingEntity player);
}
