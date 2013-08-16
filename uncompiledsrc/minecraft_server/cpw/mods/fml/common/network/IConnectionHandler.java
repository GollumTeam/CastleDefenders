package cpw.mods.fml.common.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

public interface IConnectionHandler
{
    void playerLoggedIn(Player var1, NetHandler var2, INetworkManager var3);

    String connectionReceived(NetLoginHandler var1, INetworkManager var2);

    void connectionOpened(NetHandler var1, String var2, int var3, INetworkManager var4);

    void connectionOpened(NetHandler var1, MinecraftServer var2, INetworkManager var3);

    void connectionClosed(INetworkManager var1);

    void clientLoggedIn(NetHandler var1, INetworkManager var2, Packet1Login var3);
}
