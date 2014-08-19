package forge.packets;

import forge.IPacketHandler;
import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet;

public abstract class PacketHandlerBase implements IPacketHandler
{
    public static boolean DEBUG = false;

    public PacketHandlerBase()
    {
    }

    public abstract void sendPacket(NetworkManager networkmanager, Packet packet);
}
