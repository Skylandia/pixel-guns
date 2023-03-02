package com.ultreon.mods.pixelguns.registry;

import com.ultreon.mods.pixelguns.network.packet.c2s.play.BowFireC2SPacket;
import com.ultreon.mods.pixelguns.network.packet.c2s.play.GunReloadC2SPacket;
import com.ultreon.mods.pixelguns.network.packet.c2s.play.GunShootC2SPacket;
import com.ultreon.mods.pixelguns.network.packet.c2s.play.WorkshopCraftC2SPacket;
import com.ultreon.mods.pixelguns.network.packet.s2c.play.GunRecoilS2CPacket;
import com.ultreon.mods.pixelguns.util.ResourcePath;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class PacketRegistry {
    public static final Identifier GUN_RECOIL = ResourcePath.get("recoil");
    public static final Identifier GUN_RELOAD = ResourcePath.get("reload");
    public static final Identifier GUN_SHOOT = ResourcePath.get("shoot");
    public static final Identifier GUN_AIM = ResourcePath.get("aim");
    public static final Identifier BOW_SHOOT = ResourcePath.get("bow_shoot");
    public static final Identifier WORKSHOP_ASSEMBLE = ResourcePath.get("assemble");

    public static class CLIENT {
        public static void registerPackets() {
            PacketRegistry.CLIENT.registerPacket(GUN_RECOIL, new GunRecoilS2CPacket());
        }


        private static void registerPacket(Identifier id, ClientPlayNetworking.PlayChannelHandler packetHandler) {
            ClientPlayNetworking.registerGlobalReceiver(id, packetHandler);
        }
    }

    public static class SERVER {

        public static void registerPackets() {
            PacketRegistry.SERVER.registerPacket(GUN_RELOAD, new GunReloadC2SPacket());
            PacketRegistry.SERVER.registerPacket(GUN_SHOOT, new GunShootC2SPacket());
            PacketRegistry.SERVER.registerPacket(BOW_SHOOT, new BowFireC2SPacket());
            PacketRegistry.SERVER.registerPacket(WORKSHOP_ASSEMBLE, new WorkshopCraftC2SPacket());
        }
        private static void registerPacket(Identifier id, ServerPlayNetworking.PlayChannelHandler packetHandler) {
            ServerPlayNetworking.registerGlobalReceiver(id, packetHandler);
        }
    }






}
