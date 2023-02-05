package com.ultreon.mods.pixelguns.armor;

import com.ultreon.mods.pixelguns.item.ModCreativeTab;
import com.ultreon.mods.pixelguns.registry.ArmorRegistry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;

public class HazardArmor extends GeoArmorItem {
	public HazardArmor(EquipmentSlot slot) {
		super(ArmorRegistry.HAZARD, slot, new FabricItemSettings().group(ModCreativeTab.MISC));
	}
}