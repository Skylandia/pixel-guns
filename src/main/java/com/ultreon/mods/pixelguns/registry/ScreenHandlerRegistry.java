package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.client.gui.ingame.WorkshopScreen;
import com.ultreon.mods.pixelguns.client.screen.WorkshopScreenHandler;
import com.ultreon.mods.pixelguns.util.ResourcePath;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ScreenHandlerRegistry {
    public static final ScreenHandlerType<WorkshopScreenHandler> WORKSHOP_SCREEN_HANDLER = ScreenHandlerRegistry.register("workshop", WorkshopScreenHandler::new);

    public static void registerScreenRenderers() {
        HandledScreens.register(ScreenHandlerRegistry.WORKSHOP_SCREEN_HANDLER, WorkshopScreen::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, ResourcePath.get(name), new ScreenHandlerType<>(factory));
    }
}
