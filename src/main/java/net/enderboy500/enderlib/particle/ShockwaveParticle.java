package net.enderboy500.enderlib.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Random;

public class ShockwaveParticle extends ExplosionLargeParticle {
    public ShockwaveParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider, float scale) {
        super(world, x, y, z, d, spriteProvider);
        this.maxAge = 8;
        this.scale = scale;
        this.gravityStrength = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.red = 1;
        this.green = 1;
        this.blue = 1;
        this.alpha = 0.5f;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public float getSize(float tickDelta) {
        float d = (this.age + tickDelta) / (this.maxAge);
        return this.scale * MathHelper.clamp(d, 0, 1);
    }

    @Override
    public void tick() {
        super.tick();
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vec3d vec3d = camera.getPos();
        float f = (float) (MathHelper.lerp(tickDelta, this.lastX, this.x) - vec3d.getX());
        float g = (float) (MathHelper.lerp(tickDelta, this.lastY, this.y) - vec3d.getY());
        float h = (float) (MathHelper.lerp(tickDelta, this.lastZ, this.z) - vec3d.getZ());
        Quaternionf quaternion = camera.getRotation();
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1, -1, 0), new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0), new Vector3f(1, -1, 0)};
        float size = this.getSize(tickDelta);
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = vector3fs[i];
            vector3f.rotate(quaternion);
            vector3f.mul(size);
            vector3f.add(f, g, h);
        }
        int brightness = this.getBrightness(tickDelta);
        this.alpha = (float) MathHelper.lerp((float) this.age / this.getMaxAge(), 0.5, 0);
        this.vertex(vertexConsumer, vector3fs[0], this.getMaxU(), this.getMaxV(), brightness);
        this.vertex(vertexConsumer, vector3fs[1], this.getMaxU(), this.getMinV(), brightness);
        this.vertex(vertexConsumer, vector3fs[2], this.getMinU(), this.getMinV(), brightness);
        this.vertex(vertexConsumer, vector3fs[3], this.getMinU(), this.getMaxV(), brightness);
    }

    private void vertex(VertexConsumer vertexConsumer, Vector3f pos, float u, float v, int light) {
        vertexConsumer.vertex(pos.x(), pos.y(), pos.z()).texture(u, v).color(this.red, this.green, this.blue, this.alpha).light(light);
    }

    @Override
    public int getBrightness(float tint) {
        return 240;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class RandomSize implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public RandomSize(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, new Random().nextFloat(10) + 1);
        }
    }
    public static class SizeOne implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeOne(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 1);
        }
    }
    public static class SizeTwo implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeTwo(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 2);
        }
    }
    public static class SizeThree implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeThree(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 3);
        }
    }
    public static class SizeFour implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeFour(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 4);
        }
    }
    public static class SizeFive implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeFive(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 5);
        }
    }
    public static class SizeSix implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeSix(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 6);
        }
    }
    public static class SizeSeven implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeSeven(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 7);
        }
    }
    public static class SizeEight implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeEight(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 8);
        }
    }
    public static class SizeNine implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeNine(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 9);
        }
    }
    public static class SizeTen implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;
        public SizeTen(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ShockwaveParticle(clientWorld, d, e, f, g, this.spriteProvider, 10);
        }
    }
}
