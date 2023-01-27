package com.ultreon.mods.pixelguns.event.forge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.ChunkSectionPos;

public class EntityEvent extends Event
{
	private final Entity entity;

	public EntityEvent(Entity entity)
	{
		this.entity = entity;
	}

	public Entity getEntity()
	{
		return entity;
	}

	public static class EntityConstructing extends EntityEvent
	{
		public EntityConstructing(Entity entity)
		{
			super(entity);
		}
	}

	public static class EnteringSection extends EntityEvent
	{

		private final long packedOldPos;
		private final long packedNewPos;

		public EnteringSection(Entity entity, long packedOldPos, long packedNewPos)
		{
			super(entity);
			this.packedOldPos = packedOldPos;
			this.packedNewPos = packedNewPos;
		}

		public long getPackedOldPos()
		{
			return packedOldPos;
		}

		public long getPackedNewPos()
		{
			return packedNewPos;
		}

		/**
		 * @return the position of the old section
		 */
		public ChunkSectionPos getOldPos()
		{
			return ChunkSectionPos.from(packedOldPos);
		}

		/**
		 * @return the position of the new section
		 */
		public ChunkSectionPos getNewPos()
		{
			return ChunkSectionPos.from(packedNewPos);
		}

		/**
		 * Whether the chunk has changed as part of this event. If this method returns false, only the Y position of the
		 * section has changed.
		 */
		public boolean didChunkChange()
		{
			return ChunkSectionPos.unpackX(packedOldPos) != ChunkSectionPos.unpackX(packedNewPos) || ChunkSectionPos.unpackZ(packedOldPos) != ChunkSectionPos.unpackZ(packedNewPos);
		}

	}

	public static class Size extends EntityEvent
	{
		private final EntityPose pose;
		private final EntityDimensions oldSize;
		private EntityDimensions newSize;
		private final float oldEyeHeight;
		private float newEyeHeight;

		public Size(Entity entity, EntityPose pose, EntityDimensions size, float defaultEyeHeight)
		{
			this(entity, pose, size, size, defaultEyeHeight, defaultEyeHeight);
		}

		public Size(Entity entity, EntityPose pose, EntityDimensions oldSize, EntityDimensions newSize, float oldEyeHeight, float newEyeHeight)
		{
			super(entity);
			this.pose = pose;
			this.oldSize = oldSize;
			this.newSize = newSize;
			this.oldEyeHeight = oldEyeHeight;
			this.newEyeHeight = newEyeHeight;
		}


		public EntityPose getPose() { return pose; }
		public EntityDimensions getOldSize() { return oldSize; }
		public EntityDimensions getNewSize() { return newSize; }
		public void setNewSize(EntityDimensions size)
		{
			setNewSize(size, false);
		}

		/**
		 * Set the new size of the entity. Set updateEyeHeight to true to also update the eye height according to the new size.
		 */
		public void setNewSize(EntityDimensions size, boolean updateEyeHeight)
		{
			this.newSize = size;
			if (updateEyeHeight)
			{
				this.newEyeHeight = size.height * 0.85F;
			}
		}
		public float getOldEyeHeight() { return oldEyeHeight; }
		public float getNewEyeHeight() { return newEyeHeight; }
		public void setNewEyeHeight(float newHeight) { this.newEyeHeight = newHeight; }
	}
}
