package com.ultreon.mods.pixelguns.event.forge;

import java.util.ArrayList;
import java.util.HashMap;

public class Event
{
	private static final HashMap<Class<? extends Event>, ArrayList<EventHandler<?>>> handlers = new HashMap<>();
	public Event() {}

	public static <T extends Event> void registerHandler(Class<T> type, EventHandler<T> handler)
	{
		handlers.computeIfAbsent(type, k -> new ArrayList<>());
		handlers.get(type).add(handler);
	}
	public static <T extends Event> void call(T event)
	{
		if (!handlers.containsKey(event.getClass()))
		{
			return;
		}

		for (EventHandler handler : handlers.get(event.getClass()))
		{
			handler.onEvent(event);
		}
	}
}