package net.enderboy500.enderlib.events;

import net.enderboy500.enderlib.misc.CustomHeart;
import net.enderboy500.enderlib.misc.CustomHunger;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public interface HudEvents {
    Event<HudEvents.Heart> HEART = EventFactory.createArrayBacked(HudEvents.Heart.class, events -> player -> {
        List<HudEvents.Heart> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(HudEvents.Heart::getPriority));
        for (HudEvents.Heart event : sortedEvents) {
            CustomHeart heart = event.getHeart(player);
            if (heart != null) {
                return heart;
            }
        }
        return null;
    });

    Event<HudEvents.Hunger> HUNGER = EventFactory.createArrayBacked(HudEvents.Hunger.class, events -> player -> {
        List<HudEvents.Hunger> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(HudEvents.Hunger::getPriority));
        for (HudEvents.Hunger event : sortedEvents) {
            CustomHunger hunger = event.getHunger(player);
            if (hunger != null) {
                return hunger;
            }
        }
        return null;
    });

    Event<HudEvents.ScreenOverlay> SCREEN_OVERLAY = EventFactory.createArrayBacked(HudEvents.ScreenOverlay.class, events -> player -> {
        List<HudEvents.ScreenOverlay> sortedEvents = new ArrayList<>(Arrays.asList(events));
        sortedEvents.sort(Comparator.comparingInt(HudEvents.ScreenOverlay::getPriority));
        for (HudEvents.ScreenOverlay event : sortedEvents) {
            Identifier overlay = event.getOverlay(player);
            if (overlay != null) {
                return overlay;
            }
        }
        return null;
    });
    
    interface Heart {
        CustomHeart getHeart(Player player);
        default int getPriority() {
            return 1000;
        }
    }
    
    interface Hunger {
        CustomHunger getHunger(Player player);
        default int getPriority() {
            return 1000;
        }
    }
    
    interface ScreenOverlay {
        Identifier getOverlay(Player player);
        default int getPriority() {
            return 1000;
        }
    }
}
