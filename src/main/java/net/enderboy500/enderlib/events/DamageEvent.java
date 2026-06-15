package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface DamageEvent {
    Event<DamageEvent> ON_DAMAGED = EventFactory.createArrayBacked(DamageEvent.class, events -> (entity, serverWorld, source, amount) -> {
        List<DamageEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(DamageEvent::getPriority));
        for (DamageEvent event : sortedEvents) {
            event.damage(entity, serverWorld, source, amount);
        }
    });

    void damage(LivingEntity entity, ServerLevel world, DamageSource source, float amount);
    default int getPriority() {
        return 1000;
    }
}
