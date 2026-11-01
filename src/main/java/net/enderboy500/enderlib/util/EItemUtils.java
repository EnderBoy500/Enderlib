package net.enderboy500.enderlib.util;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.impl.client.indigo.renderer.helper.ColorHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.ColorArgument;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.attribute.modifier.ColorModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlocksAttacks;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.w3c.dom.css.RGBColor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static net.minecraft.core.component.DataComponents.ENCHANTMENTS;

public class EItemUtils {
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

    public static <T> boolean checkComponent(ItemStack stack, DataComponentType<T> dataComponentType, T value ) {
        return stack.has(dataComponentType) && stack.get(dataComponentType) == value;
    }

    public static boolean isWearingFullArmorSet(Player player, TagKey<Item> armorTag) {
        List<EquipmentSlot> equipmentSlotsValue = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        int i = 0;
        for (EquipmentSlot equipmentSlot : equipmentSlotsValue) {

            if (player.getItemBySlot(equipmentSlot).is(armorTag)) i++;
        }
        return i == 4;
    }
    public static boolean isWearingFullArmorSet(Player player, List<Item> items) {
        List<EquipmentSlot> equipmentSlotsValue = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
        int i = 0;
        for (EquipmentSlot equipmentSlot : equipmentSlotsValue) {

            if (items.contains(player.getItemBySlot(equipmentSlot).getItem())) i++;
        }
        return i == 4;
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

    public static void modifyItemNameColor(Item item, int nameColor) {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                Predicate.isEqual(item),
                (builder, item1) -> {
                    MutableComponent text = Component.translatable(item1.getDescriptionId());
                    builder.set(DataComponents.ITEM_NAME, builder.getOrDefault(DataComponents.ITEM_NAME, text).copy().withColor(nameColor));
                }
        ));
    }

    public static void modifyItemName(Item item, ChatFormatting... chatFormatting) {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                Predicate.isEqual(item),
                (builder, item1) -> {
                    MutableComponent text = Component.translatable(item1.getDescriptionId());
                    builder.set(DataComponents.ITEM_NAME, builder.getOrDefault(DataComponents.ITEM_NAME, text).copy().withStyle(chatFormatting));
                }
        ));
    }

    public static void enchantItem(Level level, ItemStack stack, String enchantKey, int enchantLevel) {
        List<Holder.Reference<Enchantment>> enchantments = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).listElements().toList();

        for (Holder.Reference<Enchantment> enchant : enchantments) {
            if (enchant.key().equals(ResourceKey.create(Registries.ENCHANTMENT, Identifier.parse(enchantKey)))) {
                stack.enchant(enchant, enchantLevel);
            }
        }
    }

    public static void enchantItem(Level level, ItemStack stack, String enchantKey) {
        enchantItem(level, stack, enchantKey, 1);
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
