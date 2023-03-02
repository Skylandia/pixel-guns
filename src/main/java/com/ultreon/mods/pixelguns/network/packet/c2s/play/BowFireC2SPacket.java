package com.ultreon.mods.pixelguns.network.packet.c2s.play;

import com.ultreon.mods.pixelguns.item.JujuBowItem;
import com.ultreon.mods.pixelguns.registry.PacketRegistry;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class BowFireC2SPacket implements ServerPlayNetworking.PlayChannelHandler {
	public static void send() {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

		ClientPlayNetworking.send(PacketRegistry.BOW_SHOOT, buf);
	}
	@Override
	public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
		ItemStack itemStack = player.getMainHandStack();
		if (itemStack.getItem() instanceof JujuBowItem bowItem) {
			bowItem.instantFullPowerShot(itemStack, player.world, player);
		}

	}
}
