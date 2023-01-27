package com.ultreon.mods.pixelguns.event.forge;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;

public class PlayerEvent extends LivingEvent
{
	private final PlayerEntity player;

	public PlayerEvent(PlayerEntity player)
	{
		super(player);
		this.player = player;
	}

	@Override
	public PlayerEntity getEntity()
	{
		return player;
	}

	public static class HarvestCheck extends PlayerEvent
	{
		private final BlockState state;
		private boolean success;

		public HarvestCheck(PlayerEntity player, BlockState state, boolean success)
		{
			super(player);
			this.state = state;
			this.success = success;
		}

		public BlockState getTargetBlock() { return this.state; }
		public boolean canHarvest() { return this.success; }
		public void setCanHarvest(boolean success){ this.success = success; }
	}

	public static class BreakSpeed extends PlayerEvent
	{
		private static final BlockPos LEGACY_UNKNOWN = new BlockPos(0, -1, 0);
		private final BlockState state;
		private final float originalSpeed;
		private float newSpeed = 0.0f;
		private final Optional<BlockPos> pos; // Y position of -1 notes unknown location

		public BreakSpeed(PlayerEntity player, BlockState state, float original, @Nullable BlockPos pos)
		{
			super(player);
			this.state = state;
			this.originalSpeed = original;
			this.setNewSpeed(original);
			this.pos = Optional.ofNullable(pos);
		}

		public BlockState getState() { return state; }
		public float getOriginalSpeed() { return originalSpeed; }
		public float getNewSpeed() { return newSpeed; }
		public void setNewSpeed(float newSpeed) { this.newSpeed = newSpeed; }
		public Optional<BlockPos> getPosition() { return this.pos; }
		@Deprecated(forRemoval = true, since ="1.19") public BlockPos getPos() { return pos.orElse(LEGACY_UNKNOWN); }
	}

	public static class NameFormat extends PlayerEvent
	{
		private final Text username;
		private Text displayname;

		public NameFormat(PlayerEntity player, Text username)
		{
			super(player);
			this.username = username;
			this.setDisplayname(username);
		}

		public Text getUsername()
		{
			return username;
		}

		public Text getDisplayname()
		{
			return displayname;
		}

		public void setDisplayname(Text displayname)
		{
			this.displayname = displayname;
		}
	}

	public static class TabListNameFormat extends PlayerEvent
	{
		@Nullable
		private Text displayName;

		public TabListNameFormat(PlayerEntity player)
		{
			super(player);
		}

		@Nullable
		public Text getDisplayName()
		{
			return displayName;
		}

		public void setDisplayName(@Nullable Text displayName)
		{
			this.displayName = displayName;
		}
	}

	/**
	 * Fired when the EntityPlayer is cloned, typically caused by the impl sending a RESPAWN_PLAYER event.
	 * Either caused by death, or by traveling from the End to the overworld.
	 */
	public static class Clone extends PlayerEvent
	{
		private final PlayerEntity original;
		private final boolean wasDeath;

		public Clone(PlayerEntity _new, PlayerEntity oldPlayer, boolean wasDeath)
		{
			super(_new);
			this.original = oldPlayer;
			this.wasDeath = wasDeath;
		}

		/**
		 * The old EntityPlayer that this new entity is a clone of.
		 */
		public PlayerEntity getOriginal()
		{
			return original;
		}

		/**
		 * True if this event was fired because the player died.
		 * False if it was fired because the entity switched dimensions.
		 */
		public boolean isWasDeath()
		{
			return wasDeath;
		}
	}

	/**
	 * Fired when an Entity is started to be "tracked" by this player (the player receives updates about this entity, e.g. motion).
	 *
	 */
	public static class StartTracking extends PlayerEvent {

		private final Entity target;

		public StartTracking(PlayerEntity player, Entity target)
		{
			super(player);
			this.target = target;
		}

		/**
		 * The Entity now being tracked.
		 */
		public Entity getTarget()
		{
			return target;
		}
	}

	/**
	 * Fired when an Entity is stopped to be "tracked" by this player (the player no longer receives updates about this entity, e.g. motion).
	 *
	 */
	public static class StopTracking extends PlayerEvent {

		private final Entity target;

		public StopTracking(PlayerEntity player, Entity target)
		{
			super(player);
			this.target = target;
		}

		/**
		 * The Entity no longer being tracked.
		 */
		public Entity getTarget()
		{
			return target;
		}
	}

	/**
	 * The player is being loaded from the world save. Note that the
	 * player won't have been added to the world yet. Intended to
	 * allow mods to load an additional file from the players directory
	 * containing additional mod related player data.
	 */
	public static class LoadFromFile extends PlayerEvent {
		private final File playerDirectory;
		private final String playerUUID;

		public LoadFromFile(PlayerEntity player, File originDirectory, String playerUUID)
		{
			super(player);
			this.playerDirectory = originDirectory;
			this.playerUUID = playerUUID;
		}

		/**
		 * Construct and return a recommended file for the supplied suffix
		 * @param suffix The suffix to use.
		 */
		public File getPlayerFile(String suffix)
		{
			if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
			return new File(this.getPlayerDirectory(), this.getPlayerUUID() +"."+suffix);
		}

		/**
		 * The directory where player data is being stored. Use this
		 * to locate your mod additional file.
		 */
		public File getPlayerDirectory()
		{
			return playerDirectory;
		}

