package com.ultreon.mods.pixelguns.item.gun.variant;

import com.ultreon.mods.pixelguns.client.GeoRendererGenerator;
import com.ultreon.mods.pixelguns.entity.projectile.RocketEntity;
import com.ultreon.mods.pixelguns.event.GunFireEvent;
import com.ultreon.mods.pixelguns.event.forge.Event;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RocketLauncherItem extends GunItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public RocketLauncherItem() {
        super(
            false,
            25.0f,
            128,
            30,
            1,
            ItemRegistry.ROCKET,
            30,
            0,
            25.0f,
            1,
            LoadingType.INDIVIDUAL,
            SoundRegistry.ROCKET_LAUNCHER_RELOAD,
            SoundRegistry.ROCKET_LAUNCHER_FIRE,
            1,
            false,
            new int[] {1, 8, 17},
            new ItemStack[] {
                new ItemStack(Items.IRON_INGOT, 32)
            }
        );
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void shoot(PlayerEntity player, ItemStack stack) {
        Event.call(new GunFireEvent.Pre(player, stack));
        if (player.world.isClient) {
            Event.call(new GunFireEvent.Post(player, stack));
            return;
        }
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
        ServerWorld world = serverPlayer.getWorld();

        triggerAnim(player, GeoItem.getOrAssignId(player.getMainHandStack(), world), "shoot_controller", "shoot");

        player.getItemCooldownManager().set(this, this.fireCooldown);

        // Spawn Rocket
        RocketEntity rocket = new RocketEntity(world, player);
        world.spawnEntity(rocket);

        if (!player.getAbilities().creativeMode) {
            this.useAmmo(stack);
        }

        this.playFireAudio(world, player);

        Event.call(new GunFireEvent.Post(player, stack));
    }

    @Override
    protected void doReloadTick(World world, NbtCompound nbtCompound, PlayerEntity player, ItemStack stack) {
        int reloadTick = nbtCompound.getInt("reloadTick");
        if (reloadTick == 0 && (world instanceof ServerWorld serverWorld)) {
            triggerAnim(player, GeoItem.getOrAssignId(player.getMainHandStack(), serverWorld), "reload_controller", "reload");
        }
        super.doReloadTick(world, nbtCompound, player, stack);
    }

    /*
     * ANIMATION SIDE
     */

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "reload_controller", 1, state -> PlayState.CONTINUE)
            .triggerableAnim("reload", DefaultAnimations.ATTACK_BITE));
        controllers.add(new AnimationController<>(this, "shoot_controller", 1, state -> PlayState.CONTINUE)
            .triggerableAnim("shoot", DefaultAnimations.ATTACK_BITE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private final GeoItemRenderer<RocketLauncherItem> renderer = GeoRendererGenerator.gun(RocketLauncherItem.this);

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }
}