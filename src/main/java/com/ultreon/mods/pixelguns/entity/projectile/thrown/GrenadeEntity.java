package com.ultreon.mods.pixelguns.entity.projectile.thrown;

import com.ultreon.mods.pixelguns.registry.EntityRegistry;
import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.registry.SoundRegistry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GrenadeEntity extends ThrownItemEntity implements GeoEntity {

    public GrenadeEntity(EntityType<? extends GrenadeEntity> entityType, World world) {
        super(entityType, world);
    }

    public GrenadeEntity(World world, LivingEntity owner) {
        super(EntityRegistry.GRENADE, owner, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(DamageSource.thrownProjectile(this, this.getOwner()), 0.0f);
    }

    private void explode() {
        if (this.world.isClient) return;

        this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.0f, false, World.ExplosionSourceType.TNT);
        this.discard();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        explode();
        world.playSound(hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z, SoundRegistry.GRENADE_EXPLODE, SoundCategory.MASTER, 0.8f, 0.8f, false);
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.GRENADE;
    }



    /*
     * Animation Side
     */

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}