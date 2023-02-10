package com.ultreon.mods.pixelguns.data;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.registry.TagRegistry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class TagGenerator extends FabricTagProvider<Item> {
	public TagGenerator(FabricDataGenerator dataGenerator) {
		super(dataGenerator, Registry.ITEM);
	}

	@Override
	protected void generateTags() {
		getOrCreateTagBuilder(TagRegistry.GUNS)
			.add(ItemRegistry.PISTOL)
			.add(ItemRegistry.HEAVY_PISTOL)
			.add(ItemRegistry.REVOLVER)
			.add(ItemRegistry.PUMP_SHOTGUN)
			.add(ItemRegistry.COMBAT_SHOTGUN)
			.add(ItemRegistry.SUBMACHINE_GUN)
			.add(ItemRegistry.LIGHT_ASSAULT_RIFLE)
			.add(ItemRegistry.ASSAULT_RIFLE)
			.add(ItemRegistry.SNIPER_RIFLE)
			.add(ItemRegistry.ROCKET_LAUNCHER)
//			.add(ItemRegistry.INFINITY_GUN)
		;

		getOrCreateTagBuilder(TagRegistry.AMMUNITION)
			.add(ItemRegistry.STANDARD_HANDGUN_BULLET)
			.add(ItemRegistry.SHOTGUN_SHELL)
			.add(ItemRegistry.HEAVY_HANDGUN_BULLET)
			.add(ItemRegistry.STANDARD_RIFLE_BULLET)
			.add(ItemRegistry.HEAVY_RIFLE_BULLET)
			.add(ItemRegistry.ROCKET)
			.add(ItemRegistry.ENERGY_BATTERY);
	}
}
