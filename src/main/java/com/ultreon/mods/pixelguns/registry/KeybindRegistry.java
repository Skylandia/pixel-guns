package com.ultreon.mods.pixelguns.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(value = EnvType.CLIENT)
public class KeybindRegistry {
    public static final KeyBinding reload = new KeyBinding("key.pixel_guns.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.pixel_guns.binds");

    public static void registerKeybinds() {
        KeyBindingHelper.registerKeyBinding(reload);
    }
}
