package com.ultreon.mods.pixelguns.client;

import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.util.ResourcePath;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class GeoRendererGenerator {
	public static <T extends GunItem & GeoAnimatable> GeoItemRenderer<T> gun(T item) {
		return new GeoItemRenderer<>(new DefaultedGunGeoModel<>(Registries.ITEM.getId(item)));
	}
	public static <T extends Item & GeoAnimatable> GeoItemRenderer<T> item(T item) {
		return new GeoItemRenderer<>(new DefaultedItemGeoModel<>(Registries.ITEM.getId(item)));
	}

	public static <T extends Entity & GeoEntity> GeoEntityRenderer<T> entity(EntityType<T> entityType, EntityRendererFactory.Context renderManager) {
		return new GeoEntityRenderer<>(renderManager, new DefaultedEntityGeoModel<>(Registries.ENTITY_TYPE.getId(entityType))) {
			@Override
			public void render(T entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
				poseStack.push();
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch()));

				super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
				poseStack.pop();
			}
		};
	}

	public static <T extends ArmorItem & GeoItem> GeoArmorRenderer<T> armor(T armor) {
		return new GeoArmorRenderer<>(new DefaultedItemGeoModel<>(ResourcePath.get("armor/" + armor.getMaterial().getName() + "_armor")));
	}
}
