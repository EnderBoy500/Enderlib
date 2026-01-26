package net.enderboy500.enderlib.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public record BlockPositionComponent(Vec3d position) {
    public static final Codec<BlockPositionComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Vec3d.CODEC.fieldOf("position").forGetter(BlockPositionComponent::position)).apply(instance, BlockPositionComponent::new));
    public static final PacketCodec<RegistryByteBuf, BlockPositionComponent> PACKET_CODEC = PacketCodec.tuple(Vec3d.PACKET_CODEC.cast(), BlockPositionComponent::position, BlockPositionComponent::new);

    public BlockPositionComponent(BlockPos position) {
        this(new Vec3d(position));
    }

    @Override
    public Vec3d position() {
        return position;
    }
}
