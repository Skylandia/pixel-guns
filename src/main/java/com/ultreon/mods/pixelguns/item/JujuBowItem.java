package com.ultreon.mods.pixelguns.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JujuBowItem extends BowItem {
	public JujuBowItem(Settings settings) {
		super(settings);
	}

	public void instantFullPowerShot(ItemStack stack, World world, LivingEntity user) {
		this.onStoppedUsing(stack, world, user, 0);
	}

	@Override
	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return false;
	}
}
