package com.ultreon.mods.pixelguns.item.gun.variant;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;

public class SubmachineGunItem extends GunItem {
    public SubmachineGunItem() {
        super(
            GunItem.AmmoLoadingType.AUTOMATIC,
            5.0f,
            128,
            2,
            30,
            ItemRegistry.LIGHT_BULLETS,
            44,
            0.0f,
            10.0f,
            1,
            LoadingType.CLIP,
            new SoundEvent[] {SoundRegistry.RELOAD_GENERIC_SMG_P1, SoundRegistry.RELOAD_GENERIC_SMG_P2, SoundRegistry.RELOAD_GENERIC_SMG_P3},
            SoundRegistry.SMG_MACHINEPISTOL,
            1,
            false,
            new int[] {5, 17, 30},
            new ItemStack[] {
                new ItemStack(Items.IRON_INGOT, 22)
            }
        );
    }
}
