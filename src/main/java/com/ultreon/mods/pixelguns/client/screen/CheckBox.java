package com.ultreon.mods.pixelguns.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CheckBox extends ClickableWidget
{
	// TODO update
	private static final Identifier GUI = new Identifier("cgm:textures/gui/Texts.png");

	private boolean toggled = false;

	public CheckBox(int left, int top, Text title)
	{
		super(left, top, 8, 8, title);
	}

	public void setToggled(boolean toggled)
	{
		this.toggled = toggled;
	}

	public boolean isToggled()
	{
		return this.toggled;
	}

	@Override
	public void renderButton(MatrixStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI);
		this.drawTexture(poseStack, this.getX(), this.getY(), 0, 0, 8, 8);
		if(this.toggled)
		{
			this.drawTexture(poseStack, this.getX(), this.getY() - 1, 8, 0, 9, 8);
		}
		drawTextWithShadow(poseStack, MinecraftClient.getInstance().textRenderer, this.getMessage(), this.getX() + 12, this.getY(), 0xFFFFFF);
	}

	@Override
	public void onClick(double mouseX, double mouseY)
	{
		this.toggled = !this.toggled;
	}

	@Override
	protected void appendClickableNarrations(NarrationMessageBuilder narrationMessageBuilder) {
		this.appendDefaultNarrations(narrationMessageBuilder);
	}
}