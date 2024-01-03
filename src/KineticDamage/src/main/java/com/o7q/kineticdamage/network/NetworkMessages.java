package com.o7q.kineticdamage.network;

import com.o7q.kineticdamage.KineticDamage;
import com.o7q.kineticdamage.network.packets.AttackC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class NetworkMessages
{
    public static final Identifier ATTACK_ID = new Identifier(KineticDamage.MOD_ID, "attack");

    public static void registerC2SPackets()
    {
        ServerPlayNetworking.registerGlobalReceiver(ATTACK_ID, AttackC2SPacket::recieve);
    }

    public static void registerS2CPackets()
    {

    }
}