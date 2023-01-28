package com.ultreon.mods.pixelguns.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class GasParticle extends SpriteBillboardParticle {
    protected GasParticle(ClientWorld level, double xCoord, double yCoord, double zCoord,
                          SpriteProvider spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.velocityMultiplier = 0.6f;
        this.x = xd;
        this.y = yd;
        this.z = zd;
        this.scale *= 100F;
        this.maxAge = 90000;
        this.setSpriteForAge(spriteSet);

        this.red = 1f;
        this.green = 1f;
        this.blue = 1f;
    }

    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }
    public void fadeOut() {
        this.alpha = (-(1/(float)maxAge) * age + 1);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public Factory(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z,
                                       double dx, double dy,  double dz) {
            return new GasParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }
}
