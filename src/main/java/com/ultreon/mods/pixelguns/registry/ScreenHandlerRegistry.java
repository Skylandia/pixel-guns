package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.client.screen.WorkshopScreen;
import com.ultreon.mods.pixelguns.client.screen.handler.WorkshopScreenHandler;
import com.ultreon.mods.pixelguns.util.ResourcePath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandlerRegistry {
    public static final ScreenHandlerType<WorkshopScreenHandler> WORKSHOP_SCREEN_HANDLER;

    static {
        WORKSHOP_SCREEN_HANDLER = ScreenHandlerRegistry.register("workshop", WorkshopScreenHandler::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, ResourcePath.get(name), new ScreenHandlerType<>(factory));
    }

    @Environment(value = EnvType.CLIENT)
    public static class RENDERER {
        public static void registerScreenRenderers() {
            RENDERER.registerScreenRenderer(ScreenHandlerRegistry.WORKSHOP_SCREEN_HANDLER, WorkshopScreen::new);
        }

        private static <M extends ScreenHandler, U extends Screen & ScreenHandlerProvider<M>> void registerScreenRenderer(ScreenHandlerType<? extends M> screenHandlerType, HandledScreens.Provider<M, U> provider) {
            HandledScreens.register(screenHandlerType, provider);
        }
    }
}