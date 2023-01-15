package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.util.ResourcePath;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;

public class ModelPredicateRegistry {
    public static void registerModelPredicates() {
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.PISTOL);
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.HEAVY_PISTOL);
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.MAGNUM_REVOLVER);
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.MACHINE_PISTOL);
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.LIGHT_ASSAULT_RIFLE);
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.HEAVY_ASSAULT_RIFLE);
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.COMBAT_SHOTGUN);
        ModelPredicateRegistry.registerGunPredicate(ItemRegistry.CLASSIC_SNIPER_RIFLE);

        ModelPredicateProviderRegistry.register(ItemRegistry.ROCKET_LAUNCHER, ResourcePath.get("aiming"), (stack, world, entity, seed) -> entity != null && MinecraftClient.getInstance().options.useKey.isPressed() && GunItem.isLoaded(stack) ? 1.0f : 0.0f);
    }

    private static void registerGunPredicate(Item gun) {
        ModelPredicateProviderRegistry.register(gun, ResourcePath.get("load_tick"), (stack, world, entity, seed) -> entity != null && stack.getOrCreateNbt().getBoolean("isReloading") ? (float) stack.getOrCreateNbt().getInt("reloadTick") / 200.0f : 0.0f);
        ModelPredicateProviderRegistry.register(gun, ResourcePath.get("loading"), (stack, world, entity, seed) -> entity != null && stack.getOrCreateNbt().getBoolean("isReloading") ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(gun, ResourcePath.get("aiming"), (stack, world, entity, seed) -> entity != null && MinecraftClient.getInstance().options.useKey.isPressed() && GunItem.isLoaded(stack) ? 1.0f : 0.0f);
        ModelPredicateProviderRegistry.register(gun, ResourcePath.get("sprinting"), (stack, world, entity, seed) -> entity != null && entity.getStackInHand(Hand.MAIN_HAND) == stack && entity.isSprinting() ? 1.0f : 0.0f);
    }
}

