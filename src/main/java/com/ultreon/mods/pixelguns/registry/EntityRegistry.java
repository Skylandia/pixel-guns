package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.client.GeoRendererGenerator;
import com.ultreon.mods.pixelguns.entity.*;
import com.ultreon.mods.pixelguns.entity.projectile.thrown.*;
import com.ultreon.mods.pixelguns.entity.projectile.*;
import com.ultreon.mods.pixelguns.util.ResourcePath;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import software.bernie.geckolib.animatable.GeoEntity;

public class EntityRegistry {

    public static final EntityType<GrenadeEntity> GRENADE = EntityRegistry.register(
        "grenade", 
        FabricEntityTypeBuilder.<GrenadeEntity>create(
            SpawnGroup.MISC,
            GrenadeEntity::new
        )
        .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
        .trackRangeBlocks(4).trackedUpdateRate(20)
    );

    public static final EntityType<RocketEntity> ROCKET = EntityRegistry.register(
        "rocket",
        FabricEntityTypeBuilder.<RocketEntity>create(
            SpawnGroup.MISC,
            RocketEntity::new
        )
        .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
        .trackRangeBlocks(128).trackedUpdateRate(10)
    );

    private static <T extends Entity> EntityType<T> register(String name, FabricEntityTypeBuilder<T> type) {
        return Registry.register(
            Registries.ENTITY_TYPE,
            ResourcePath.get(name),
            type.build()
        );
    }

    @Environment(value = EnvType.CLIENT)
    public static class RENDERER {
        public static void registerEntityRenderers() {
            RENDERER.registerEntityRenderer(GRENADE);
            RENDERER.registerEntityRenderer(ROCKET);
        }

        private static <T extends Entity & GeoEntity> void registerEntityRenderer(EntityType<T> entityType) {
            EntityRendererRegistry.register(entityType, (ctx) -> GeoRendererGenerator.entity(entityType, ctx));
        }
    }
}