package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.util.ResourcePath;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;

public class TagRegistry {
	public static final TagKey<Item> GUNS = TagRegistry.registerItem("guns");
	public static final TagKey<Item> AMMUNITION = TagRegistry.registerItem("ammunition");

	private static TagKey<Item> registerItem(String name) {
		return TagKey.of(Registry.ITEM_KEY, ResourcePath.get(name));
	}
	private static TagKey<Block> registerBlock(String name) {
		return TagKey.of(Registry.BLOCK_KEY, ResourcePath.get(name));
	}
}
