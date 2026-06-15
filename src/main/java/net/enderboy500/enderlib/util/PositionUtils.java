package net.enderboy500.enderlib.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class PositionUtils {
    public static void applyVelocityInLookDirection(LivingEntity living, float multiplier, boolean inverted) {
        living.setDeltaMovement(
                living.getLookAngle().x * (inverted ? -multiplier : multiplier),
                living.getLookAngle().y * (inverted ? -multiplier : multiplier),
                living.getLookAngle().z * (inverted ? -multiplier : multiplier)
        );
        living.needsSync = true;
    }

    public static void applyVelocityByPos(LivingEntity target, BlockPos pos, float multiplier, boolean inverted) {
        target.setDeltaMovement(target.position().subtract(new Vec3(pos)).scale(inverted ? -multiplier : multiplier));
        target.needsSync = true;
    }
}
