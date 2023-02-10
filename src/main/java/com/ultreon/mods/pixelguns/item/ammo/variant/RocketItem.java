package com.ultreon.mods.pixelguns.item.ammo.variant;

import com.ultreon.mods.pixelguns.util.WorkshopCraftable;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class RocketItem extends Item implements IAnimatable, WorkshopCraftable {
    public RocketItem() {
        super(new FabricItemSettings().maxCount(64));
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public ItemStack[] getIngredients() {
        return List.of(
            new ItemStack(Items.IRON_NUGGET, 1),
            new ItemStack(Items.GUNPOWDER, 4)
        ).toArray(new ItemStack[0]);
    }
}
