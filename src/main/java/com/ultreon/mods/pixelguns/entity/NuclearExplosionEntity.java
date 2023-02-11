package com.ultreon.mods.pixelguns.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class NuclearExplosionEntity extends Entity implements GeoEntity {

    public NuclearExplosionEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    @Override
    public void tick() {
        super.tick();
    }

    public float getScale() {
        return 100.0f;
    }

    @Override
    protected void initDataTracker() {}

    @Override
    protected void readCustomDataFromNbt(NbtCompound var1) {}

    @Override
    protected void writeCustomDataToNbt(NbtCompound var1) {}

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    /*
     * Animation Side
     */

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
