package cpw.mods.fml.common;

public interface INetworkHandler
{
    boolean onChat(Object ... var1);

    void onPacket250Packet(Object ... var1);

    void onServerLogin(Object var1);
}
