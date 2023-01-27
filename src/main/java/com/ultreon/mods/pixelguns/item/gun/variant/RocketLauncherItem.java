package com.ultreon.mods.pixelguns.item.gun.variant;

import com.ultreon.mods.pixelguns.entity.projectile.RocketEntity;
import com.ultreon.mods.pixelguns.event.GunFireEvent;
import com.ultreon.mods.pixelguns.event.forge.Event;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class RocketLauncherItem extends GunItem implements IAnimatable, ISyncable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public RocketLauncherItem() {
        super(
            GunItem.AmmoLoadingType.SEMI_AUTOMATIC,
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
            new SoundEvent[] {SoundRegistry.RELOAD_GENERIC_SNIPER_P1, SoundRegistry.RELOAD_GENERIC_SNIPER_P2, SoundRegistry.RELOAD_GENERIC_SNIPER_P3},
            SoundRegistry.SNIPER_CLASSIC,
            1,
            false,
            new int[] {1, 8, 17}
        );
        GeckoLibNetwork.registerSyncable(this);
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

        this.sendAnimationPacket(ANIM_FIRE, world, player, stack);

        player.getItemCooldownManager().set(this, this.fireCooldown);

        RocketEntity rocket = new RocketEntity(world, player);
        rocket.setPosition(player.getEyePos().subtract(0, 0.1, 0));
        rocket.setPitch(player.getPitch());

        rocket.setVelocity(player.getRotationVector().normalize().multiply(1.5));
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
            sendAnimationPacket(ANIM_RELOAD, serverWorld, player, stack);
        }
        super.doReloadTick(world, nbtCompound, player, stack);
    }

    /*
     * ANIMATION SIDE
     */

    protected void sendAnimationPacket(int animation, ServerWorld world, PlayerEntity player, ItemStack stack) {
        final int id = GeckoLibUtil.guaranteeIDForStack(stack, world);
        GeckoLibNetwork.syncAnimation(player, this, id, animation);
        for (ServerPlayerEntity otherPlayer : PlayerLookup.tracking(player)) {
            GeckoLibNetwork.syncAnimation(otherPlayer, this, id, animation);
        }
    }

    public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 1, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public static final int ANIM_RELOAD = 0;
    public static final int ANIM_FIRE = 1;
    @Override
    public void onAnimationSync(int id, int state) {
        switch (state) {
            case ANIM_RELOAD -> {
                final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, "controller");
                if (controller.getAnimationState() == AnimationState.Stopped) {
                    controller.markNeedsReload();
                    controller.setAnimation(new AnimationBuilder().addAnimation("reload", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
                }
            }
            case ANIM_FIRE -> {
                final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, "controller");
                if (controller.getAnimationState() == AnimationState.Stopped) {
                    controller.markNeedsReload();
                    controller.setAnimation(new AnimationBuilder().addAnimation("fire", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
                }
            }
        }
    }
}