package com.ultreon.mods.pixelguns.item;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Items;

public class PoliceShieldItem extends FabricShieldItem {
	public PoliceShieldItem() {
		super(new FabricItemSettings().maxDamage(500), 10, 0, Items.AIR);
	}
}
