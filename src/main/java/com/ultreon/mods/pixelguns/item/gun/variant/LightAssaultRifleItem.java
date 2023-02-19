package com.ultreon.mods.pixelguns.item.gun.variant;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LightAssaultRifleItem extends GunItem {
    public LightAssaultRifleItem() {
        super(
            true,
            5.5f,
            128,
            3,
            30,
            ItemRegistry.MEDIUM_BULLETS,
            44,
            0.15f,
            3.0f,
            1,
            LoadingType.CLIP,
            SoundRegistry.LIGHT_ASSAULT_RIFLE_RELOAD,
            SoundRegistry.LIGHT_ASSAULT_RIFLE_FIRE,
            1,
            false,
            new int[] {6, 18, 37},
            new ItemStack[] {
                new ItemStack(Items.IRON_INGOT, 28)
            }
        );
    }
}