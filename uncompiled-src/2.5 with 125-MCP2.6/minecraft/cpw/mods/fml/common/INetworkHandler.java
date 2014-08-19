package cpw.mods.fml.common;

public interface INetworkHandler
{
    public abstract boolean onChat(Object aobj[]);

    public abstract void onPacket250Packet(Object aobj[]);

    public abstract void onServerLogin(Object obj);
}
