package com.ultreon.mods.pixelguns;

import com.ultreon.mods.pixelguns.registry.*;
import com.ultreon.mods.pixelguns.registry.ModelPredicateRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

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

        ModelPredicateRegistry.registerModelPredicates();
    }
}