package net.enderboy500.enderlib.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record UUIDComponent(UUID uuid) {
    public static final Codec<UUIDComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(UUIDUtil.AUTHLIB_CODEC.fieldOf("uuid").forGetter(UUIDComponent::uuid)).apply(instance, UUIDComponent::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, UUIDComponent> PACKET_CODEC = StreamCodec.composite(UUIDUtil.STREAM_CODEC.cast(), UUIDComponent::uuid, UUIDComponent::new);
}
