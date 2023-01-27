package com.ultreon.mods.pixelguns.item.gun.variant;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;
import net.minecraft.sound.SoundEvent;

public class AssaultRifleItem extends GunItem {
    public AssaultRifleItem() {
        super(
            GunItem.AmmoLoadingType.AUTOMATIC,
            5.5f,
            128,
            3,
            30,
            ItemRegistry.STANDARD_RIFLE_BULLET,
            44,
            0.15f,
            3.0f,
            1,
            LoadingType.CLIP,
            new SoundEvent[] {SoundRegistry.RELOAD_GENERIC_AR_P1, SoundRegistry.RELOAD_GENERIC_AR_P2, SoundRegistry.RELOAD_GENERIC_AR_P3},
            SoundRegistry.ASSAULTRIFLE_LIGHT,
            1,
            false,
            new int[] {6, 18, 37}
        );
    }
}