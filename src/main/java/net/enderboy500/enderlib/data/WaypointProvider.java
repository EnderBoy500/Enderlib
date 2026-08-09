package net.enderboy500.enderlib.data;

import com.mojang.serialization.Codec;
import net.minecraft.client.resources.WaypointStyle;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.waypoints.WaypointStyleAsset;
import net.minecraft.world.waypoints.WaypointStyleAssets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class WaypointProvider implements DataProvider {
    private final PackOutput.PathProvider pathProvider;

    public WaypointProvider(PackOutput packOutput) {
        this.pathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "waypoint_style");
    }

    public abstract void generate(Output output);

    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        Map<ResourceKey<WaypointStyleAsset>, WaypointStyle> map = new HashMap();
        generate((resourceKey, waypointStyle) -> {
            if (map.putIfAbsent(resourceKey, waypointStyle) != null) {
                throw new IllegalStateException("Tried to register waypoint style twice for id: " + String.valueOf(resourceKey));
            }
        });
        Codec var10001 = WaypointStyle.CODEC;
        PackOutput.PathProvider var10002 = this.pathProvider;
        Objects.requireNonNull(var10002);
        return DataProvider.saveAll(cachedOutput, var10001, var10002::json, map);
    }

    public static ResourceKey<WaypointStyleAsset> createWaypointId(Identifier identifier) {
        return ResourceKey.create(WaypointStyleAssets.ROOT_ID, identifier);
    }

    public String getName() {
        return "Waypoint Style Definitions";
    }

    public interface Output {
        void accept(ResourceKey<WaypointStyleAsset> resourceKey, WaypointStyle waypointStyle);
        default void addDefault(Identifier identifier) {
            accept(WaypointProvider.createWaypointId(identifier), new WaypointStyle(128, 332, List.of(identifier.withSuffix("_0"), identifier.withSuffix("_1"), identifier.withSuffix("_2"), identifier.withSuffix("_3"))));
        }
        default void addDefaultWithDistance(Identifier identifier, int nearDistance, int farDistance) {
            accept(WaypointProvider.createWaypointId(identifier), new WaypointStyle(nearDistance, farDistance, List.of(identifier.withSuffix("_0"), identifier.withSuffix("_1"), identifier.withSuffix("_2"), identifier.withSuffix("_3"))));
        }
        default void addDefaultWithNearDistance(Identifier identifier, int nearDistance) {
            accept(WaypointProvider.createWaypointId(identifier), new WaypointStyle(nearDistance, 332, List.of(identifier.withSuffix("_0"), identifier.withSuffix("_1"), identifier.withSuffix("_2"), identifier.withSuffix("_3"))));
        }
        default void addDefaultWithFarDistance(Identifier identifier, int farDistance) {
            accept(WaypointProvider.createWaypointId(identifier), new WaypointStyle(128, farDistance, List.of(identifier.withSuffix("_0"), identifier.withSuffix("_1"), identifier.withSuffix("_2"), identifier.withSuffix("_3"))));
        }
    }

}
