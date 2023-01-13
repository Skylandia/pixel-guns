package com.ultreon.mods.pixelguns;

import com.ultreon.mods.pixelguns.registry.*;
import com.ultreon.mods.pixelguns.registry.ModelPredicateRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(value = EnvType.CLIENT)
public class PixelGunsClient implements ClientModInitializer {

    public void onInitializeClient() {
        KeybindRegistry.registerKeybinds();

        EntityRegistry.registerEntityRenderers();
        BlockRegistry.registerBlockRenderers();
        ItemRegistry.registerItemRenderers();
        ArmorRegistry.registerArmorRenderers();

        ScreenHandlerRegistry.registerScreenRenderers();

        ModelPredicateRegistry.registerModelPredicates();

        ClientPlayNetworking.registerGlobalReceiver(PixelGuns.RECOIL_PACKET_ID, (client, handler, buf, sender) -> {
            float kick = buf.readFloat();
            client.execute(() -> {
                if (client.player != null) {
                    if (Config.DO_RECOIL.get()) {
                        client.player.setPitch(kick);
                    }
                }
            });
        });
        

    }
}