package com.ultreon.mods.pixelguns.client;

import com.ultreon.mods.pixelguns.PixelGuns;
import com.ultreon.mods.pixelguns.util.ResourcePath;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GeoModelGenerator {
    public static <T extends IAnimatable> AnimatedGeoModel<T> generateEntityModel(EntityType entityType) {
        String entityName = Registry.ENTITY_TYPE.getId(entityType).getPath();
        return new AnimatedGeoModel<>() {
            @Override
            public Identifier getAnimationResource(T animatable) {
                return ResourcePath.get("animations/" + entityName + ".animation.json");
            }

            @Override
            public Identifier getModelResource(T animatable) {
                return ResourcePath.get("geo/" + entityName + ".geo.json");
            }

            @Override
            public Identifier getTextureResource(T animatable) {
                return ResourcePath.get("textures/entity/" + entityName + ".png");
            }
        };
    }
    public static <T extends IAnimatable> AnimatedGeoModel<T> generateItemModel(Item item) {
        return generateItemModel(item, "item/");
    }
    public static <T extends IAnimatable> AnimatedGeoModel<T> generateItemModel(Item item, String texturesPath) {
        String itemName = Registry.ITEM.getId(item).getPath();
        PixelGuns.LOGGER.info(itemName);
        return new AnimatedGeoModel<>() {
            @Override
            public Identifier getAnimationResource(T animatable) {
                return ResourcePath.get("animations/" + itemName + ".animation.json");
            }

            @Override
            public Identifier getModelResource(T animatable) {
                return ResourcePath.get("geo/" + itemName + ".geo.json");
            }

            @Override
            public Identifier getTextureResource(T animatable) {
                return ResourcePath.get("textures/" + texturesPath + itemName + ".png");
            }
        };
    }
    public static <T extends IAnimatable> AnimatedGeoModel<T> generateArmorModel(ArmorMaterial armor) {
        String armorName = armor.getName();
        return new AnimatedGeoModel<>() {
            @Override
            public Identifier getAnimationResource(T animatable) {
                return ResourcePath.get("animations/" + armorName + ".animation.json");
            }

            @Override
            public Identifier getModelResource(T animatable) {
                return ResourcePath.get("geo/" + armorName + ".geo.json");
            }

            @Override
            public Identifier getTextureResource(T animatable) {
                return ResourcePath.get("textures/armor/" + armorName + ".png");
            }
        };
    }
}
