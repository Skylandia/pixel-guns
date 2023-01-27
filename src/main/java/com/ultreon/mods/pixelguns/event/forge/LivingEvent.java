package com.ultreon.mods.pixelguns.event.forge;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class LivingEvent extends EntityEvent
{
	private final LivingEntity livingEntity;

	public LivingEvent(LivingEntity entity)
	{
		super(entity);
		livingEntity = entity;
	}

	@Override
	public LivingEntity getEntity()
	{
		return livingEntity;
	}


	public static class LivingTickEvent extends LivingEvent
	{
		public LivingTickEvent(LivingEntity e){ super(e); }
	}

	public static class LivingJumpEvent extends LivingEvent
	{
		public LivingJumpEvent(LivingEntity e){ super(e); }
	}

	public static class LivingVisibilityEvent extends LivingEvent
	{
		private double visibilityModifier;
		@Nullable
		private final Entity lookingEntity;

		public LivingVisibilityEvent(LivingEntity livingEntity, @Nullable Entity lookingEntity, double originalMultiplier)
		{
			super(livingEntity);
			this.visibilityModifier = originalMultiplier;
			this.lookingEntity = lookingEntity;
		}

		/**
		 * @param mod Is multiplied with the current modifier
		 */
		public void modifyVisibility(double mod)
		{
			visibilityModifier *= mod;
		}

		/**
		 * @return The current modifier
		 */
		public double getVisibilityModifier()
		{
			return visibilityModifier;
		}

		/**
		 * @return The entity trying to see this LivingEntity, if available
		 */
		@Nullable
		public Entity getLookingEntity()
		{
			return lookingEntity;
		}
	}
}
