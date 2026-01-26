package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface DeathEvent {
    Event<DeathEvent> DEATH = EventFactory.createArrayBacked(DeathEvent.class, events -> player -> {
        List<DeathEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(DeathEvent::getPriority));
        for (DeathEvent event : sortedEvents) {
            event.die(player);
        }
    });
    void die(PlayerEntity player);
    default int getPriority() {
            return 1000;
        }
}
