package net.enderboy500.enderlib.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PositionUtils {
    public static void applyVelocityInLookDirection(LivingEntity living, float multiplier, boolean inverted) {
        living.setVelocity(
                living.getRotationVector().x * (inverted ? -multiplier : multiplier),
                living.getRotationVector().y * (inverted ? -multiplier : multiplier),
                living.getRotationVector().z * (inverted ? -multiplier : multiplier)
        );
        living.velocityDirty = true;
    }

    public static void applyVelocityByPos(LivingEntity target, BlockPos pos, float multiplier, boolean inverted) {
        target.setVelocity(target.getEntityPos().subtract(new Vec3d(pos)).multiply(inverted ? -multiplier : multiplier));
        target.velocityDirty = true;
    }
}
