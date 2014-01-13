package cpw.mods.fml.common.network;

import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet131MapData;

public interface ITinyPacketHandler
{
    void handle(NetHandler var1, Packet131MapData var2);
}
