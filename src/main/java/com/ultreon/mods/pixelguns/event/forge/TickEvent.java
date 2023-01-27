package com.ultreon.mods.pixelguns.event.forge;

import com.ultreon.mods.pixelguns.util.LogicalSide;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.function.BooleanSupplier;

public class TickEvent extends Event
{
	public enum Type {
		LEVEL, PLAYER, CLIENT, SERVER, RENDER
	}

	public enum Phase {
		START, END
	}
	public final Type type;
	public final LogicalSide side;
	public final Phase phase;
	public TickEvent(Type type, LogicalSide side, Phase phase)
	{
		this.type = type;
		this.side = side;
		this.phase = phase;
	}

	public static class ServerTickEvent extends TickEvent {
		private final BooleanSupplier haveTime;
		private final MinecraftServer server;

		public ServerTickEvent(Phase phase, BooleanSupplier haveTime, MinecraftServer server)
		{
			super(Type.SERVER, LogicalSide.SERVER, phase);
			this.haveTime = haveTime;
			this.server = server;
		}

		/**
		 * @return {@code true} whether the server has enough time to perform any
		 *         additional tasks (usually IO related) during the current tick,
		 *         otherwise {@code false}
		 */
		public boolean haveTime()
		{
			return this.haveTime.getAsBoolean();
		}

		/**
		 * {@return the server instance}
		 */
		public MinecraftServer getServer()
		{
			return server;
		}
	}

	public static class ClientTickEvent extends TickEvent {
		public ClientTickEvent(Phase phase)
		{
			super(Type.CLIENT, LogicalSide.CLIENT, phase);
		}
	}

	public static class LevelTickEvent extends TickEvent {
		public final World level;
		private final BooleanSupplier haveTime;

		public LevelTickEvent(LogicalSide side, Phase phase, World level, BooleanSupplier haveTime)
		{
			super(Type.LEVEL, side, phase);
			this.level = level;
			this.haveTime = haveTime;
		}

		/**
		 * @return {@code true} whether the server has enough time to perform any
		 *         additional tasks (usually IO related) during the current tick,
		 *         otherwise {@code false}
		 *
		 * @see ServerTickEvent#haveTime()
		 */
		public boolean haveTime()
		{
			return this.haveTime.getAsBoolean();
		}
	}
	public static class PlayerTickEvent extends TickEvent {
		public final PlayerEntity player;

		public PlayerTickEvent(Phase phase, PlayerEntity player)
		{
			super(Type.PLAYER, player instanceof ServerPlayerEntity ? LogicalSide.SERVER : LogicalSide.CLIENT, phase);
			this.player = player;
		}
	}

	public static class RenderTickEvent extends TickEvent {
		public final float renderTickTime;
		public RenderTickEvent(Phase phase, float renderTickTime)
		{
			super(Type.RENDER, LogicalSide.CLIENT, phase);
			this.renderTickTime = renderTickTime;
		}
	}
}
