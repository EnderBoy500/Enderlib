package net.enderboy500.enderlib.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;

public record BlockPositionComponent(Vec3 position) {
    public static final Codec<BlockPositionComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(Vec3.CODEC.fieldOf("position").forGetter(BlockPositionComponent::position)).apply(instance, BlockPositionComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlockPositionComponent> PACKET_CODEC = StreamCodec.composite(Vec3.STREAM_CODEC.cast(), BlockPositionComponent::position, BlockPositionComponent::new);

    public BlockPositionComponent(BlockPos position) {
        this(new Vec3(position));
    }

    @Override
    public Vec3 position() {
        return position;
    }
}
