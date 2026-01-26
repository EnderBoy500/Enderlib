package net.enderboy500.enderlib.test;

import com.mojang.serialization.Codec;
import net.enderboy500.enderlib.EnderLibComponents;
import net.enderboy500.enderlib.helper.BlockCreator;
import net.enderboy500.enderlib.helper.ItemCreator;
import net.enderboy500.enderlib.helper.RegistryHelper;
import net.enderboy500.enderlib.item.AttackStatusEffectComponent;
import net.enderboy500.enderlib.item.UUIDComponent;
import net.enderboy500.enderlib.item.ArmorItemSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.equipment.ArmorMaterials;
import net.minecraft.network.codec.PacketCodecs;

import java.util.List;

public class TestInit {

    public static final Item TEST = RegistryHelper.registerItem("test", TestItem::new, new Item.Settings().component(EnderLibComponents.ATTACK_STATUS_EFFECT, new AttackStatusEffectComponent(List.of(new StatusEffectInstance(StatusEffects.SPEED, 200), new StatusEffectInstance(StatusEffects.POISON, 100)))));
    public static final Item TEST_2 = RegistryHelper.registerItem("test_2", TestItem::new, new Item.Settings());
    public static final ComponentType<UUIDComponent> TEST_ID = RegistryHelper.registerDataComponent("test_pos", builder -> builder.codec(UUIDComponent.CODEC).packetCodec(UUIDComponent.PACKET_CODEC));
    public static final ComponentType<String> TEST_NAME = RegistryHelper.registerDataComponent("test_name", builder -> builder.codec(Codec.STRING).packetCodec(PacketCodecs.STRING));
    public static void init() {}
    public static final Block TEST_BLOCK = BlockCreator.defaultBlock("test_block", Blocks.AMETHYST_BLOCK);
    public static final ArmorItemSet EMERALD = ArmorItemSet.create("emerald", ArmorMaterials.IRON);
}
