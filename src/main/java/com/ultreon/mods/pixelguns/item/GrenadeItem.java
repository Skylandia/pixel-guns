package com.ultreon.mods.pixelguns.item;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.ultreon.mods.pixelguns.client.GeoRendererGenerator;
import com.ultreon.mods.pixelguns.entity.projectile.thrown.GrenadeEntity;
import com.ultreon.mods.pixelguns.registry.ItemGroupRegistry;
import com.ultreon.mods.pixelguns.registry.ItemRegistry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GrenadeItem extends RangedWeaponItem implements GeoItem {

    public GrenadeItem() {
        super(new FabricItemSettings().maxCount(16));
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) return;

		if (remainingUseTicks < 0) return;

		if (stack.isEmpty() && !playerEntity.isCreative()) return;
		int useTicks = this.getMaxUseTime(stack) - remainingUseTicks;
		if (useTicks < 10) return;
		float throwStrength = GrenadeItem.getThrowStrength(useTicks);
        if (!world.isClient) {
			GrenadeEntity grenade = new GrenadeEntity(world, playerEntity);
			grenade.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0f, throwStrength * 1.5f, 1.0f);
            world.spawnEntity(grenade);
        }
        if (!playerEntity.isCreative()) {
            stack.decrement(1);
        }
		user.swingHand(Hand.MAIN_HAND);
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    public static float getThrowStrength(int useTicks) {
		float throwStrength = (float) useTicks / 30;
		if (throwStrength < 1) return throwStrength;
        return 1;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 120;
    }

	@Override
	public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
		return false;
	}

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        player.setCurrentHand(hand);
		return TypedActionResult.fail(stack);
    }

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof PlayerEntity) {
			((PlayerEntity) user).getItemCooldownManager().set(this, 20);
		}
        stack.removeSubNbt("GeckoLibID");
        return stack;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return stack -> stack.isOf(ItemRegistry.GRENADE);
    }

    @Override
    public int getRange() {
        return 15;
    }


    /*
     * Animation Side
     */

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private GeoItemRenderer<GrenadeItem> renderer;

			@Override
			public BuiltinModelItemRenderer getCustomRenderer() {
				if (this.renderer == null)
					this.renderer = GeoRendererGenerator.item(GrenadeItem.this);

				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return this.renderProvider;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.cache;
	}
}