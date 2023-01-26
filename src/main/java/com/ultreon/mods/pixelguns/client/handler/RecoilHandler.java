package com.ultreon.mods.pixelguns.client.handler;

import com.ultreon.mods.pixelguns.Config;

import net.minecraft.client.MinecraftClient;

public class RecoilHandler
{
	private static RecoilHandler instance;

	public static RecoilHandler get()
	{
		if (instance == null)
		{
			instance = new RecoilHandler();
		}
		return instance;
	}

	private float cameraRecoil;
	private float progressCameraRecoil;

	private RecoilHandler() {}

	public void onGunFire()
	{

		if (!Config.DO_RECOIL.get())
			return;

		this.cameraRecoil = 10.0f;
		this.progressCameraRecoil = 0F;
	}

	public void onRenderTick()
	{
		if (this.cameraRecoil <= 0)
			return;

		MinecraftClient mc = MinecraftClient.getInstance();
		if (mc.player == null)
			return;

		if (!Config.DO_RECOIL.get())
			return;

		float recoilAmount = this.cameraRecoil * mc.getTickDelta() * 0.15F;
		float startProgress = this.progressCameraRecoil / this.cameraRecoil;
		float endProgress = (this.progressCameraRecoil + recoilAmount) / this.cameraRecoil;

		float pitch = mc.player.getPitch();
		if (startProgress < 0.2F)
		{
			mc.player.setPitch(pitch - ((endProgress - startProgress) / 0.2F) * this.cameraRecoil);
		}
		else
		{
			mc.player.setPitch(pitch + ((endProgress - startProgress) / 0.8F) * this.cameraRecoil);
		}

		this.progressCameraRecoil += recoilAmount;

		if (this.progressCameraRecoil >= this.cameraRecoil)
		{
			this.cameraRecoil = 0;
			this.progressCameraRecoil = 0;
		}
	}
}