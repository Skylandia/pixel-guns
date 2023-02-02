package com.ultreon.mods.pixelguns.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import com.ultreon.mods.pixelguns.client.util.RenderUtil;
import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.util.ResourcePath;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import org.lwjgl.opengl.GL11;

public class WorkshopScreen extends HandledScreen<WorkshopScreenHandler> {
    private static final Identifier TEXTURE = ResourcePath.get("textures/gui/container/workshop.png");

    private PlayerInventory playerInventory;

    private ItemStack displayStack;

    public WorkshopScreen(WorkshopScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        this.playerInventory = playerInventory;
        this.backgroundWidth = 275;
        this.backgroundHeight = 184;

        this.displayStack = ItemRegistry.HEAVY_ASSAULT_RIFLE.getDefaultStack();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float partialTicks, int mouseX, int mouseY) {

        /* Fixes partial ticks to use percentage from 0 to 1 */
        partialTicks = MinecraftClient.getInstance().getTickDelta();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        ItemStack currentItem = this.displayStack;

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.scissor(x + 8, y + 17, 160, 70);

        MatrixStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.push();
        {
            modelViewStack.translate(x + 88, y + 60, 100);
            modelViewStack.scale(50F, -50F, 50F);
            modelViewStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(5F));
            modelViewStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MinecraftClient.getInstance().player.age + partialTicks));
            RenderSystem.applyModelViewMatrix();
            VertexConsumerProvider.Immediate buffer = this.client.getBufferBuilders().getEntityVertexConsumers();
            MinecraftClient.getInstance().getItemRenderer().renderItem(currentItem, ModelTransformation.Mode.FIXED, false, matrices, buffer, 15728880, OverlayTexture.DEFAULT_UV, RenderUtil.getModel(currentItem));
            buffer.draw();
        }
        modelViewStack.pop();
        RenderSystem.applyModelViewMatrix();

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}