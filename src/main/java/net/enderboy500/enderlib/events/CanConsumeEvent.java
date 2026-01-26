package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface CanConsumeEvent {
    Event<CanConsumeEvent> EVENT = EventFactory.createArrayBacked(CanConsumeEvent.class, events -> player -> {
        List<CanConsumeEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(CanConsumeEvent::getPriority));
        for (CanConsumeEvent event : sortedEvents) {
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
