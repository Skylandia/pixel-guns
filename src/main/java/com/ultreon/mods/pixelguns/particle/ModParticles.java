package com.ultreon.mods.pixelguns.particle;

import com.ultreon.mods.pixelguns.PixelGuns;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType GAS_PARTICLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(PixelGuns.MOD_ID, "gas_particle"),
                GAS_PARTICLE);
    }
}