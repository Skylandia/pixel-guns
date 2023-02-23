package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.PixelGuns;
import com.ultreon.mods.pixelguns.config.PixelGunsConfig;
import eu.midnightdust.lib.config.MidnightConfig;

public class ConfigRegistry {
    public static void registerConfig() {
        MidnightConfig.init(PixelGuns.MOD_ID, PixelGunsConfig.class);
    }
}
