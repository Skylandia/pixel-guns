package com.ultreon.mods.pixelguns;

import com.ultreon.mods.pixelguns.particle.ModParticles;
import com.ultreon.mods.pixelguns.particle.custom.GasParticle;
import com.ultreon.mods.pixelguns.registry.*;
import com.ultreon.mods.pixelguns.registry.ModelPredicateRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

@Environment(value = EnvType.CLIENT)
public class PixelGunsClient implements ClientModInitializer {

    public void onInitializeClient() {
        KeybindRegistry.registerKeybinds();

        EntityRegistry.RENDERER.registerEntityRenderers();
        BlockRegistry.registerBlockRenderers();
        ItemRegistry.registerItemRenderers();
        ArmorRegistry.registerArmorRenderers();
        ScreenHandlerRegistry.registerScreenRenderers();

        PacketRegistry.CLIENT.registerPackets();

        ParticleFactoryRegistry.getInstance().register(ModParticles.GAS_PARTICLE, GasParticle.Factory::new);

        ModelPredicateRegistry.registerModelPredicates();
    }
}