package com.ultreon.mods.pixelguns.util;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.block.Block;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import org.joml.*;
import org.lwjgl.opengl.GL11;

import java.lang.Math;
import java.lang.reflect.Field;
import java.util.List;

public class RenderUtil
{
	public static void scissor(int x, int y, int width, int height)
	{
		MinecraftClient mc = MinecraftClient.getInstance();
		int scale = (int) mc.getWindow().getScaleFactor();
		GL11.glScissor(x * scale, mc.getWindow().getHeight() - y * scale - height * scale, Math.max(0, width * scale), Math.max(0, height * scale));
	}

	public static BakedModel getModel(Item item)
	{
		return MinecraftClient.getInstance().getItemRenderer().getModels().getModel(new ItemStack(item));
	}

	public static BakedModel getModel(ItemStack item)
	{
		return MinecraftClient.getInstance().getItemRenderer().getModels().getModel(item);
	}

	public static void rotateZ(MatrixStack MatrixStack, float xOffset, float yOffset, float rotation)
	{
		MatrixStack.translate(xOffset, yOffset, 0);
		MatrixStack.multiply(new Quaternionf(new AxisAngle4f(rotation, new Vector3f(0.0F, 0.0F, -1.0F))));
//		MatrixStack.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(rotation));
		MatrixStack.translate(-xOffset, -yOffset, 0);
	}

	public static void renderGun(ItemStack stack, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay, @Nullable LivingEntity entity)
	{
		renderModel(stack, ModelTransformation.Mode.NONE, MatrixStack, buffer, light, overlay, entity);
	}

	public static void renderModel(ItemStack child, ItemStack parent, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay)
	{
		BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(child);
		renderModel(model, ModelTransformation.Mode.NONE, null, child, parent, MatrixStack, buffer, light, overlay);
	}

	public static void renderModel(ItemStack stack, ModelTransformation.Mode transformType, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay, @Nullable LivingEntity entity)
	{
		BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(stack);
		if(entity != null)
		{
			model = MinecraftClient.getInstance().getItemRenderer().getModel(stack, entity.world, entity, 0);
		}
		renderModel(model, transformType, stack, MatrixStack, buffer, light, overlay);
	}

	public static void renderModel(ItemStack stack, ModelTransformation.Mode transformType, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay, @Nullable World world, @Nullable LivingEntity entity)
	{
		BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModel(stack, world, entity, 0);
		renderModel(model, transformType, stack, MatrixStack, buffer, light, overlay);
	}

	public static void renderModel(BakedModel model, ItemStack stack, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay)
	{
		renderModel(model, ModelTransformation.Mode.NONE, stack, MatrixStack, buffer, light, overlay);
	}

	public static void renderModel(BakedModel model, ModelTransformation.Mode transformType, ItemStack stack, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay)
	{
		renderModel(model, transformType, null, stack, ItemStack.EMPTY, MatrixStack, buffer, light, overlay);
	}

	public static void renderModel(BakedModel model, ModelTransformation.Mode transformType, @Nullable Transform transform, ItemStack stack, ItemStack parent, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay)
	{
		if(!stack.isEmpty())
		{
			MatrixStack.push();
			boolean flag = transformType == ModelTransformation.Mode.GUI || transformType == ModelTransformation.Mode.GROUND || transformType == ModelTransformation.Mode.FIXED;
			if(stack.getItem() == Items.TRIDENT && flag)
			{
				model = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier("minecraft:trident"), "inventory"));
			}

			// model = net.MinecraftClientforge.client.ForgeHooksClient.handleCameraTransforms(MatrixStack, model, transformType, false);
			MatrixStack.translate(-0.5D, -0.5D, -0.5D);
			if(!model.isBuiltin() && (stack.getItem() != Items.TRIDENT || flag))
			{
				boolean entity = true;
				if(transformType != ModelTransformation.Mode.GUI && !transformType.isFirstPerson() && stack.getItem() instanceof BlockItem)
				{
					Block block = ((BlockItem) stack.getItem()).getBlock();
					entity = !(block instanceof TransparentBlock) && !(block instanceof StainedGlassPaneBlock);
				}

				RenderLayer RenderLayer = getRenderLayer(stack, entity);
				VertexConsumer builder;
				if(stack.getItem() == Items.COMPASS && stack.hasGlint())
				{
					MatrixStack.push();
					MatrixStack.Entry entry = MatrixStack.peek();
					if(transformType == ModelTransformation.Mode.GUI)
					{
						entry.getPositionMatrix().scale(0.5F);
					}
					else if(transformType.isFirstPerson())
					{
						entry.getPositionMatrix().scale(0.75F);
					}

					if(entity)
					{
						builder = ItemRenderer.getDirectCompassGlintConsumer(buffer, RenderLayer, entry);
					}
					else
					{
						builder = ItemRenderer.getCompassGlintConsumer(buffer, RenderLayer, entry);
					}

					MatrixStack.pop();
				}
				else if(entity)
				{
					builder = ItemRenderer.getDirectItemGlintConsumer(buffer, RenderLayer, true, stack.hasGlint() || parent.hasGlint());
				}
				else
				{
					builder = ItemRenderer.getItemGlintConsumer(buffer, RenderLayer, true, stack.hasGlint() || parent.hasGlint());
				}

				renderModel(model, stack, parent, transform, MatrixStack, builder, light, overlay);
			}
			else
			{
				// IClientItemExtensions.of(stack).getCustomRenderer().renderByItem(stack, transformType, MatrixStack, buffer, light, overlay);
			}

