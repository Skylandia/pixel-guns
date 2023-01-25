package com.ultreon.mods.pixelguns.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class GeoRendererGenerator {
    public static <T extends Item & IAnimatable> GeoItemRenderer<T> generateItemRenderer(AnimatedGeoModel<T> modelProvider) {
        return new GeoItemRenderer<>(modelProvider);
    }

    public static <T extends LivingEntity & IAnimatable> GeoEntityRenderer<T> generateLivingEntityRenderer(EntityRendererFactory.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        return new GeoEntityRenderer<>(renderManager, modelProvider) {};
    }

    public static <T extends Entity & IAnimatable> GeoProjectilesRenderer<T> generateEntityRenderer(EntityRendererFactory.Context renderManager, AnimatedGeoModel<T> modelProvider) {
        return new GeoProjectilesRenderer<>(renderManager, modelProvider) {};
    }

    public static <T extends ArmorItem & IAnimatable> GeoArmorRenderer<T> generateArmorRenderer(AnimatedGeoModel<T> modelProvider) {
        return new GeoArmorRenderer<>(modelProvider) {
            {
                this.headBone = "armorHead";
                this.bodyBone = "armorBody";
                this.rightArmBone = "armorRightArm";
                this.leftArmBone = "armorLeftArm";
                this.rightLegBone = "armorRightLeg";
                this.leftLegBone = "armorLeftLeg";
                this.rightBootBone = "armorRightBoot";
                this.leftBootBone = "armorLeftBoot";
            }
        };
    }
}