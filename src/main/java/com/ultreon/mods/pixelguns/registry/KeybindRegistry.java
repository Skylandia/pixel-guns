package com.ultreon.mods.pixelguns.registry;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindRegistry {
    public static final KeyBinding shoot = new KeyBinding("key.pixel_guns.shoot", InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_LEFT, "category.pixel_guns.binds");
    public static final KeyBinding aim = new KeyBinding("key.pixel_guns.aim", InputUtil.Type.MOUSE, GLFW.GLFW_MOUSE_BUTTON_RIGHT, "category.pixel_guns.binds");
    public static final KeyBinding reload = new KeyBinding("key.pixel_guns.reload", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.pixel_guns.binds");

    public static void registerKeybinds() {
        KeyBindingHelper.registerKeyBinding(shoot);
        KeyBindingHelper.registerKeyBinding(aim);
        KeyBindingHelper.registerKeyBinding(reload);
    }
}
