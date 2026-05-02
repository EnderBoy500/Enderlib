package net.enderboy500.enderlib.util;

import com.twelvemonkeys.imageio.metadata.iptc.IPTC;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.component.DataComponentTypes.ENCHANTMENTS;

public class ItemUtils {
    public static boolean hasEnchantment(ItemStack stack, String enchantKey) {
        final var enchantments = stack.getOrDefault(ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT).getEnchantmentEntries();

        for (final var entry : enchantments) {
            String enchant = entry.getKey().getIdAsString();

            if (enchant.contains(enchantKey)) {
                return true;
            }
        }

        return false;
    }

    public static <T> List<T> getAll(Registry<T> registry) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < registry.size(); i++) {
            list.add(registry.get(i));
        }

        return list;
    }

    public static List<Item> getItemsInTag(TagKey<Item> tagKey) {
        List<Item> items = new ArrayList<>();
        for (Item item : getAll(Registries.ITEM)) {
            if (item.getDefaultStack().isIn(tagKey)) {
                items.add(item);
            }
        }
        return items;
    }

    public static <T> void addComponentToAllItems(ComponentType<T> type, T value) {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                getAll(Registries.ITEM),
                (builder, item) -> builder.add(type, value)
        ));
    }

    public static boolean isWearingFullSet(PlayerEntity player, TagKey<Item> itemTagKey) {
        return player.getEquippedStack(EquipmentSlot.HEAD).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.CHEST).isIn(itemTagKey)
                && player.getEquippedStack(EquipmentSlot.LEGS).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.FEET).isIn(itemTagKey);
    }

    public static boolean isWearingFullArmorSet(PlayerEntity player, List<Item> list) {
        return player.getEquippedStack(EquipmentSlot.HEAD).isOf(list.get(0).asItem()) && player.getEquippedStack(EquipmentSlot.CHEST).isOf(list.get(1).asItem())
                && player.getEquippedStack(EquipmentSlot.LEGS).isOf(list.get(2).asItem()) && player.getEquippedStack(EquipmentSlot.FEET).isOf(list.get(3).asItem());
    }
    public static boolean isWearingFullSet(PlayerEntity player, List<TagKey<Item>> list) {
        return player.getEquippedStack(EquipmentSlot.HEAD).isIn(list.get(0)) && player.getEquippedStack(EquipmentSlot.CHEST).isIn(list.get(1))
                && player.getEquippedStack(EquipmentSlot.LEGS).isIn(list.get(2)) && player.getEquippedStack(EquipmentSlot.FEET).isIn(list.get(3));
    }

    public static void doSweepAttack(PlayerEntity player, ParticleEffect particleEffect, SoundEvent soundEvent) {
        Entity target = player.getAttacking();
        player.playAttackSound(soundEvent);
        float f = player.isUsingRiptide() ? player.riptideAttackDamage : (float)player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
        f *= player.getAttackCooldownDamageModifier();
        ItemStack itemStack = player.getWeaponStack();
        float v = player.getAttackCooldownProgress(0.5F);
        DamageSource damageSource = player.getDamageSource(itemStack);
        World var6 = player.getEntityWorld();
        if (var6 instanceof ServerWorld serverWorld) {
            float var12 = 1.0F + (float)player.getAttributeValue(EntityAttributes.SWEEPING_DAMAGE_RATIO) * f;

            for(LivingEntity livingEntity : player.getEntityWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand((double)1.0F, (double)0.25F, (double)1.0F))) {
                if (livingEntity != player && livingEntity != target && !player.isTeammate(livingEntity)) {
                    if (livingEntity instanceof ArmorStandEntity) {
                        ArmorStandEntity armorStandEntity = (ArmorStandEntity)livingEntity;
                        if (armorStandEntity.isMarker()) {
                            continue;
                        }
                    }

                    if (player.squaredDistanceTo(livingEntity) < (double)9.0F) {
                        float g = player.getDamageAgainst(livingEntity, var12, damageSource) * v;
                        if (livingEntity.damage(serverWorld, damageSource, g)) {
                            livingEntity.takeKnockback((double)0.4F, (double)MathHelper.sin((double)(player.getYaw() * ((float)Math.PI / 180F))), (double)(-MathHelper.cos((double)(player.getYaw() * ((float)Math.PI / 180F)))));
                            EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity, damageSource);
                        }
                    }
                }
            }

            double d = (double)(-MathHelper.sin((double)(player.getYaw() * ((float)Math.PI / 180F))));
            double e = (double)MathHelper.cos((double)(player.getYaw() * ((float)Math.PI / 180F)));
            serverWorld.spawnParticles(particleEffect, player.getX() + d, player.getBodyY((double)0.5F), player.getZ() + e, 0, d, (double)0.0F, e, (double)0.0F);
        }
    }

    public static DamageSource addNewDamageSource(World world, RegistryKey<DamageType> damageTypeRegistryEntry) {
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(damageTypeRegistryEntry.getValue()).get());
        return damageSource;
    }

    public static void applyShield(List<Item> items, int durability, TagKey<Item> repairIngredients, float blockPercentage) {
        RegistryEntryLookup<Item> registryEntryLookup = Registries.createEntryLookup(Registries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                items,
                (builder, item) -> builder.add(DataComponentTypes.MAX_DAMAGE, durability)
                        .add(DataComponentTypes.MAX_STACK_SIZE, 1)
                        .add(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT)
                        .add(DataComponentTypes.REPAIRABLE, new RepairableComponent(registryEntryLookup.getOrThrow(repairIngredients)))
                        .add(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.OFFHAND).swappable(false).build())
                        .add(DataComponentTypes.BLOCKS_ATTACKS, new BlocksAttacksComponent(0.25F, 1.0F, List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(SoundEvents.ITEM_SHIELD_BLOCK), Optional.of(SoundEvents.ITEM_SHIELD_BREAK)))
                        .add(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK))
        );
    }
    public static void applyShield(Item item, int durability, TagKey<Item> repairIngredients, float blockPercentage) {
        RegistryEntryLookup<Item> registryEntryLookup = Registries.createEntryLookup(Registries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                List.of(item),
                (builder, item2) -> builder.add(DataComponentTypes.MAX_DAMAGE, durability)
                        .add(DataComponentTypes.MAX_STACK_SIZE, 1)
                        .add(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT)
                        .add(DataComponentTypes.REPAIRABLE, new RepairableComponent(registryEntryLookup.getOrThrow(repairIngredients)))
                        .add(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.OFFHAND).swappable(false).build())
                        .add(DataComponentTypes.BLOCKS_ATTACKS, new BlocksAttacksComponent(0.25F, 1.0F, List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(SoundEvents.ITEM_SHIELD_BLOCK), Optional.of(SoundEvents.ITEM_SHIELD_BREAK)))
                        .add(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK))
        );
    }
    public static void applyShield(List<Item> items, int durability, TagKey<Item> repairIngredients, float blockPercentage, RegistryEntry.Reference<SoundEvent> blockAttackSound, RegistryEntry.Reference<SoundEvent> shieldBreakSound) {
        RegistryEntryLookup<Item> registryEntryLookup = Registries.createEntryLookup(Registries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                items,
                (builder, item) -> builder.add(DataComponentTypes.MAX_DAMAGE, durability)
                        .add(DataComponentTypes.MAX_STACK_SIZE, 1)
                        .add(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT)
                        .add(DataComponentTypes.REPAIRABLE, new RepairableComponent(registryEntryLookup.getOrThrow(repairIngredients)))
                        .add(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.OFFHAND).swappable(false).build())
                        .add(DataComponentTypes.BLOCKS_ATTACKS, new BlocksAttacksComponent(0.25F, 1.0F, List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(blockAttackSound), Optional.of(shieldBreakSound)))
                        .add(DataComponentTypes.BREAK_SOUND, shieldBreakSound))
        );
    }
    public static void applyShield(Item item, int durability, TagKey<Item> repairIngredients, float blockPercentage, RegistryEntry.Reference<SoundEvent> blockAttackSound, RegistryEntry.Reference<SoundEvent> shieldBreakSound) {
        RegistryEntryLookup<Item> registryEntryLookup = Registries.createEntryLookup(Registries.ITEM);
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                List.of(item),
                (builder, item2) -> builder.add(DataComponentTypes.MAX_DAMAGE, durability)
                        .add(DataComponentTypes.MAX_STACK_SIZE, 1)
                        .add(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT)
                        .add(DataComponentTypes.REPAIRABLE, new RepairableComponent(registryEntryLookup.getOrThrow(repairIngredients)))
                        .add(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.OFFHAND).swappable(false).build())
                        .add(DataComponentTypes.BLOCKS_ATTACKS, new BlocksAttacksComponent(0.25F, 1.0F, List.of(new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, blockPercentage)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD), Optional.of(blockAttackSound), Optional.of(shieldBreakSound)))
                        .add(DataComponentTypes.BREAK_SOUND, shieldBreakSound))
        );
    }
}
