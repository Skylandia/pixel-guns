package com.ultreon.mods.pixelguns.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterials;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class CrowbarItem extends SwordItem implements IAnimatable {

    public CrowbarItem() {
        super(ToolMaterials.IRON, 3, -2.4f, new FabricItemSettings().group(ModCreativeTab.WEAPONS));
    }

    /*
     * ANIMATION
     */

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData data) {}

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}

