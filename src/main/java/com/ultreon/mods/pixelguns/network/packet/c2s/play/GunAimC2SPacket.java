package com.ultreon.mods.pixelguns.network.packet.c2s.play;

import com.ultreon.mods.pixelguns.item.gun.GunItem;
import com.ultreon.mods.pixelguns.registry.PacketRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class GunAimC2SPacket implements ServerPlayNetworking.PlayChannelHandler {
    public static void send(boolean aiming) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBoolean(aiming);

        ClientPlayNetworking.send(PacketRegistry.GUN_AIM, buf);
    }
    @Override
    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (player.getMainHandStack().getItem() instanceof GunItem) {
            if (GunItem.isLoaded(player.getMainHandStack())) {
                player.getMainHandStack().getOrCreateNbt().putBoolean("isAiming", buf.readBoolean());
            } else {
                player.getMainHandStack().getOrCreateNbt().putBoolean("isAiming", false);
            }
        }
    }
}
