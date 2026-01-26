package net.enderboy500.enderlib.util;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.block.Block;
import net.minecraft.component.ComponentType;
import net.minecraft.component.type.ItemEnchantmentsComponent;
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
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
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

    public static List<Block> getBlocksInTag(TagKey<Block> tagKey) {
        List<Block> blocks = new ArrayList<>();
        for (Block block : getAll(Registries.BLOCK)) {
            if (block.getDefaultState().isIn(tagKey)) {
                blocks.add(block);
            }
        }
        return blocks;
    }

    public static <T> void addComponentToAllItems(ComponentType<T> type, T value) {
        DefaultItemComponentEvents.MODIFY.register(ctx -> ctx.modify(
                getAll(Registries.ITEM),
                (builder, item) -> builder.add(type, value)
        ));
    }

    public static void createSweepAttack(PlayerEntity player, Entity target, ItemStack stack) {
            float f = player.isUsingRiptide() ? player.riptideAttackDamage : (float) player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
            float l = 1.0F + (float) player.getAttributeValue(EntityAttributes.SWEEPING_DAMAGE_RATIO) * f;
            DamageSource damageSource = (DamageSource) Optional.ofNullable(stack.getItem().getDamageSource(player)).orElse(player.getDamageSources().playerAttack(player));
            float h = player.getAttackCooldownProgress(0.5F);
            float g = player.getDamageAgainst(target, f, damageSource) - f;
            boolean bl = h > 0.9F;
            boolean bl2;
            if (player.isSprinting() && bl) {
                player.getWorld().playSound((Entity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, player.getSoundCategory(), 1.0F, 1.0F);
                bl2 = true;
            } else {
                bl2 = false;
            }

            f += stack.getItem().getBonusAttackDamage(target, f, damageSource);
            boolean bl3 = bl && player.fallDistance > (double)0.0F && !player.isOnGround() && !player.isClimbing() && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.BLINDNESS) && !player.hasVehicle() && target instanceof LivingEntity && !player.isSprinting();
            if (bl3) {
                f *= 1.5F;
            }

            float i = f + g;
            boolean bl4 = false;
            if (bl && !bl3 && !bl2 && player.isOnGround()) {
                double d = player.getMovement().horizontalLengthSquared();
                double e = (double)player.getMovementSpeed() * (double)2.5F;
                if (d < MathHelper.square(e)) {
                    bl4 = true;
                }
            }

            if (bl4) {
                for (LivingEntity livingEntity3 : player.getWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand((double) 1.0F, (double) 0.25F, (double) 1.0F))) {
                    if (livingEntity3 != player && livingEntity3 != target && !player.isTeammate(livingEntity3)) {
                        if (livingEntity3 instanceof ArmorStandEntity) {
                            ArmorStandEntity armorStandEntity = (ArmorStandEntity) livingEntity3;
                            if (armorStandEntity.isMarker()) {
                                continue;
                            }
                        }

                        if (player.squaredDistanceTo(livingEntity3) < (double) 9.0F) {
                            float m = player.getDamageAgainst(livingEntity3, l, damageSource) * h;
                            World var22 = player.getWorld();
                            if (var22 instanceof ServerWorld) {
                                ServerWorld serverWorld = (ServerWorld) var22;
                                if (livingEntity3.damage(serverWorld, damageSource, m)) {
                                    livingEntity3.takeKnockback((double) 0.4F, (double) MathHelper.sin(player.getYaw() * ((float) Math.PI / 180F)), (double) (-MathHelper.cos(player.getYaw() * ((float) Math.PI / 180F))));
                                    EnchantmentHelper.onTargetDamaged(serverWorld, livingEntity3, damageSource);
                                }
                            }
                        }
                    }
                }

                player.getWorld().playSound((Entity) null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 1.0F);
                player.spawnSweepAttackParticles();
            }
    }

    public static void spawnSweepAttackParticles(PlayerEntity player, ParticleEffect particleType) {
        double d = (double)(-MathHelper.sin(player.getYaw() * ((float)Math.PI / 180F)));
        double e = (double)MathHelper.cos(player.getYaw() * ((float)Math.PI / 180F));
        if (player.getWorld() instanceof ServerWorld) {
            ((ServerWorld)player.getWorld()).spawnParticles(particleType, player.getX() + d, player.getBodyY((double)0.5F), player.getZ() + e, 0, d, (double)0.0F, e, (double)0.0F);
        }

    }

    public static boolean isWearingFullSet(PlayerEntity player, TagKey<Item> itemTagKey) {
        if (player.getEquippedStack(EquipmentSlot.HEAD).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.CHEST).isIn(itemTagKey)
                && player.getEquippedStack(EquipmentSlot.LEGS).isIn(itemTagKey) && player.getEquippedStack(EquipmentSlot.FEET).isIn(itemTagKey)) {
            return true;
        } else {
            return false;
        }
    }

    public static DamageSource addNewDamageSource(World world, RegistryKey<DamageType> damageTypeRegistryEntry) {
        DamageSource damageSource = new DamageSource(
                world.getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(damageTypeRegistryEntry.getValue()).get());
        return damageSource;
    }
}
