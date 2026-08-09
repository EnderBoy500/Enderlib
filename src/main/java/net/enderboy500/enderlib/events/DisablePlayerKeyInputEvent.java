package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface DisablePlayerKeyInputEvent {
    Event<DisablePlayerKeyInputEvent.MovementKeys> MOVEMENT_KEYS = EventFactory.createArrayBacked(DisablePlayerKeyInputEvent.MovementKeys.class, events -> player -> {
        List<DisablePlayerKeyInputEvent.MovementKeys> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(MovementKeys::getPriority));
        for (DisablePlayerKeyInputEvent.MovementKeys event : sortedEvents) {
            boolean b = event.getB(player);
            return b;
        }
        return false;
    });
    Event<DisablePlayerKeyInputEvent.AllKeys> ALL_KEYS = EventFactory.createArrayBacked(DisablePlayerKeyInputEvent.AllKeys.class, events -> player -> {
        List<DisablePlayerKeyInputEvent.AllKeys> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(AllKeys::getPriority));
        for (DisablePlayerKeyInputEvent.AllKeys event : sortedEvents) {
            boolean b = event.getB(player);
            return b;
        }
        return false;
    });
    Event<DisablePlayerKeyInputEvent.MouseKeys> MOUSE_KEYS = EventFactory.createArrayBacked(DisablePlayerKeyInputEvent.MouseKeys.class, events -> player -> {
        List<DisablePlayerKeyInputEvent.MouseKeys> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(MouseKeys::getPriority));
        for (DisablePlayerKeyInputEvent.MouseKeys event : sortedEvents) {
            boolean b = event.getB(player);
            return b;
        }
        return false;
    });
    Event<DisablePlayerKeyInputEvent.Scroll> SCROLL = EventFactory.createArrayBacked(DisablePlayerKeyInputEvent.Scroll.class, events -> player -> {
        List<DisablePlayerKeyInputEvent.Scroll> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(Scroll::getPriority));
        for (DisablePlayerKeyInputEvent.Scroll event : sortedEvents) {
            boolean b = event.getB(player);
            return b;
        }
        return false;
    });

    interface MovementKeys {
        default int getPriority() {
            return 1000;
        }
        boolean getB(LivingEntity player);
    }
    interface AllKeys {
        default int getPriority() {
            return 1000;
        }
        boolean getB(LivingEntity player);
    }
    interface MouseKeys {
        default int getPriority() {
            return 1000;
        }
        boolean getB(LivingEntity player);
    }
    interface Scroll {
        default int getPriority() {
            return 1000;
        }
        boolean getB(LivingEntity player);
    }
}
