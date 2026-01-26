package net.enderboy500.enderlib.misc;

import net.enderboy500.enderlib.client.ScreenShake;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class ScreenShaker {
    public static void addScreenShake(PlayerEntity player, float intensity, int duration) {
        if (player instanceof ScreenShake screenShake) {
            screenShake.addScreenShake(intensity, duration);
        }
    }

    public static void addScreenShake(ServerWorld world, BlockPos pos, double radius, float intensity, int duration) {
        List<ServerPlayerEntity> playersInRange = ((ServerWorld) world).getPlayers(p -> p.getBlockPos().isWithinDistance(pos, radius));
        for (ServerPlayerEntity serverPlayerEntity : playersInRange) {
            if (serverPlayerEntity instanceof ScreenShake screenShake) screenShake.addScreenShake(intensity, duration);
        }
    }
    public static void addDynamicScreenShake(ServerWorld world, BlockPos pos, double radius, double distanceModifier, float intensity, int duration) {
        List<ServerPlayerEntity> playersInRange = ((ServerWorld) world).getPlayers(p -> p.getBlockPos().isWithinDistance(pos, radius));
        for (ServerPlayerEntity serverPlayerEntity : playersInRange) {
            double distance = serverPlayerEntity.getBlockPos().getSquaredDistance(pos);
            float dynamicIntensity = (float) (intensity  / (Math.sqrt(distance) * distanceModifier));
            if (serverPlayerEntity instanceof ScreenShake screenShake) screenShake.addScreenShake(dynamicIntensity, duration);
        }
    }

    public static void addScreenShake(World world, UUID uuid, float intensity, int duration) {
        PlayerEntity player = world.getPlayerByUuid(uuid);
        addScreenShake(player, intensity, duration);
    }
}
