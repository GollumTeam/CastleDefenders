package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.network.IChatListener;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet3Chat;

public class ModLoaderChatListener implements IChatListener
{
    private BaseModProxy mod;

    public ModLoaderChatListener(BaseModProxy var1)
    {
        this.mod = var1;
    }

    public Packet3Chat serverChat(NetHandler var1, Packet3Chat var2)
    {
        this.mod.serverChat((NetServerHandler)var1, var2.message);
        return var2;
    }

    public Packet3Chat clientChat(NetHandler var1, Packet3Chat var2)
    {
        this.mod.clientChat(var2.message);
        return var2;
    }
}
