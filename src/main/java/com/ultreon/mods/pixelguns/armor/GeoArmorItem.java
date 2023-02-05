package com.ultreon.mods.pixelguns.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class GeoArmorItem extends ArmorItem implements IAnimatable {

	protected final AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public GeoArmorItem(ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Settings settings) {
		super(armorMaterial, equipmentSlot, settings);
	}

	@Override
	public void registerControllers(AnimationData animationData) {}

	@Override
	public AnimationFactory getFactory() {
		return factory;
	}
}
