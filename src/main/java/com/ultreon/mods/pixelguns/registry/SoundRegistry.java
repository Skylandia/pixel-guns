package com.ultreon.mods.pixelguns.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {
    public static final SoundEvent RELOAD_GENERIC_PISTOL_P1 = SoundRegistry.register("generic_pistol_p1");
    public static final SoundEvent RELOAD_GENERIC_PISTOL_P2 = SoundRegistry.register("generic_pistol_p2");
    public static final SoundEvent RELOAD_GENERIC_PISTOL_P3 = SoundRegistry.register("generic_pistol_p3");
    public static final SoundEvent PISTOL_LIGHT = SoundRegistry.register("pistol_light");

    public static final SoundEvent RELOAD_GENERIC_REVOLVER_P1 = SoundRegistry.register("generic_revolver_p1");
    public static final SoundEvent RELOAD_GENERIC_REVOLVER_P2 = SoundRegistry.register("generic_revolver_p2");
    public static final SoundEvent RELOAD_GENERIC_REVOLVER_P3 = SoundRegistry.register("generic_revolver_p3");
    public static final SoundEvent REVOLVER = SoundRegistry.register("revolver");

    public static final SoundEvent RELOAD_GENERIC_SMG_P1 = SoundRegistry.register("generic_smg_p1");
    public static final SoundEvent RELOAD_GENERIC_SMG_P2 = SoundRegistry.register("generic_smg_p2");
    public static final SoundEvent RELOAD_GENERIC_SMG_P3 = SoundRegistry.register("generic_smg_p3");
    public static final SoundEvent SUBMACHINE_GUN = SoundRegistry.register("submachine_gun");

    public static final SoundEvent RELOAD_GENERIC_AR_P1 = SoundRegistry.register("generic_ar_p1");
    public static final SoundEvent RELOAD_GENERIC_AR_P2 = SoundRegistry.register("generic_ar_p2");
    public static final SoundEvent RELOAD_GENERIC_AR_P3 = SoundRegistry.register("generic_ar_p3");
    public static final SoundEvent ASSAULTRIFLE_LIGHT = SoundRegistry.register("assaultrifle_light");

    public static final SoundEvent RELOAD_HEAVY_AR_P1 = SoundRegistry.register("heavy_ar_p1");
    public static final SoundEvent RELOAD_HEAVY_AR_P2 = SoundRegistry.register("heavy_ar_p2");
    public static final SoundEvent RELOAD_HEAVY_AR_P3 = SoundRegistry.register("heavy_ar_p3");
    public static final SoundEvent ASSAULTRIFLE_HEAVY = SoundRegistry.register("assaultrifle_heavy");

    public static final SoundEvent RELOAD_COMBAT_SHOTGUN_P1 = SoundRegistry.register("combat_shotgun_p1");
    public static final SoundEvent RELOAD_COMBAT_SHOTGUN_P2 = SoundRegistry.register("combat_shotgun_p2");
    public static final SoundEvent RELOAD_COMBAT_SHOTGUN_P3 = SoundRegistry.register("combat_shotgun_p3");
    public static final SoundEvent COMBAT_SHOTGUN = SoundRegistry.register("combat_shotgun");

    public static final SoundEvent RELOAD_GENERIC_SNIPER_P1 = SoundRegistry.register("generic_sniper_p1");
    public static final SoundEvent RELOAD_GENERIC_SNIPER_P2 = SoundRegistry.register("generic_sniper_p2");
    public static final SoundEvent RELOAD_GENERIC_SNIPER_P3 = SoundRegistry.register("generic_sniper_p3");
    public static final SoundEvent RELOAD_CLASSIC_SNIPER_P2 = SoundRegistry.register("classic_sniper_p2");
    public static final SoundEvent SNIPER_RIFLE = SoundRegistry.register("sniper_rifle");

    public static final SoundEvent KATANA_SWING = SoundRegistry.register("katana_swoop");
    public static final SoundEvent KATANA_HIT = SoundRegistry.register("katana_hit");
    public static final SoundEvent GRENADE_EXPLODE = SoundRegistry.register("grenade_explode");
    public static final SoundEvent CROWBAR_HIT = SoundRegistry.register("crowbar_hit");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier("pixel_guns", name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }
}

