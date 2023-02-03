package com.ultreon.mods.pixelguns;

import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

public class Config {
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER;
    public static final ForgeConfigSpec.BooleanValue DO_RECOIL;

    private static ModConfig config;

    static {
        CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        DO_RECOIL = CLIENT_BUILDER.comment("Do recoil when shooting.").define("do_recoil", true);
    }

    public static void registerConfig() {
        config = ModLoadingContext.registerConfig(PixelGuns.MOD_ID, ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }

    public static void save() {
        config.save();
    }
}
