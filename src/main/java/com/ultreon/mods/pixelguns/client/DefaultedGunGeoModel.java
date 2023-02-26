package com.ultreon.mods.pixelguns.client;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class DefaultedGunGeoModel<T extends GeoAnimatable> extends DefaultedItemGeoModel<T> {
	public DefaultedGunGeoModel(Identifier assetSubpath) {
		super(assetSubpath.withPrefixedPath("gun/"));
	}
}
