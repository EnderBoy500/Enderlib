package net.enderboy500.enderlib.misc;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public interface CustomDamageSource {
    default DamageSource addNewDamageSource(World world, RegistryKey<DamageType> damageTypeRegistryEntry) {
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(damageTypeRegistryEntry.getValue()).get());
        return damageSource;
    }
}
