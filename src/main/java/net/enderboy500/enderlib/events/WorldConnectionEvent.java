package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface WorldConnectionEvent {
    Event<WorldConnectionEvent.PlayerJoin> JOIN = EventFactory.createArrayBacked(WorldConnectionEvent.PlayerJoin.class, events -> clientWorld -> {
        List<WorldConnectionEvent.PlayerJoin> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(WorldConnectionEvent.PlayerJoin::getPriority));
        for (WorldConnectionEvent.PlayerJoin event : sortedEvents) {
            event.join(clientWorld);
        }
    });
    Event<WorldConnectionEvent.PlayerLeave> LEAVE = EventFactory.createArrayBacked(WorldConnectionEvent.PlayerLeave.class, events -> serverPlayerEntity -> {
        List<WorldConnectionEvent.PlayerLeave> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(WorldConnectionEvent.PlayerLeave::getPriority));
        for (WorldConnectionEvent.PlayerLeave event : sortedEvents) {
            event.leave(serverPlayerEntity);
        }
    });

    interface PlayerJoin {
        void join(ClientWorld clientWorld) throws IOException;
        default int getPriority() {
            return 1000;
        }
    }
    interface PlayerLeave {
        void leave(ServerPlayerEntity serverPlayerEntity) throws IOException;
        default int getPriority() {
            return 1000;
        }
    }

}
