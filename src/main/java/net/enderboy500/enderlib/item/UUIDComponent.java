package net.enderboy500.enderlib.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.UUID;

public record UUIDComponent(UUID uuid) {
    public static final Codec<UUIDComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Uuids.CODEC.fieldOf("uuid").forGetter(UUIDComponent::uuid)).apply(instance, UUIDComponent::new));
    public static final PacketCodec<RegistryByteBuf, UUIDComponent> PACKET_CODEC = PacketCodec.tuple(Uuids.PACKET_CODEC.cast(), UUIDComponent::uuid, UUIDComponent::new);

}
