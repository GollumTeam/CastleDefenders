package forge;

import net.minecraft.src.NetworkManager;

public interface IPacketHandler
{
    public abstract void onPacketData(NetworkManager networkmanager, String s, byte abyte0[]);
}
