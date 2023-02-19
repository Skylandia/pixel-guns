package com.ultreon.mods.pixelguns.entity.projectile;

import com.ultreon.mods.pixelguns.registry.EntityRegistry;

import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class RocketEntity extends PersistentProjectileEntity implements GeoEntity {
	public RocketEntity(EntityType<? extends RocketEntity> entityType, World world) {
		super(entityType, world);
	}

	public RocketEntity(World world, LivingEntity owner) {
		super(EntityRegistry.ROCKET, world);
		this.refreshPositionAndAngles(owner.getX(), owner.getEyeY(), owner.getZ(), owner.getYaw(), owner.getPitch());
		this.setVelocity(owner.getRotationVector().normalize().multiply(1.5f));
		this.setOwner(owner);
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		if (!this.world.isClient) {
			Entity victim = entityHitResult.getEntity();
			Entity aggressor = this.getOwner();
			victim.damage(DamageSource.thrownProjectile(victim, aggressor), 8.0F);
		}
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.world.isClient) {
			this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.0F, false, World.ExplosionSourceType.MOB);
			this.discard();
		}
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected boolean canHit(Entity entity) {
		return false;
	}

	@Override
	protected ItemStack asItemStack() {
		return ItemRegistry.ROCKET.getDefaultStack();
	}

	/*
	 * Animation Side
	 */

	private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}
}