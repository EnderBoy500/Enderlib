package net.enderboy500.enderlib.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.feature.EndPlatformFeature;

import java.util.Set;

public class PositionUtils {
    public static void randomTeleportation(World world, LivingEntity user, int diameter) {
        for(int i = 0; i < 16; ++i) {
            double d = user.getX() + (user.getRandom().nextDouble() - (double)0.5F) * (double)diameter;
            double e = MathHelper.clamp(user.getY() + (user.getRandom().nextDouble() - (double)0.5F) * (double)diameter, (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
            double f = user.getZ() + (user.getRandom().nextDouble() - (double)0.5F) * (double)diameter;
            if (user.hasVehicle()) {
                user.stopRiding();
            }

            Vec3d vec3d = user.getPos();
            if (user.teleport(d, e, f, true)) {
                world.emitGameEvent(GameEvent.TELEPORT, vec3d, GameEvent.Emitter.of(user));
                SoundCategory soundCategory;
                SoundEvent soundEvent;

                soundEvent = SoundEvents.ENTITY_PLAYER_TELEPORT;
                soundCategory = SoundCategory.PLAYERS;


                world.playSound((Entity)null, user.getX(), user.getY(), user.getZ(), soundEvent, soundCategory);
                user.onLanding();
                break;
            }
        }
    }

    public TeleportTarget createDimentionalTeleportTarget(ServerWorld world, Entity entity, RegistryKey<World> dimention1, RegistryKey<World> dimention2, BlockPos pos, boolean spawnEndPlatform) {
        RegistryKey<World> registryKey = world.getRegistryKey() == dimention1 ? dimention2 : dimention1;
        ServerWorld serverWorld = world.getServer().getWorld(registryKey);

        if (serverWorld == null) {
            return null;
        } else {
            Vec3d vec3d = new Vec3d(pos.down());
            if (spawnEndPlatform) {
                EndPlatformFeature.generate(serverWorld, BlockPos.ofFloored(vec3d).down(), true);
            }
            boolean bl = serverWorld.getRegistryKey() == dimention1;
            WorldBorder worldBorder = serverWorld.getWorldBorder();
            Set<PositionFlag> set;
            double d = DimensionType.getCoordinateScaleFactor(world.getDimension(), serverWorld.getDimension());
            BlockPos blockPos = worldBorder.clampFloored(entity.getX() * d, entity.getY(), entity.getZ() * d);
            set = PositionFlag.combine(new Set[]{PositionFlag.DELTA, PositionFlag.ROT});
            return new TeleportTarget(serverWorld, new Vec3d(pos), Vec3d.ZERO, 0, 0.0F, set, TeleportTarget.SEND_TRAVEL_THROUGH_PORTAL_PACKET.then(TeleportTarget.ADD_PORTAL_CHUNK_TICKET));
        }
    }

    public static void applyVelocityInLookDirection(LivingEntity living, float multiplier, boolean inverted) {
        living.setVelocity(
                living.getRotationVector().x * (inverted ? -multiplier : multiplier),
                living.getRotationVector().y * (inverted ? -multiplier : multiplier),
                living.getRotationVector().z * (inverted ? -multiplier : multiplier)
        );
        living.velocityModified = true;
    }

    public static void applyVelocityByPos(LivingEntity target, BlockPos pos, float multiplier, boolean inverted) {
        target.setVelocity(target.getPos().subtract(new Vec3d(pos)).multiply(inverted ? -multiplier : multiplier));
        target.velocityModified = true;
    }
}
