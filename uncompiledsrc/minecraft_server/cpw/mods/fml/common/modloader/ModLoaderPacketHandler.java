package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ModLoaderPacketHandler implements IPacketHandler
{
    private BaseModProxy mod;

    public ModLoaderPacketHandler(BaseModProxy var1)
    {
        this.mod = var1;
    }

    public void onPacketData(INetworkManager var1, Packet250CustomPayload var2, Player var3)
    {
        if (var3 instanceof EntityPlayerMP)
        {
            this.mod.serverCustomPayload(((EntityPlayerMP)var3).playerNetServerHandler, var2);
        }
        else
        {
            ModLoaderHelper.sidedHelper.sendClientPacket(this.mod, var2);
        }
    }
}
