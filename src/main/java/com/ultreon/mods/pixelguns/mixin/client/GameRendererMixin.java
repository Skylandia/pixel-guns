package com.ultreon.mods.pixelguns.mixin.client;

import com.ultreon.mods.pixelguns.client.handler.RecoilHandler;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(method = "render", at = @At("HEAD"))
	private void render(float f, long l, boolean bl, CallbackInfo ci) {
		RecoilHandler.get().onRenderTick();
	}
}
