package com.ultreon.mods.pixelguns.armor;

import com.ultreon.mods.pixelguns.registry.ItemGroupRegistry;
import com.ultreon.mods.pixelguns.registry.ArmorRegistry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;

public class ArmoredArmor extends ArmorItem {
	public ArmoredArmor(EquipmentSlot equipmentSlot) {
		super(ArmorRegistry.ARMORED, equipmentSlot, new FabricItemSettings());
	}
}