package com.ultreon.mods.pixelguns.armor;

import com.ultreon.mods.pixelguns.item.ModCreativeTab;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ArmoredArmor extends ArmorItem implements IAnimatable {
	
	private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public ArmoredArmor() {
		super(ModArmorMaterials.ARMORED, EquipmentSlot.CHEST, new FabricItemSettings().group(ModCreativeTab.MISC));
	}

	@Override
	public void registerControllers(AnimationData data) {}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}
}