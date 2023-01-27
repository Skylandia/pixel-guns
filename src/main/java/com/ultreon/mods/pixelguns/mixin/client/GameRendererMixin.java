package com.ultreon.mods.pixelguns.mixin.client;

import com.ultreon.mods.pixelguns.event.forge.Event;
import com.ultreon.mods.pixelguns.event.forge.TickEvent;

import net.minecraft.client.render.GameRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void startRender(float tickDelta, long systemNanoTime, boolean shouldTick, CallbackInfo ci) {
		Event.call(new TickEvent.RenderTickEvent(TickEvent.Phase.START, tickDelta));
	}
	@Inject(method = "render", at = @At("RETURN"))
	private void endRender(float tickDelta, long systemNanoTime, boolean shouldTick, CallbackInfo ci) {
		Event.call(new TickEvent.RenderTickEvent(TickEvent.Phase.END, tickDelta));
	}
}