		/**
		 * The UUID is the standard for player related file storage.
		 * It is broken out here for convenience for quick file generation.
		 */
		public String getPlayerUUID()
		{
			return playerUUID;
		}
	}
	/**
	 * The player is being saved to the world store. Note that the
	 * player may be in the process of logging out or otherwise departing
	 * from the world. Don't assume it's association with the world.
	 * This allows mods to load an additional file from the players directory
	 * containing additional mod related player data.
	 * <br>
	 * Use this event to save the additional mod related player data to the world.
	 *
	 * <br>
	 * <em>WARNING</em>: Do not overwrite the player's .dat file here. You will
	 * corrupt the world state.
	 */
	public static class SaveToFile extends PlayerEvent {
		private final File playerDirectory;
		private final String playerUUID;

		public SaveToFile(PlayerEntity player, File originDirectory, String playerUUID)
		{
			super(player);
			this.playerDirectory = originDirectory;
			this.playerUUID = playerUUID;
		}

		/**
		 * Construct and return a recommended file for the supplied suffix
		 * @param suffix The suffix to use.
		 */
		public File getPlayerFile(String suffix)
		{
			if ("dat".equals(suffix)) throw new IllegalArgumentException("The suffix 'dat' is reserved");
			return new File(this.getPlayerDirectory(), this.getPlayerUUID() +"."+suffix);
		}

		/**
		 * The directory where player data is being stored. Use this
		 * to locate your mod additional file.
		 */
		public File getPlayerDirectory()
		{
			return playerDirectory;
		}

		/**
		 * The UUID is the standard for player related file storage.
		 * It is broken out here for convenience for quick file generation.
		 */
		public String getPlayerUUID()
		{
			return playerUUID;
		}
	}

	public static class ItemPickupEvent extends PlayerEvent {
		/**
		 * Original EntityItem with current remaining stack size
		 */
		private final ItemEntity originalEntity;
		/**
		 * Clone item stack, containing the item and amount picked up
		 */
		private final ItemStack stack;
		public ItemPickupEvent(PlayerEntity player, ItemEntity entPickedUp, ItemStack stack)
		{
			super(player);
			this.originalEntity = entPickedUp;
			this.stack = stack;
		}

		public ItemStack getStack() {
			return stack;
		}

		public ItemEntity getOriginalEntity() {
			return originalEntity;
		}
	}

	public static class ItemCraftedEvent extends PlayerEvent {
		@NotNull
		private final ItemStack crafting;
		private final CraftingInventory craftMatrix;
		public ItemCraftedEvent(PlayerEntity player, @NotNull ItemStack crafting, CraftingInventory craftMatrix)
		{
			super(player);
			this.crafting = crafting;
			this.craftMatrix = craftMatrix;
		}

		@NotNull
		public ItemStack getCrafting()
		{
			return this.crafting;
		}

		public CraftingInventory getInventory()
		{
			return this.craftMatrix;
		}
	}

	public static class ItemSmeltedEvent extends PlayerEvent {
		@NotNull
		private final ItemStack smelting;
		public ItemSmeltedEvent(PlayerEntity player, @NotNull ItemStack crafting)
		{
			super(player);
			this.smelting = crafting;
		}

		@NotNull
		public ItemStack getSmelting()
		{
			return this.smelting;
		}
	}

	public static class PlayerLoggedInEvent extends PlayerEvent {
		public PlayerLoggedInEvent(PlayerEntity player)
		{
			super(player);
		}
	}

	public static class PlayerLoggedOutEvent extends PlayerEvent {
		public PlayerLoggedOutEvent(PlayerEntity player)
		{
			super(player);
		}
	}

	public static class PlayerRespawnEvent extends PlayerEvent {
		private final boolean endConquered;

		public PlayerRespawnEvent(PlayerEntity player, boolean endConquered)
		{
			super(player);
			this.endConquered = endConquered;
		}

		/**
		 * Did this respawn event come from the player conquering the end?
		 * @return if this respawn was because the player conquered the end
		 */
		public boolean isEndConquered()
		{
			return this.endConquered;
		}


	}

	public static class PlayerChangedDimensionEvent extends PlayerEvent {
		private final RegistryKey<World> fromDim;
		private final RegistryKey<World> toDim;
		public PlayerChangedDimensionEvent(PlayerEntity player, RegistryKey<World> fromDim, RegistryKey<World> toDim)
		{
			super(player);
			this.fromDim = fromDim;
			this.toDim = toDim;
		}

		public RegistryKey<World> getFrom()
		{
			return this.fromDim;
		}

		public RegistryKey<World> getTo()
		{
			return this.toDim;
		}
	}

	/**
	 * Fired when the game type of a server player is changed to a different value than what it was previously. Eg Creative to Survival, not Survival to Survival.
	 * If the event is cancelled the game mode of the player is not changed and the value of <code>newGameMode</code> is ignored.
	 */
	public static class PlayerChangeGameModeEvent extends PlayerEvent
	{
		private final GameMode currentGameMode;
		private GameMode newGameMode;

		public PlayerChangeGameModeEvent(PlayerEntity player, GameMode currentGameMode, GameMode newGameMode)
		{
			super(player);
			this.currentGameMode = currentGameMode;
			this.newGameMode = newGameMode;
		}

		public GameMode getCurrentGameMode()
		{
			return currentGameMode;
		}

		public GameMode getNewGameMode()
		{
			return newGameMode;
		}

		/**
		 * Sets the game mode the player will be changed to if this event is not cancelled.
		 */
		public void setNewGameMode(GameMode newGameMode)
		{
			this.newGameMode = newGameMode;
		}
	}
}