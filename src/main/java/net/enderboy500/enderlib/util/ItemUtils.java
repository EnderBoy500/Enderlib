package net.enderboy500.enderlib.util;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.world.item.component.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.core.component.DataComponents.ENCHANTMENTS;

public class ItemUtils {
    public static boolean hasEnchantment(ItemStack stack, String enchantKey) {
        final var enchantments = stack.getOrDefault(ENCHANTMENTS, ItemEnchantments.EMPTY).entrySet();

        for (final var entry : enchantments) {
            String enchant = entry.getKey().getRegisteredName();

            if (enchant.contains(enchantKey)) {
                return true;
            }
        }

        return false;
    }

    public static <T> List<T> getAll(Registry<T> registry) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < registry.size(); i++) {
            list.add(registry.byId(i));
        }

        return list;
    }

    public static List<Item> getItemsInTag(TagKey<Item> tagKey) {
        List<Item> items = new ArrayList<>();
        for (Item item : getAll(BuiltInRegistries.ITEM)) {
            if (item.getDefaultInstance().is(tagKey)) {
                items.add(item);
            }
        }
        return items;
    }

    public static <T> void addComponentToAllItems(DataComponentType<T> type, T value) {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                getAll(BuiltInRegistries.ITEM),
                (builder, item) -> builder.set(type, value)
        ));
    }

    public static boolean isWearingFullSet(Player player, TagKey<Item> itemTagKey) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(itemTagKey) && player.getItemBySlot(EquipmentSlot.CHEST).is(itemTagKey)
                && player.getItemBySlot(EquipmentSlot.LEGS).is(itemTagKey) && player.getItemBySlot(EquipmentSlot.FEET).is(itemTagKey);
    }

    public static boolean isWearingFullArmorSet(Player player, List<Item> list) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(list.get(0).asItem()) && player.getItemBySlot(EquipmentSlot.CHEST).is(list.get(1).asItem())
                && player.getItemBySlot(EquipmentSlot.LEGS).is(list.get(2).asItem()) && player.getItemBySlot(EquipmentSlot.FEET).is(list.get(3).asItem());
    }
    public static boolean isWearingFullSet(Player player, List<TagKey<Item>> list) {
        return player.getItemBySlot(EquipmentSlot.HEAD).is(list.get(0)) && player.getItemBySlot(EquipmentSlot.CHEST).is(list.get(1))
                && player.getItemBySlot(EquipmentSlot.LEGS).is(list.get(2)) && player.getItemBySlot(EquipmentSlot.FEET).is(list.get(3));
    }

    public static void doSweepAttack(Player player, ParticleOptions particleEffect, SoundEvent soundEvent) {
        Entity target = player.getLastHurtMob();
        player.playServerSideSound(soundEvent);
        float f = player.isAutoSpinAttack() ? player.autoSpinAttackDmg : (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        f *= player.baseDamageScaleFactor();
        ItemStack itemStack = player.getWeaponItem();
        float v = player.getAttackStrengthScale(0.5F);
        DamageSource damageSource = player.createAttackSource(itemStack);
        Level var6 = player.level();
        if (var6 instanceof ServerLevel serverWorld) {
            float var12 = 1.0F + (float)player.getAttributeValue(Attributes.SWEEPING_DAMAGE_RATIO) * f;

            for(LivingEntity livingEntity : player.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate((double)1.0F, (double)0.25F, (double)1.0F))) {
                if (livingEntity != player && livingEntity != target && !player.isAlliedTo(livingEntity)) {
                    if (livingEntity instanceof ArmorStand) {
                        ArmorStand armorStandEntity = (ArmorStand)livingEntity;
                        if (armorStandEntity.isMarker()) {
                            continue;
                        }
                    }

                    if (player.distanceToSqr(livingEntity) < (double)9.0F) {
                        float g = player.getEnchantedDamage(livingEntity, var12, damageSource) * v;
                        if (livingEntity.hurtServer(serverWorld, damageSource, g)) {
                            livingEntity.knockback((double)0.4F, (double)Mth.sin((double)(player.getYRot() * ((float)Math.PI / 180F))), (double)(-Mth.cos((double)(player.getYRot() * ((float)Math.PI / 180F)))));
                            EnchantmentHelper.doPostAttackEffects(serverWorld, livingEntity, damageSource);
                        }
                    }
                }
            }

            double d = (double)(-Mth.sin((double)(player.getYRot() * ((float)Math.PI / 180F))));
            double e = (double)Mth.cos((double)(player.getYRot() * ((float)Math.PI / 180F)));
            serverWorld.sendParticles(particleEffect, player.getX() + d, player.getY((double)0.5F), player.getZ() + e, 0, d, (double)0.0F, e, (double)0.0F);
        }
    }

    public static DamageSource addNewDamageSource(Level world, ResourceKey<DamageType> damageTypeRegistryEntry) {
        DamageSource damageSource = new DamageSource(
                world.registryAccess()
                        .lookupOrThrow(Registries.DAMAGE_TYPE)
                        .get(damageTypeRegistryEntry.identifier()).get());
        return damageSource;
    }

    public static void applyShield(List<Item> items, int durability, TagKey<Item> repairIngredients, float blockPercentage) {
        HolderGetter<Item> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                items,
                (builder, item) -> builder.set(DataComponents.MAX_DAMAGE, durability)
                        .set(DataComponents.MAX_STACK_SIZE, 1)
                        .set(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
                        .set(DataComponents.REPAIRABLE, new Repairable(registryEntryLookup.getOrThrow(repairIngredients)))
                        .set(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.OFFHAND).setSwappable(false).build())
                        .set(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(0.25F, 1.0F, List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                        .set(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK))
        );
    }
    public static void applyShield(Item item, int durability, TagKey<Item> repairIngredients, float blockPercentage) {
        HolderGetter<Item> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                List.of(item),
                (builder, item2) -> builder.set(DataComponents.MAX_DAMAGE, durability)
                        .set(DataComponents.MAX_STACK_SIZE, 1)
                        .set(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
                        .set(DataComponents.REPAIRABLE, new Repairable(registryEntryLookup.getOrThrow(repairIngredients)))
                        .set(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.OFFHAND).setSwappable(false).build())
                        .set(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(0.25F, 1.0F, List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(SoundEvents.SHIELD_BLOCK), Optional.of(SoundEvents.SHIELD_BREAK)))
                        .set(DataComponents.BREAK_SOUND, SoundEvents.SHIELD_BREAK))
        );
    }
    public static void applyShield(List<Item> items, int durability, TagKey<Item> repairIngredients, float blockPercentage, Holder.Reference<SoundEvent> blockAttackSound, Holder.Reference<SoundEvent> shieldBreakSound) {
        HolderGetter<Item> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                items,
                (builder, item) -> builder.set(DataComponents.MAX_DAMAGE, durability)
                        .set(DataComponents.MAX_STACK_SIZE, 1)
                        .set(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
                        .set(DataComponents.REPAIRABLE, new Repairable(registryEntryLookup.getOrThrow(repairIngredients)))
                        .set(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.OFFHAND).setSwappable(false).build())
                        .set(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(0.25F, 1.0F, List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(blockAttackSound), Optional.of(shieldBreakSound)))
                        .set(DataComponents.BREAK_SOUND, shieldBreakSound))
        );
    }
    public static void applyShield(Item item, int durability, TagKey<Item> repairIngredients, float blockPercentage, Holder.Reference<SoundEvent> blockAttackSound, Holder.Reference<SoundEvent> shieldBreakSound) {
        HolderGetter<Item> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                List.of(item),
                (builder, item2) -> builder.set(DataComponents.MAX_DAMAGE, durability)
                        .set(DataComponents.MAX_STACK_SIZE, 1)
                        .set(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY)
                        .set(DataComponents.REPAIRABLE, new Repairable(registryEntryLookup.getOrThrow(repairIngredients)))
                        .set(DataComponents.EQUIPPABLE, Equippable.builder(EquipmentSlot.OFFHAND).setSwappable(false).build())
                        .set(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(0.25F, 1.0F, List.of(new BlocksAttacks.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacks.ItemDamageFunction(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(blockAttackSound), Optional.of(shieldBreakSound)))
                        .set(DataComponents.BREAK_SOUND, shieldBreakSound))
        );
    }
}
