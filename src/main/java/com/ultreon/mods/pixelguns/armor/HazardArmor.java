package com.ultreon.mods.pixelguns.armor;

import com.ultreon.mods.pixelguns.registry.ArmorRegistry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public class HazardArmor extends ArmorItem {
	public HazardArmor(EquipmentSlot slot) {
		super(ArmorRegistry.HAZARD, slot, new FabricItemSettings());
	}
}