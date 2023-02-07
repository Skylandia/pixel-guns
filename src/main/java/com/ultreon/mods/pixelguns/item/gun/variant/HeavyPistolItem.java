package com.ultreon.mods.pixelguns.item.gun.variant;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;

public class HeavyPistolItem extends GunItem {
    public HeavyPistolItem() {
        super(
            GunItem.AmmoLoadingType.SEMI_AUTOMATIC,
            11.0f,
            128,
            5,
            7,
            ItemRegistry.HEAVY_HANDGUN_BULLET,
            26,
            0.25f,
            10.0f,
            1,
            LoadingType.CLIP,
            new SoundEvent[] {SoundRegistry.RELOAD_GENERIC_PISTOL_P1, SoundRegistry.RELOAD_GENERIC_PISTOL_P2, SoundRegistry.RELOAD_GENERIC_PISTOL_P3},
            SoundRegistry.PISTOL_HEAVY,
            1,
            false,
            new int[] {6, 16, 20},
            new ItemStack[] {
                new ItemStack(Items.IRON_INGOT, 18)
            }
        );
    }
}
