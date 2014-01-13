package forge.packets;

import java.io.*;
import java.util.*;

public class PacketModList extends ForgePacket
{
    private boolean isServer;
    public String Mods[];
    public Hashtable ModIDs;
    public int Length;

    public PacketModList(boolean flag)
    {
        isServer = false;
        ModIDs = new Hashtable();
        Length = -1;
        isServer = flag;
    }

    public void writeData(DataOutputStream dataoutputstream) throws IOException
    {
        if (!isServer)
        {
            dataoutputstream.writeInt(Mods.length);
            String as[] = Mods;
            int i = as.length;

            for (int j = 0; j < i; j++)
            {
                String s = as[j];
                dataoutputstream.writeUTF(s);
            }
        }
        else
        {
            dataoutputstream.writeInt(ModIDs.size());
            java.util.Map.Entry entry;

            for (Iterator iterator = ModIDs.entrySet().iterator(); iterator.hasNext(); dataoutputstream.writeUTF((String)entry.getValue()))
            {
                entry = (java.util.Map.Entry)iterator.next();
                dataoutputstream.writeInt(((Integer)entry.getKey()).intValue());
            }
        }
    }

    public void readData(DataInputStream datainputstream) throws IOException
    {
        if (isServer)
        {
            Length = datainputstream.readInt();

            if (Length >= 0)
            {
                Mods = new String[Length];

                for (int i = 0; i < Length; i++)
                {
                    Mods[i] = datainputstream.readUTF();
                }
            }
        }
        else
        {
            Length = datainputstream.readInt();

            for (int j = 0; j < Length; j++)
            {
                ModIDs.put(Integer.valueOf(datainputstream.readInt()), datainputstream.readUTF());
            }
        }
    }

    public int getID()
    {
        return 2;
    }

    public String toString(boolean flag)
    {
        if (flag)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append(toString()).append('\n');

            if (Mods != null)
            {
                String as[] = Mods;
                int i = as.length;

                for (int j = 0; j < i; j++)
                {
                    String s = as[j];
                    stringbuilder.append((new StringBuilder()).append("    ").append(s).append('\n').toString());
                }
            }
            else if (ModIDs.size() != 0)
            {
                java.util.Map.Entry entry;

                for (Iterator iterator = ModIDs.entrySet().iterator(); iterator.hasNext(); stringbuilder.append((new StringBuilder()).append(String.format("    %03d ", new Object[]
                        {
                            entry.getKey()
                        })).append((String)entry.getValue()).append('\n').toString()))
                {
                    entry = (java.util.Map.Entry)iterator.next();
                }
            }

            return stringbuilder.toString();
        }
        else
        {
            return toString();
        }
    }
}
