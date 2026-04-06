package net.enderboy500.enderlib.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

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
        void die(PlayerEntity player, World world, DamageSource damageSource);
        default int getPriority() {
            return 1000;
        }
    }
    interface Death {
        void die(LivingEntity entity, World world, DamageSource damageSource);
        default int getPriority() {
            return 1000;
        }
    }


}
