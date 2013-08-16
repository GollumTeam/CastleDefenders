package cpw.mods.fml.common.network;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public interface IPacketHandler
{
    void onPacketData(INetworkManager var1, Packet250CustomPayload var2, Player var3);
}
