package com.ultreon.mods.pixelguns.network.packet.s2c.play;

import com.ultreon.mods.pixelguns.Config;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class GunRecoilS2CPacket implements ClientPlayNetworking.PlayChannelHandler {
    @Override
    public void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        float kick = buf.readFloat();
        client.execute(() -> {
            if (client.player != null) {
                if (Config.DO_RECOIL.get()) {
                    client.player.setPitch(kick);
                }
            }
        });
    }
}
