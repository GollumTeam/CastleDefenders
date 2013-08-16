package cpw.mods.fml.common.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

public class PacketDispatcher
{
    public static Packet250CustomPayload getPacket(String var0, byte[] var1)
    {
        return new Packet250CustomPayload(var0, var1);
    }

    public static void sendPacketToServer(Packet var0)
    {
        FMLCommonHandler.instance().getSidedDelegate().sendPacket(var0);
    }

    public static void sendPacketToPlayer(Packet var0, Player var1)
    {
        if (var1 instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)var1).playerNetServerHandler.sendPacketToPlayer(var0);
        }
    }

    public static void sendPacketToAllAround(double var0, double var2, double var4, double var6, int var8, Packet var9)
    {
        MinecraftServer var10 = FMLCommonHandler.instance().getMinecraftServerInstance();

        if (var10 != null)
        {
            var10.getConfigurationManager().sendToAllNear(var0, var2, var4, var6, var8, var9);
        }
        else
        {
            FMLLog.fine("Attempt to send packet to all around without a server instance available", new Object[0]);
        }
    }

    public static void sendPacketToAllInDimension(Packet var0, int var1)
    {
        MinecraftServer var2 = FMLCommonHandler.instance().getMinecraftServerInstance();

        if (var2 != null)
        {
            var2.getConfigurationManager().sendPacketToAllPlayersInDimension(var0, var1);
        }
        else
        {
            FMLLog.fine("Attempt to send packet to all in dimension without a server instance available", new Object[0]);
        }
    }

    public static void sendPacketToAllPlayers(Packet var0)
    {
        MinecraftServer var1 = FMLCommonHandler.instance().getMinecraftServerInstance();

        if (var1 != null)
        {
            var1.getConfigurationManager().sendPacketToAllPlayers(var0);
        }
        else
        {
            FMLLog.fine("Attempt to send packet to all in dimension without a server instance available", new Object[0]);
        }
    }

    public static Packet131MapData getTinyPacket(Object var0, short var1, byte[] var2)
    {
        NetworkModHandler var3 = FMLNetworkHandler.instance().findNetworkModHandler(var0);
        return new Packet131MapData((short)var3.getNetworkId(), var1, var2);
    }
}
