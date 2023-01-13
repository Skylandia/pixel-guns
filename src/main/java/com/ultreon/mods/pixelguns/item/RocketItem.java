package com.ultreon.mods.pixelguns.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class RocketItem extends Item implements IAnimatable {
    public RocketItem() {
        super(new FabricItemSettings().group(ModCreativeTab.WEAPONS).maxCount(64));
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
