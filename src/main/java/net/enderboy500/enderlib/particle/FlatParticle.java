package net.enderboy500.enderlib.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class FlatParticle extends SingleQuadParticle {

    protected FlatParticle(ClientLevel clientLevel, double d, double e, double f, TextureAtlasSprite textureAtlasSprite) {
        super(clientLevel, d, e, f, textureAtlasSprite);
    }

    @Override
    public FacingCameraMode getFacingCameraMode() {
        return (quaternionf, camera, f) -> {
            quaternionf.rotateX((float) -Math.PI / 2);
            quaternionf.rotateY((float) Math.toRadians(camera.rotation().y));

        };
    }

    @Override
    protected Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final TextureAtlasSprite spriteProvider;

        public Factory(TextureAtlasSprite sprite) {
            this.spriteProvider = sprite;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new FlatParticle(world, x, y, z, spriteProvider);
        }
    }
}