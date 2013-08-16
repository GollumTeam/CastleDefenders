package cpw.mods.fml.common.network;

public @interface NetworkMod$SidedPacketHandler
{
    String[] channels();

    Class packetHandler();
}
