package com.ultreon.mods.pixelguns.mixin.client;

import com.ultreon.mods.pixelguns.item.JujuBowItem;
import com.ultreon.mods.pixelguns.network.packet.c2s.play.BowFireC2SPacket;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class BowKeybinds {

	@Shadow
	@Nullable
	public ClientPlayerEntity player;
	@Shadow private int itemUseCooldown;

	@Shadow @Nullable public ClientWorld world;

	@Inject(method = "handleInputEvents", at = @At("TAIL"))
	public void handleBowShoot(CallbackInfo info) {
		assert this.player != null;
		if (this.player.getMainHandStack().getItem() instanceof JujuBowItem bowItem) {
			if (MinecraftClient.getInstance().options.attackKey.isPressed() && this.itemUseCooldown == 0) {
				MinecraftClient.getInstance().options.attackKey.setPressed(false);
				this.itemUseCooldown = 4;

				BowFireC2SPacket.send();
				bowItem.instantFullPowerShot(this.player.getMainHandStack(), this.world, this.player);
			}
		}
	}
}
