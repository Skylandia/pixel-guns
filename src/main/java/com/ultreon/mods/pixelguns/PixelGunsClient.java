package com.ultreon.mods.pixelguns;

import com.ultreon.mods.pixelguns.client.gui.ingame.WorkshopScreen;
import com.ultreon.mods.pixelguns.registry.*;
import com.ultreon.mods.pixelguns.registry.ModelPredicateRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

@Environment(value = EnvType.CLIENT)
public class PixelGunsClient implements ClientModInitializer {


    public void onInitializeClient() {
        KeybindRegistry.registerKeybinds();

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.WORKSHOP, RenderLayer.getCutout());



        HandledScreens.register(ScreenHandlerRegistry.WORKSHOP_SCREEN_HANDLER, WorkshopScreen::new);

        ModelPredicateRegistry.registerModelPredicates();

        RendererRegistry.registerEntityRenderers();
        RendererRegistry.registerItemRenderers();
        RendererRegistry.registerArmorRenderers();

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