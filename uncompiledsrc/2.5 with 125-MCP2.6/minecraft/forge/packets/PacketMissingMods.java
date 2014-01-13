package forge.packets;

public class PacketMissingMods extends PacketModList
{
    public PacketMissingMods(boolean flag)
    {
        super(!flag);
    }

    public int getID()
    {
        return 3;
    }
}
