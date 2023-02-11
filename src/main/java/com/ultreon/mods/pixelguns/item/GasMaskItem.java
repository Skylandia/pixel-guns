package com.ultreon.mods.pixelguns.item;

import com.ultreon.mods.pixelguns.armor.HazardArmor;
import com.ultreon.mods.pixelguns.client.GeoRendererGenerator;
import com.ultreon.mods.pixelguns.entity.GasEntity;

import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.RenderProvider;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class GasMaskItem extends HazardArmor implements GeoItem {

    public GasMaskItem() {
        super(EquipmentSlot.HEAD);
    }
    
    @Override
    public void inventoryTick(ItemStack itemStack, World world, Entity entity, int i, boolean bl) {
        if (entity instanceof ServerPlayerEntity player) {
            if (player.getInventory().armor.get(EquipmentSlot.HEAD.getEntitySlotId()) == itemStack && !world.getEntitiesByClass(GasEntity.class, entity.getBoundingBox(), a -> true).isEmpty()) {
                if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                    itemStack.decrement(1);
                } else {
                    itemStack.setDamage(itemStack.getDamage() + 1);
                }
            }
        }
    }

    /*
     * Animation Side
     */

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoItemRenderer<GasMaskItem> renderer;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = GeoRendererGenerator.item(GasMaskItem.this);

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