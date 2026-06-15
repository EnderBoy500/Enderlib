package net.enderboy500.enderlib.misc;

import net.enderboy500.enderlib.util.interfaces.ScreenShake;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import java.util.List;
import java.util.UUID;

public class ScreenShaker {
    public static void addScreenShake(Player player, float intensity, int duration) {
        if (player instanceof ScreenShake screenShake) {
            screenShake.addScreenShake(intensity, duration);
        }
    }

    public static void addScreenShake(ServerLevel world, BlockPos pos, double radius, float intensity, int duration) {
        List<ServerPlayer> playersInRange = ((ServerLevel) world).getPlayers(p -> p.blockPosition().closerThan(pos, radius));
        for (ServerPlayer serverPlayerEntity : playersInRange) {
            if (serverPlayerEntity instanceof ScreenShake screenShake) screenShake.addScreenShake(intensity, duration);
        }
    }
    public static void addDynamicScreenShake(ServerLevel world, BlockPos pos, double radius, double distanceModifier, float intensity, int duration) {
        List<ServerPlayer> playersInRange = ((ServerLevel) world).getPlayers(p -> p.blockPosition().closerThan(pos, radius));
        for (ServerPlayer serverPlayerEntity : playersInRange) {
            double distance = serverPlayerEntity.blockPosition().distSqr(pos);
            float dynamicIntensity = (float) (intensity  / (Math.sqrt(distance) * distanceModifier));
            if (serverPlayerEntity instanceof ScreenShake screenShake) screenShake.addScreenShake(dynamicIntensity, duration);
        }
    }

    public static void addScreenShake(Level world, UUID uuid, float intensity, int duration) {
        Player player = world.getPlayerByUUID(uuid);
        addScreenShake(player, intensity, duration);
    }
}
