package net.enderboy500.enderlib.particle;

import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Random;

public class ShockwaveParticle extends HugeExplosionParticle {
    public ShockwaveParticle(ClientLevel world, double x, double y, double z, double d, SpriteSet spriteProvider, float scale) {
        super(world, x, y, z, d, spriteProvider);
        this.lifetime = 8;
        this.quadSize = scale;
        this.gravity = 0;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.rCol = 1;
        this.gCol = 1;
        this.bCol = 1;
        this.alpha = 0.5f;
        this.setSpriteFromAge(spriteProvider);
    }

    @Override
    public float getQuadSize(float tickDelta) {
        float d = (this.age + tickDelta) / (this.lifetime);
        return this.quadSize * Mth.clamp(d, 0, 1);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3 vec3d = camera.position();
        float f = (float) (Mth.lerp(tickDelta, this.xo, this.x) - vec3d.x());
        float g = (float) (Mth.lerp(tickDelta, this.yo, this.y) - vec3d.y());
        float h = (float) (Mth.lerp(tickDelta, this.zo, this.z) - vec3d.z());
        Quaternionf quaternion = camera.rotation();
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1, -1, 0), new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0)};
        float size = this.getQuadSize(tickDelta);
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = vector3fs[i];
            vector3f.rotate(quaternion);
            vector3f.mul(size);
            vector3f.add(f, g, h);
        }
        int brightness = this.getLightColor(tickDelta);
        this.alpha = (float) Mth.lerp((float) this.age / this.getLifetime(), 0.5, 0);
        this.vertex(vertexConsumer, vector3fs[0], this.getU1(), this.getV1(), brightness);
        this.vertex(vertexConsumer, vector3fs[1], this.getU1(), this.getV0(), brightness);
        this.vertex(vertexConsumer, vector3fs[2], this.getU0(), this.getV0(), brightness);
        this.vertex(vertexConsumer, vector3fs[3], this.getU0(), this.getV1(), brightness);
    }

    private void vertex(VertexConsumer vertexConsumer, Vector3f pos, float u, float v, int light) {
        vertexConsumer.addVertex(pos.x(), pos.y(), pos.z()).setUv(u, v).setColor(this.rCol, this.gCol, this.bCol, this.alpha).setLight(light);
    }

    @Override
    public int getLightColor(float tint) {
        return 240;
    }

    @Override
    public Layer getLayer() {
        return Layer.TRANSLUCENT;
    }

    public static class RandomSize implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public RandomSize(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, new Random().nextFloat(10) + 1);
        }
    }
    public static class SizeOne implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeOne(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 1);
        }
    }
    public static class SizeTwo implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeTwo(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 2);
        }
    }
    public static class SizeThree implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeThree(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 3);
        }
    }
    public static class SizeFour implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeFour(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 4);
        }
    }
    public static class SizeFive implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeFive(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 5);
        }
    }
    public static class SizeSix implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeSix(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 6);
        }
    }
    public static class SizeSeven implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeSeven(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 7);
        }
    }
    public static class SizeEight implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeEight(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 8);
        }
    }
    public static class SizeNine implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeNine(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 9);
        }
    }
    public static class SizeTen implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;
        public SizeTen(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i, net.minecraft.util.RandomSource random) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 10);
        }
    }
}
