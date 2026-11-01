package net.enderboy500.enderlib.data;

import net.enderboy500.enderlib.util.EnderlibTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.effect.MobEffects;

import java.util.concurrent.CompletableFuture;

public class EnderlibMobEffectTagProvider extends MobEffectTagProvider{
    public EnderlibMobEffectTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(EnderlibTags.MobEffectTags.DAMAGE_DEALING)
                .add(MobEffects.POISON.value())
                .add(MobEffects.INSTANT_DAMAGE.value())
                .add(MobEffects.WITHER.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.MOTION_MODIFYING)
                .addTag(EnderlibTags.MobEffectTags.MOTION_BOOST)
                .addTag(EnderlibTags.MobEffectTags.SLOWNESS)
                .add(MobEffects.LEVITATION.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.WATER_EFFECTS)
                .add(MobEffects.WATER_BREATHING.value())
                .add(MobEffects.DOLPHINS_GRACE.value())
                .add(MobEffects.BREATH_OF_THE_NAUTILUS.value())
                .add(MobEffects.CONDUIT_POWER.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.HEALING_EFFECTS)
                .add(MobEffects.REGENERATION.value())
                .add(MobEffects.ABSORPTION.value())
                .add(MobEffects.INSTANT_HEALTH.value())
                .add(MobEffects.CONDUIT_POWER.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.OMEN)
                .add(MobEffects.BAD_OMEN.value())
                .add(MobEffects.RAID_OMEN.value())
                .add(MobEffects.TRIAL_OMEN.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.OVERLAYING_EFFECTS)
                .add(MobEffects.NAUSEA.value())
                .add(MobEffects.BLINDNESS.value())
                .add(MobEffects.DARKNESS.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.HUNGER)
                .add(MobEffects.HUNGER.value())
                .add(MobEffects.SATURATION.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.MOTION_BOOST)
                .add(MobEffects.SPEED.value())
                .add(MobEffects.JUMP_BOOST.value())
                .add(MobEffects.DOLPHINS_GRACE.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.SLOWNESS)
                .add(MobEffects.SLOWNESS.value())
                .add(MobEffects.SLOW_FALLING.value())
        ;
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.MINING)
                .add(MobEffects.HASTE.value())
                .add(MobEffects.MINING_FATIGUE.value())
                .add(MobEffects.CONDUIT_POWER.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.ENTITY_MARKERS)
                .add(MobEffects.GLOWING.value())
                .add(MobEffects.INVISIBILITY.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.RESISTANCE)
                .add(MobEffects.RESISTANCE.value())
                .add(MobEffects.FIRE_RESISTANCE.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.SUBMERGED_BREATHING)
                .add(MobEffects.WATER_BREATHING.value())
                .add(MobEffects.CONDUIT_POWER.value())
                .add(MobEffects.BREATH_OF_THE_NAUTILUS.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.INSTANT)
                .add(MobEffects.INSTANT_HEALTH.value())
                .add(MobEffects.INSTANT_DAMAGE.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.STRENGTH)
                .add(MobEffects.STRENGTH.value())
                .add(MobEffects.WEAKNESS.value())
        ;
        valueLookupBuilder(EnderlibTags.MobEffectTags.VISION_MODIFIERS)
                .add(MobEffects.NIGHT_VISION.value())
                .add(MobEffects.BLINDNESS.value())
                .add(MobEffects.DARKNESS.value())
        ;
    }
}
