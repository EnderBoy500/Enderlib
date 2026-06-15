package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface DeathEvent {
    Event<DeathEvent.PlayerDeath> PLAYER_DEATH = EventFactory.createArrayBacked(DeathEvent.PlayerDeath.class, events -> (player, world, damageSource) -> {
        List<DeathEvent.PlayerDeath> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(DeathEvent.PlayerDeath::getPriority));
        for (DeathEvent.PlayerDeath event : sortedEvents) {
            event.die(player, world, damageSource);
        }
    });
    Event<DeathEvent.Death> DEATH = EventFactory.createArrayBacked(DeathEvent.Death.class, events -> (entity, world, damageSource) -> {
        List<DeathEvent.Death> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(DeathEvent.Death::getPriority));
        for (DeathEvent.Death event : sortedEvents) {
            event.die(entity, world, damageSource);
        }
    });
    interface PlayerDeath {
        void die(Player player, Level world, DamageSource damageSource);
        default int getPriority() {
            return 1000;
        }
    }
    interface Death {
        void die(LivingEntity entity, Level world, DamageSource damageSource);
        default int getPriority() {
            return 1000;
        }
    }


}
