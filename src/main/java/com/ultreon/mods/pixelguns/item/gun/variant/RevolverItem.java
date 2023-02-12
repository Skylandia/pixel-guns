package com.ultreon.mods.pixelguns.item.gun.variant;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;

public class RevolverItem extends GunItem {
    public RevolverItem() {
        super(
            GunItem.AmmoLoadingType.SEMI_AUTOMATIC,
            11.0f,
            128,
            10,
            6,
            ItemRegistry.MEDIUM_BULLETS,
            40,
            0.125f,
            10.0f,
            1,
            LoadingType.CLIP,
            new SoundEvent[] {SoundRegistry.RELOAD_GENERIC_REVOLVER_P1, SoundRegistry.RELOAD_GENERIC_REVOLVER_P2, SoundRegistry.RELOAD_GENERIC_REVOLVER_P3},
            SoundRegistry.revolver,
            1,
            false,
            new int[] {1, 26, 34},
            new ItemStack[] {
                new ItemStack(Items.IRON_INGOT, 18)
            }
        );
    }
}
