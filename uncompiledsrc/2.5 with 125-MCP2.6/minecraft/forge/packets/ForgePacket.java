package forge.packets;

import java.io.*;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;

public abstract class ForgePacket
{
    public static final int FORGE_ID = 0x40e9b47;
    public static final int SPAWN = 1;
    public static final int MODLIST = 2;
    public static final int MOD_MISSING = 3;
    public static final int OPEN_GUI = 5;

    public ForgePacket()
    {
    }

    public Packet getPacket()
    {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);

        try
        {
            dataoutputstream.writeByte(getID());
            writeData(dataoutputstream);
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        Packet250CustomPayload packet250custompayload = new Packet250CustomPayload();
        packet250custompayload.channel = "Forge";
        packet250custompayload.data = bytearrayoutputstream.toByteArray();
        packet250custompayload.length = packet250custompayload.data.length;
        return packet250custompayload;
    }

    public abstract void writeData(DataOutputStream dataoutputstream) throws IOException;

    public abstract void readData(DataInputStream datainputstream) throws IOException;

    public abstract int getID();

    public String toString(boolean flag)
    {
        return toString();
    }

    public String toString()
    {
        return (new StringBuilder()).append(getID()).append(" ").append(getClass().getSimpleName()).toString();
    }
}
