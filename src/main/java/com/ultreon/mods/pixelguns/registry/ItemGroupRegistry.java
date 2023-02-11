package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.util.ResourcePath;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ItemGroupRegistry {
    public static final ItemGroup MISC = FabricItemGroup.builder(ResourcePath.get("misc"))
        .displayName(Text.translatable("itemGroup.pixel_guns.misc"))
        .icon(() -> new ItemStack(ItemRegistry.ARMORED_VEST))
        .build();
    public static final ItemGroup WEAPONS = FabricItemGroup.builder(ResourcePath.get("guns"))
        .displayName(Text.translatable("itemGroup.pixel_guns.guns"))
        .icon(() -> new ItemStack(ItemRegistry.REVOLVER))
        .build();

    public static void registerItemGroups() {
        ItemGroupEvents.modifyEntriesEvent(WEAPONS).register(entries -> {
            entries.add(ItemRegistry.SHOTGUN_SHELL);
            entries.add(ItemRegistry.LIGHT_BULLETS);
            entries.add(ItemRegistry.MEDIUM_BULLETS);
            entries.add(ItemRegistry.HEAVY_BULLETS);
            entries.add(ItemRegistry.ROCKET);
            entries.add(ItemRegistry.ENERGY_BATTERY);

            entries.add(ItemRegistry.PUMP_SHOTGUN);
            entries.add(ItemRegistry.COMBAT_SHOTGUN);
            entries.add(ItemRegistry.PISTOL);
            entries.add(ItemRegistry.REVOLVER);
            entries.add(ItemRegistry.SUBMACHINE_GUN);
            entries.add(ItemRegistry.LIGHT_ASSAULT_RIFLE);
            entries.add(ItemRegistry.ASSAULT_RIFLE);
            entries.add(ItemRegistry.SNIPER_RIFLE);
            entries.add(ItemRegistry.ROCKET_LAUNCHER);
        });

        ItemGroupEvents.modifyEntriesEvent(MISC).register(entries -> {
            entries.add(ItemRegistry.WORKSHOP);

            entries.add(ItemRegistry.GAS_MASK);
            entries.add(ItemRegistry.ARMORED_VEST);
        });
    }
}
