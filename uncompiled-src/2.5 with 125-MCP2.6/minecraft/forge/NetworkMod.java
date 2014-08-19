package forge;

import net.minecraft.src.BaseMod;
import net.minecraft.src.NetworkManager;

public abstract class NetworkMod extends BaseMod
{
    public NetworkMod()
    {
    }

    public boolean clientSideRequired()
    {
        return false;
    }

    public boolean serverSideRequired()
    {
        return false;
    }

    public void onPacketData(NetworkManager networkmanager, short word0, byte abyte0[])
    {
    }
}