			MatrixStack.pop();
		}
	}

	public static void renderModelWithTransforms(ItemStack child, ItemStack parent, ModelTransformation.Mode transformType, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay)
	{
		MatrixStack.push();
		BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(child);
		// model = net.MinecraftClientforge.client.ForgeHooksClient.handleCameraTransforms(MatrixStack, model, transformType, false);
		MatrixStack.translate(-0.5D, -0.5D, -0.5D);
		renderItemWithoutTransforms(model, child, parent, MatrixStack, buffer, light, overlay);
		MatrixStack.pop();
	}

	public static void renderItemWithoutTransforms(BakedModel model, ItemStack stack, ItemStack parent, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay)
	{
		RenderLayer RenderLayer = getRenderLayer(stack, false);
		VertexConsumer builder = ItemRenderer.getItemGlintConsumer(buffer, RenderLayer, true, stack.hasGlint() || parent.hasGlint());
		renderModel(model, stack, parent, null, MatrixStack, builder, light, overlay);
	}

	public static void renderItemWithoutTransforms(BakedModel model, ItemStack stack, ItemStack parent, MatrixStack MatrixStack, VertexConsumerProvider buffer, int light, int overlay, @Nullable Transform transform)
	{
		RenderLayer RenderLayer = getRenderLayer(stack, false);
		VertexConsumer builder = ItemRenderer.getItemGlintConsumer(buffer, RenderLayer, true, stack.hasGlint() || parent.hasGlint());
		renderModel(model, stack, parent, transform, MatrixStack, builder, light, overlay);
	}

	public static void renderModel(BakedModel model, ItemStack stack, ItemStack parent, @Nullable Transform transform, MatrixStack MatrixStack, VertexConsumer buffer, int light, int overlay)
	{
		if(transform != null)
		{
			transform.apply();
		}
		Random random = Random.create();
		for(Direction direction : Direction.values())
		{
			random.setSeed(42L);
			renderQuads(MatrixStack, buffer, model.getQuads(null, direction, random), stack, parent, light, overlay);
		}
		random.setSeed(42L);
		renderQuads(MatrixStack, buffer, model.getQuads(null, null, random), stack, parent, light, overlay);
	}

	private static void renderQuads(MatrixStack MatrixStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack stack, ItemStack parent, int light, int overlay)
	{
		MatrixStack.Entry entry = MatrixStack.peek();
		for(BakedQuad quad : quads)
		{
			int color = -1;
			if(quad.hasColor())
			{
				color = getItemStackColor(stack, parent, quad.getColorIndex());
			}
			float red = (float) (color >> 16 & 255) / 255.0F;
			float green = (float) (color >> 8 & 255) / 255.0F;
			float blue = (float) (color & 255) / 255.0F;
			buffer.quad(entry, quad, red, green, blue, light, overlay); //TODO check if right
		}
	}

	public static int getItemStackColor(ItemStack stack, ItemStack parent, int tintIndex)
	{
		// TODO not use reflection
		// Student object created
		MinecraftClient e = MinecraftClient.getInstance();

		// Create Field object
		Field privateField = null;
		try {
			privateField = MinecraftClient.class.getDeclaredField("itemColors");
		} catch (NoSuchFieldException ex) {
			throw new RuntimeException(ex);
		}

		// Set the accessibility as true
		privateField.setAccessible(true);

		// Store the value of private field in variable
		ItemColors itemColors = null;
		try {
			itemColors = (ItemColors) privateField.get(e);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}

		int color = itemColors.getColor(stack, tintIndex);
		if(color == -1)
		{
			if(!parent.isEmpty())
			{
				return getItemStackColor(parent, ItemStack.EMPTY, tintIndex);
			}
		}
		return color;
	}

	public static void applyTransformType(ItemStack stack, MatrixStack MatrixStack, ModelTransformation.Mode transformType, @Nullable LivingEntity entity)
	{
		BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModel(stack, entity != null ? entity.world : null, entity, 0);
		boolean leftHanded = transformType == ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND || transformType == ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND;
		// ForgeHooksClient.handleCameraTransforms(MatrixStack, model, transformType, leftHanded);

		/* Flips the model and normals if left handed. */
		if(leftHanded)
		{
			Matrix4f scale = new Matrix4f().scale(-1, 1, 1);
			Matrix3f normal = new Matrix3f(scale);
			MatrixStack.peek().getPositionMatrix().mul(scale);
			MatrixStack.peek().getNormalMatrix().mul(normal);
		}
	}

	public interface Transform
	{
		void apply();
	}

	public static boolean isMouseWithin(int mouseX, int mouseY, int x, int y, int width, int height)
	{
		return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
	}

	public static void renderFirstPersonArm(ClientPlayerEntity player, Arm hand, MatrixStack MatrixStack, VertexConsumerProvider buffer, int combinedLight)
	{
		MinecraftClient mc = MinecraftClient.getInstance();
		EntityRenderDispatcher renderManager = mc.getEntityRenderDispatcher();
		PlayerEntityRenderer renderer = (PlayerEntityRenderer) renderManager.getRenderer(player);
		RenderSystem.setShaderTexture(0, player.getSkinTexture());
		if(hand == Arm.RIGHT)
		{
			renderer.renderRightArm(MatrixStack, buffer, combinedLight, player);
		}
		else
		{
			renderer.renderLeftArm(MatrixStack, buffer, combinedLight, player);
		}
	}

	public static RenderLayer getRenderLayer(ItemStack stack, boolean entity)
	{
		Item item = stack.getItem();
		if(item instanceof BlockItem)
		{
			Block block = ((BlockItem) item).getBlock();
			return RenderLayers.getEntityBlockLayer(block.getDefaultState(), !entity);
		}
		return RenderLayer.getEntityTranslucent(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
	}
}