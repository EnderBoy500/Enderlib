package net.enderboy500.enderlib.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class AreaSmokeParticle extends SingleQuadParticle {

    AreaSmokeParticle(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, TextureAtlasSprite sprite) {
        super(world, x, y, z, sprite);
        this.scale(3.0F);
        this.setSize(0.25F, 0.25F);
        this.gravity = 3.0E-6F;
        this.xd = velocityX;
        this.yd = velocityY + (double)(this.random.nextFloat() / 500.0F);
        this.zd = velocityZ;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime && !(this.alpha <= 0.0F)) {
            this.xd += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.zd += (double)(this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1));
            this.yd -= (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.age >= this.lifetime - 60 && this.alpha > 0.01F) {
                this.alpha -= 0.015F;
            }

        } else {
            this.remove();
        }
    }

    public ParticleRenderType getType() {
        return ParticleRenderType.SINGLE_QUADS;
    }

    public float getQuadSize(float tickProgress) {
        return this.quadSize * Mth.clamp(((float) this.age + tickProgress) / (float) this.lifetime * 32.0F, 0.0F, 1.0F);
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
            AreaSmokeParticle smokeParticle = new AreaSmokeParticle(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
            return smokeParticle;
        }
    }
}