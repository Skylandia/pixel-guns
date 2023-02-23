package com.ultreon.mods.pixelguns.data;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.registry.TagRegistry;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider<Item> {

	public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, RegistryKeys.ITEM, registriesFuture);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup arg) {
		getOrCreateTagBuilder(TagRegistry.GUNS)
			.add(ItemRegistry.PISTOL)
			.add(ItemRegistry.REVOLVER)
			.add(ItemRegistry.COMBAT_SHOTGUN)
			.add(ItemRegistry.SUBMACHINE_GUN)
			.add(ItemRegistry.LIGHT_ASSAULT_RIFLE)
			.add(ItemRegistry.ASSAULT_RIFLE)
			.add(ItemRegistry.SNIPER_RIFLE)
			.add(ItemRegistry.ROCKET_LAUNCHER)
			.add(ItemRegistry.INFINITY_GUN)
		;

		getOrCreateTagBuilder(TagRegistry.AMMUNITION)
			.add(ItemRegistry.SHOTGUN_SHELL)
			.add(ItemRegistry.LIGHT_BULLETS)
			.add(ItemRegistry.MEDIUM_BULLETS)
			.add(ItemRegistry.HEAVY_BULLETS)
			.add(ItemRegistry.ROCKET)
			.add(ItemRegistry.ENERGY_BATTERY)
		;

		getOrCreateTagBuilder(TagRegistry.ATTACHMENTS)
			.add(ItemRegistry.SHORT_SCOPE)
			.add(ItemRegistry.MEDIUM_SCOPE)
			.add(ItemRegistry.LONG_SCOPE)
			.add(ItemRegistry.HEAVY_STOCK)
			.add(ItemRegistry.SPECIALISED_GRIP)
		;
	}
}