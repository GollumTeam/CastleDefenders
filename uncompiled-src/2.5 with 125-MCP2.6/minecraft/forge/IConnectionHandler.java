package forge;

import net.minecraft.src.NetworkManager;
import net.minecraft.src.Packet1Login;

public interface IConnectionHandler
{
    public abstract void onConnect(NetworkManager networkmanager);

    public abstract void onLogin(NetworkManager networkmanager, Packet1Login packet1login);

    public abstract void onDisconnect(NetworkManager networkmanager, String s, Object aobj[]);
}
