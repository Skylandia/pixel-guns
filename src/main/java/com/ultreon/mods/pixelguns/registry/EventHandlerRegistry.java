package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.client.handler.RecoilHandler;
import com.ultreon.mods.pixelguns.event.GunFireEvent;
import com.ultreon.mods.pixelguns.event.forge.Event;
import com.ultreon.mods.pixelguns.event.forge.TickEvent;

public class EventHandlerRegistry {
	public static void registerEventHandlers() {
		Event.registerHandler(GunFireEvent.Post.class, RecoilHandler::onGunFire);
		Event.registerHandler(TickEvent.RenderTickEvent.class, RecoilHandler::onRenderTick);
	}
}
