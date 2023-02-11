package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.util.ResourcePath;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class TagRegistry {
	public static final TagKey<Item> GUNS = TagRegistry.registerItem("guns");
	public static final TagKey<Item> AMMUNITION = TagRegistry.registerItem("ammunition");

	private static TagKey<Item> registerItem(String name) {
		return TagKey.of(RegistryKeys.ITEM, ResourcePath.get(name));
	}
	private static TagKey<Block> registerBlock(String name) {
		return TagKey.of(RegistryKeys.BLOCK, ResourcePath.get(name));
	}
}
