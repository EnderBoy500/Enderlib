package net.enderboy500.enderlib.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public interface EnderLibMisc {
    default DamageSource addNewDamageSource(World world, RegistryKey<DamageType> damageTypeRegistryEntry) {
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(damageTypeRegistryEntry.getValue()).get());
        return damageSource;
    }
    default void randomTeleportation(World world, LivingEntity user, int diameter) {
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
    default boolean isWearingFullSet(PlayerEntity player, TagKey<Item> itemTagKey) {
        if (player.getEquippedStack(EquipmentSlot.HEAD).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.CHEST).isIn(itemTagKey)
                && player.getEquippedStack(EquipmentSlot.LEGS).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.FEET).isIn(itemTagKey)) {
            return true;
        } else {
            return false;
        }
    }
}
