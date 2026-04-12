package net.enderboy500.enderlib.events;

import net.enderboy500.enderlib.util.skin.ItemSkin;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface SkinModifyEvent {
    Event<SkinModifyEvent> EVENT = EventFactory.createArrayBacked(SkinModifyEvent.class, events -> (skin,item, player) -> {
        List<SkinModifyEvent> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(SkinModifyEvent::getPriority));
        for (SkinModifyEvent event : sortedEvents) {
            event.modify(skin, item, player);
        }
    });

    void modify(ItemSkin skin, Item item, PlayerEntity player);
    default int getPriority() {
        return 1000;
    }
}
