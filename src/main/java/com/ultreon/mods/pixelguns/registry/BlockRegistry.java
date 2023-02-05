package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.block.WorkshopBlock;
import com.ultreon.mods.pixelguns.util.ResourcePath;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.registry.Registry;

public class BlockRegistry {
    public static final Block WORKSHOP;

    static {
        WORKSHOP = BlockRegistry.register("workshop", new WorkshopBlock());
    }

    private static Block register(String name, Block block) {
        return Registry.register(Registry.BLOCK, ResourcePath.get(name), block);
    }

    @Environment(value = EnvType.CLIENT)
    public static class RENDERER {
        public static void registerBlockRenderers() {
            RENDERER.registerBlockRenderer(BlockRegistry.WORKSHOP, RenderLayer.getCutout());
        }

        private static void registerBlockRenderer(Block block, RenderLayer renderLayer) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer);
        }
    }
}