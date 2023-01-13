package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.client.armor.renderer.ArmoredArmorRenderer;
import com.ultreon.mods.pixelguns.client.armor.renderer.HazardArmorRenderer;
import com.ultreon.mods.pixelguns.client.entity.renderer.*;
import com.ultreon.mods.pixelguns.client.item.renderer.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class RendererRegistry {
    public static void registerItemRenderers() {
        GeoItemRenderer.registerItemRenderer(ItemRegistry.INFINITY_GUN, new InfinityGunItemRenderer());
        GeoItemRenderer.registerItemRenderer(ItemRegistry.ROCKET_LAUNCHER, new RocketLauncherItemRenderer());
        GeoItemRenderer.registerItemRenderer(ItemRegistry.KATANA, new KatanaItemRenderer());
        GeoItemRenderer.registerItemRenderer(ItemRegistry.CROWBAR, new CrowbarItemRenderer());
        GeoItemRenderer.registerItemRenderer(ItemRegistry.GRENADE, new GrenadeItemRenderer());
    }
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(EntityRegistry.GRENADE, GrenadeEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ROCKET, RocketEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.NUCLEAR_BOMB, NuclearBombEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.NUCLEAR_EXPLOSION, NuclearExplosionEntityRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.GAS, GasRenderer::new);
    }
    public static void registerArmorRenderers() {
        GeoArmorRenderer.registerArmorRenderer(new ArmoredArmorRenderer(), ItemRegistry.ARMORED_VEST);
        GeoArmorRenderer.registerArmorRenderer(new HazardArmorRenderer(), ItemRegistry.GAS_MASK);
    }

}
