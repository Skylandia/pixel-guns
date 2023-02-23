package com.ultreon.mods.pixelguns.client.handler;

import com.ultreon.mods.pixelguns.config.PixelGunsConfig;
import com.ultreon.mods.pixelguns.event.GunFireEvent;
import com.ultreon.mods.pixelguns.event.forge.TickEvent;
import com.ultreon.mods.pixelguns.item.gun.GunItem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class RecoilHandler
{
	private static final Random random = new Random();
	private static double gunRecoilNormal;
	private static double gunRecoilAngle;
	private static float gunRecoilRandom;
	private static float cameraRecoil;
	private static float progressCameraRecoil;

	public static void onGunFire(GunFireEvent.Post event)
	{
		if (!event.isClient())
			return;

		if (!PixelGunsConfig.enable_recoil)
			return;

		ItemStack heldItem = event.getStack();
		GunItem gunItem = (GunItem) heldItem.getItem();
		cameraRecoil = gunItem.getRecoil() * getAdsRecoilReduction();
		progressCameraRecoil = 0F;
		gunRecoilRandom = random.nextFloat();
	}

	public static void onRenderTick(TickEvent.RenderTickEvent event)
	{
		if (event.phase != TickEvent.Phase.END || cameraRecoil <= 0)
			return;

		MinecraftClient mc = MinecraftClient.getInstance();
		if (mc.player == null)
			return;

		if (!PixelGunsConfig.enable_recoil)
			return;

		float recoilAmount = cameraRecoil * mc.getTickDelta() * 0.15F;
		float startProgress = progressCameraRecoil / cameraRecoil;
		float endProgress = (progressCameraRecoil + recoilAmount) / cameraRecoil;

		float pitch = mc.player.getPitch();
		if (startProgress < 0.2F)
		{
			mc.player.setPitch(pitch - ((endProgress - startProgress) / 0.2F) * cameraRecoil);
		}
		else
		{
			mc.player.setPitch(pitch + ((endProgress - startProgress) / 0.8F) * cameraRecoil);
		}

		progressCameraRecoil += recoilAmount;

		if (progressCameraRecoil >= cameraRecoil)
		{
			cameraRecoil = 0;
			progressCameraRecoil = 0;
		}
	}

	public static float getAdsRecoilReduction()
	{
		return MinecraftClient.getInstance().options.useKey.isPressed() ? 0.5f : 1.0f;
	}

	public static double getGunRecoilNormal()
	{
		return gunRecoilNormal;
	}

	public static double getGunRecoilAngle()
	{
		return gunRecoilAngle;
	}

	public static float getGunRecoilRandom()
	{
		return gunRecoilRandom;
	}
}