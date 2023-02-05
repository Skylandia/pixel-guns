package com.ultreon.mods.pixelguns.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnergyBatteryItem extends Item {

	public EnergyBatteryItem() {
		super(new FabricItemSettings().group(ModCreativeTab.WEAPONS).maxCount(12));
	}

	@Override
	public boolean hasGlint(ItemStack itemStack) {
		return true;
	}
}
