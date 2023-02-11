package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.PixelGuns;
import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class ConfigRegistry {
    private static ModConfig config;

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER;
    public static final ForgeConfigSpec.BooleanValue DO_RECOIL;

    static {
        CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        DO_RECOIL = CLIENT_BUILDER.comment("Do recoil when shooting.").define("do_recoil", true);
    }

    public static void registerConfig() {
        config = ForgeConfigRegistry.INSTANCE.register(PixelGuns.MOD_ID, ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }

    public static void save() {
        config.save();
    }
}
