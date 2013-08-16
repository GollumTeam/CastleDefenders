package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

public class ModLoaderConnectionHandler implements IConnectionHandler
{
    private BaseModProxy mod;

    public ModLoaderConnectionHandler(BaseModProxy var1)
    {
        this.mod = var1;
    }

    public void playerLoggedIn(Player var1, NetHandler var2, INetworkManager var3)
    {
        this.mod.onClientLogin((EntityPlayer)var1);
    }

    public String connectionReceived(NetLoginHandler var1, INetworkManager var2)
    {
        return null;
    }

    public void connectionOpened(NetHandler var1, String var2, int var3, INetworkManager var4)
    {
        ModLoaderHelper.sidedHelper.clientConnectionOpened(var1, var4, this.mod);
    }

    public void connectionClosed(INetworkManager var1)
    {
        if (ModLoaderHelper.sidedHelper == null || !ModLoaderHelper.sidedHelper.clientConnectionClosed(var1, this.mod))
        {
            this.mod.serverDisconnect();
            this.mod.onClientLogout(var1);
        }
    }

    public void clientLoggedIn(NetHandler var1, INetworkManager var2, Packet1Login var3)
    {
        this.mod.serverConnect(var1);
    }

    public void connectionOpened(NetHandler var1, MinecraftServer var2, INetworkManager var3)
    {
        ModLoaderHelper.sidedHelper.clientConnectionOpened(var1, var3, this.mod);
    }
}
