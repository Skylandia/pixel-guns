package com.ultreon.mods.pixelguns.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import com.ultreon.mods.pixelguns.client.screen.handler.WorkshopScreenHandler;
import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.network.packet.c2s.play.WorkshopCraftC2SPacket;
import com.ultreon.mods.pixelguns.registry.ItemRegistry;
import com.ultreon.mods.pixelguns.registry.TagRegistry;
import com.ultreon.mods.pixelguns.util.InventoryUtil;
import com.ultreon.mods.pixelguns.util.RenderUtil;
import com.ultreon.mods.pixelguns.util.ResourcePath;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WorkshopScreen extends HandledScreen<WorkshopScreenHandler> {

    private static final Identifier TEXTURE = ResourcePath.get("textures/gui/container/workshop.png");

    private Tab currentTab;
    private List<Tab> tabs = new ArrayList<>();
    private List<ItemStack> materials;
//    private List<MaterialItem> filteredMaterials;
    private PlayerInventory playerInventory;
    // private WorkbenchBlockEntity workbench;
    private ButtonWidget btnCraft;
    private CheckBox checkBoxMaterials;
    private ItemStack displayStack;
    private List<ItemStack> guns = new ArrayList<>();
    private List<ItemStack> ammo = new ArrayList<>();
    private List<ItemStack> attachments = new ArrayList<>();

    public WorkshopScreen(WorkshopScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);

        this.playerInventory = playerInventory;

        this.backgroundWidth = 275;
        this.backgroundHeight = 184;

        this.playerInventoryTitleX = this.x + 8;
        this.playerInventoryTitleY = this.y + 91;

        // Populate tabs
        tabs.add(new Tab(new ItemStack(ItemRegistry.ASSAULT_RIFLE)));
        tabs.add(new Tab(new ItemStack(ItemRegistry.STANDARD_RIFLE_BULLET)));
        tabs.add(new Tab(new ItemStack(ItemRegistry.GUN_SCOPE)));

        currentTab = tabs.get(0);

        // Populate guns
        for (RegistryEntry<Item> gun : Registry.ITEM.iterateEntries(TagRegistry.GUNS)) {
            guns.add(gun.comp_349().getDefaultStack());
        }

        // Populate ammo
        for (RegistryEntry<Item> bullet : Registry.ITEM.iterateEntries(TagRegistry.AMMUNITION)) {
            ammo.add(bullet.comp_349().getDefaultStack());
        }

        displayStack = guns.get(0);
        this.materials = List.of(((GunItem) displayStack.getItem()).getIngredients());
    }

    @Override
    protected void init() {
        super.init();

        // Left Arrow
        this.addDrawableChild(new ButtonWidget(this.x + 9, this.y + 18, 15, 20, Text.literal("<"), button -> {
            int nextIndex = guns.indexOf(displayStack) - 1;
            if (nextIndex < 0) nextIndex = guns.size() - 1;
            displayStack = guns.get(nextIndex);
            this.materials = List.of(((GunItem) displayStack.getItem()).getIngredients());
        }));

        // Right Arrow
        this.addDrawableChild(new ButtonWidget(this.x + 153, this.y + 18, 15, 20, Text.literal(">"), button -> {
            int nextIndex = guns.indexOf(displayStack) + 1;
            if (nextIndex >= guns.size()) nextIndex = 0;
            displayStack = guns.get(nextIndex);
            this.materials = List.of(((GunItem) displayStack.getItem()).getIngredients());
        }));

        // Assemble Button
        this.btnCraft = this.addDrawableChild(new ButtonWidget(this.x + 195, this.y + 16, 74, 20, Text.literal("Assemble"), button -> {
            GunItem currentGun = (GunItem) displayStack.getItem();
            WorkshopCraftC2SPacket.send(currentGun.getIngredients(), displayStack);
        }));

        // Disable the Assemble Button
        this.btnCraft.active = false;
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();

        boolean canAssemble = true;

        GunItem gunItem = (GunItem) this.displayStack.getItem();

        for (ItemStack stack : gunItem.getIngredients()) {
            if (InventoryUtil.itemCountInInventory(playerInventory.player, stack.getItem()) < stack.getCount()) {
                canAssemble = false;
                break;
            }
        }

        this.btnCraft.active = canAssemble;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        boolean result = super.mouseClicked(mouseX, mouseY, mouseButton);

        for (int i = 0; i < this.tabs.size(); i++) {
            if (RenderUtil.isMouseWithin((int) mouseX, (int) mouseY, this.x + 28 * i, this.y - 28, 28, 28)) {
                this.currentTab = this.tabs.get(i);
//                this.loadItem(this.currentTab.getCurrentIndex());
                this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                return true;
            }
        }

        return result;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawForeground(MatrixStack matrixStack, int i, int j) {
        this.textRenderer.draw(matrixStack, this.title, this.titleX, this.titleY, 4210752);
        this.textRenderer.draw(matrixStack, this.playerInventory.getDisplayName(), this.playerInventoryTitleX, this.playerInventoryTitleY, 4210752);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.enableBlend();

        renderUnselectedTabs(matrices);
        renderBackgroundTexture(matrices);
        renderSelectedTab(matrices);

        renderCurrentGun(matrices);
        renderGunName(matrices);

        renderIngredients(matrices);
    }

    private void renderSelectedTab(MatrixStack matrices) {
        if (this.currentTab != null)
        {
            int i = this.tabs.indexOf(this.currentTab);
            int u = i == 0 ? 80 : 108;
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TEXTURE);
            this.drawTexture(matrices, this.x + 28 * i, this.y - 28, u, 214, 28, 32);
            MinecraftClient.getInstance().getItemRenderer().renderInGuiWithOverrides(this.currentTab.icon(), this.x + 28 * i + 6, this.y - 28 + 8);
            MinecraftClient.getInstance().getItemRenderer().renderGuiItemOverlay(this.textRenderer, this.currentTab.icon(), this.x + 28 * i + 6, this.y - 28 + 8, null);
        }
    }

    private void renderUnselectedTabs(MatrixStack matrices) {
        for(int i = 0; i < this.tabs.size(); i++)
        {
            Tab tab = this.tabs.get(i);
            if(tab != this.currentTab)
            {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, TEXTURE);
                this.drawTexture(matrices, this.x + 28 * i, this.y - 28, 80, 184, 28, 32);
                MinecraftClient.getInstance().getItemRenderer().renderInGuiWithOverrides(tab.icon(), this.x + 28 * i + 6, this.y - 28 + 8);
                MinecraftClient.getInstance().getItemRenderer().renderGuiItemOverlay(this.textRenderer, tab.icon(), this.x + 28 * i + 6, this.y - 28 + 8, null);
            }
        }
    }

    private void renderIngredients(MatrixStack matrices) {
        for (int i = 0; i < this.materials.size(); i++) {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TEXTURE);

            ItemStack stack = this.materials.get(i);
            if(!stack.isEmpty()) {
                DiffuseLighting.disableGuiDepthLighting();
                if (true/*materialItem.isEnabled()*/) {
                    this.drawTexture(matrices, this.x + 172, this.y + i * 19 + 63, 0, 184, 80, 19);
                } else {
                    this.drawTexture(matrices, this.x + 172, this.y + i * 19 + 63, 0, 222, 80, 19);
                }
                String name = stack.getName().getString();
                if(this.textRenderer.getWidth(name) > 55)
                {
                    name = this.textRenderer.trimToWidth(name, 50).trim() + "...";
                }
                this.textRenderer.draw(matrices, name, this.x + 172 + 22, this.y + i * 19 + 6 + 63, Color.WHITE.getRGB());

                MinecraftClient.getInstance().getItemRenderer().renderInGuiWithOverrides(stack, this.x + 172 + 2, this.y + i * 19 + 1 + 63);

                /*if(this.checkBoxMaterials.isToggled())
                {
                    int count = InventoryUtil.itemCountInInventory(MinecraftClient.getInstance().player, stack.getItem());
                    stack = stack.copy();
                    stack.setCount(stack.getCount() - count);
                }*/

                MinecraftClient.getInstance().getItemRenderer().renderGuiItemOverlay(this.textRenderer, stack, this.x + 172 + 2, this.y + i * 19 + 1 + 63, null);
            }
        }
    }

    private void renderGunName(MatrixStack matrices) {
        this.client.textRenderer.draw(matrices, displayStack.getName(), this.x + 90 - this.client.textRenderer.getWidth(displayStack.getName())/2, this.y + 25, 0xFFFFFF);
    }


    private void renderCurrentGun(MatrixStack matrices) {
        float partialTicks = MinecraftClient.getInstance().getTickDelta();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtil.scissor(x + 8, y + 17, 160, 70);

        MatrixStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.push();

        modelViewStack.translate(x + 88, y + 60, 100);
        modelViewStack.scale(100F, -100F, 100F);
        modelViewStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MinecraftClient.getInstance().player.age + partialTicks));
        modelViewStack.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(30F));
        RenderSystem.applyModelViewMatrix();
        VertexConsumerProvider.Immediate buffer = this.client.getBufferBuilders().getEntityVertexConsumers();
        MinecraftClient.getInstance().getItemRenderer().renderItem(this.displayStack, ModelTransformation.Mode.FIXED, false, matrices, buffer, 15728880, OverlayTexture.DEFAULT_UV, RenderUtil.getModel(this.displayStack));
        buffer.draw();

        modelViewStack.pop();
        RenderSystem.applyModelViewMatrix();

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private void renderBackgroundTexture(MatrixStack matrices) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, 173, 184);
        drawTexture(matrices, x + 173, y, 78, 184, 173, 0, 1, 184, 256, 256);
        drawTexture(matrices, x + 251, y, 174, 0, 24, 184);
        drawTexture(matrices, x + 172, y + 16, 198, 0, 20, 20);
    }

    private record Tab(ItemStack icon) {}
}