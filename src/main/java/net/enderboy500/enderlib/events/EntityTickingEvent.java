package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface EntityTickingEvent {
    Event<EntityTickingEvent.PreTicking> PRE_TICKING = EventFactory.createArrayBacked(EntityTickingEvent.PreTicking.class, events -> (entity, world) -> {
        List<EntityTickingEvent.PreTicking> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(EntityTickingEvent.PreTicking::getPriority));
        for (EntityTickingEvent.PreTicking event : sortedEvents) {
            event.ticking(entity, world);
        }
    });
    Event<EntityTickingEvent.PostTicking> POST_TICKING = EventFactory.createArrayBacked(EntityTickingEvent.PostTicking.class, events -> (entity, world) -> {
        List<EntityTickingEvent.PostTicking> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(EntityTickingEvent.PostTicking::getPriority));
        for (EntityTickingEvent.PostTicking event : sortedEvents) {
            event.ticking(entity, world);
        }
    });

    interface PreTicking {
        void ticking(Entity entity, World world);
        default int getPriority() {
            return 1000;
        }
    }
    interface PostTicking {
        void ticking(Entity entity, World world);
        default int getPriority() {
            return 1000;
        }
    }
}
