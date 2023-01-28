package com.ultreon.mods.pixelguns;

import com.ultreon.mods.pixelguns.particle.ModParticles;
import com.ultreon.mods.pixelguns.registry.*;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PixelGuns implements ModInitializer {
    public static final String MOD_ID = "pixel_guns";
    public static final Logger LOGGER = LoggerFactory.getLogger("pixel_guns");

    public void onInitialize() {
        new EntityRegistry();
        new ItemRegistry();
        new BlockRegistry();
        new SoundRegistry();

        EventHandlerRegistry.registerEventHandlers();
        PacketRegistry.SERVER.registerPackets();

        ModParticles.registerParticles();

        Config.registerConfig();
    }
}