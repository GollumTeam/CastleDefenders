package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.common.registry.EntityRegistry$EntityRegistration;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;

public interface IModLoaderSidedHelper
{
    void finishModLoading(ModLoaderModContainer var1);

    Object getClientGui(BaseModProxy var1, EntityPlayer var2, int var3, int var4, int var5, int var6);

    Entity spawnEntity(BaseModProxy var1, EntitySpawnPacket var2, EntityRegistry$EntityRegistration var3);

    void sendClientPacket(BaseModProxy var1, Packet250CustomPayload var2);

    void clientConnectionOpened(NetHandler var1, INetworkManager var2, BaseModProxy var3);

    boolean clientConnectionClosed(INetworkManager var1, BaseModProxy var2);
}
