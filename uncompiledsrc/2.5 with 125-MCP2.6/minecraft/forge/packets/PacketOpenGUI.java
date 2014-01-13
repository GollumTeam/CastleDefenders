package forge.packets;

import java.io.*;

public class PacketOpenGUI extends ForgePacket
{
    public int WindowID;
    public int ModID;
    public int GuiID;
    public int X;
    public int Y;
    public int Z;

    public PacketOpenGUI()
    {
    }

    public PacketOpenGUI(int i, int j, int k, int l, int i1, int j1)
    {
        WindowID = i;
        ModID = j;
        GuiID = k;
        X = l;
        Y = i1;
        Z = j1;
    }

    public void writeData(DataOutputStream dataoutputstream) throws IOException
    {
        dataoutputstream.writeInt(WindowID);
        dataoutputstream.writeInt(ModID);
        dataoutputstream.writeInt(GuiID);
        dataoutputstream.writeInt(X);
        dataoutputstream.writeInt(Y);
        dataoutputstream.writeInt(Z);
    }

    public void readData(DataInputStream datainputstream) throws IOException
    {
        WindowID = datainputstream.readInt();
        ModID = datainputstream.readInt();
        GuiID = datainputstream.readInt();
        X = datainputstream.readInt();
        Y = datainputstream.readInt();
        Z = datainputstream.readInt();
    }

    public int getID()
    {
        return 5;
    }

    public String toString(boolean flag)
    {
        if (flag)
        {
            StringBuilder stringbuilder = new StringBuilder();
            stringbuilder.append((new StringBuilder()).append(toString()).append('\n').toString());
            stringbuilder.append((new StringBuilder()).append("    Window: ").append(WindowID).append('\n').toString());
            stringbuilder.append((new StringBuilder()).append("    Mod:    ").append(ModID).append('\n').toString());
            stringbuilder.append((new StringBuilder()).append("    Gui:    ").append(GuiID).append('\n').toString());
            stringbuilder.append((new StringBuilder()).append("    X:      ").append(X).append('\n').toString());
            stringbuilder.append((new StringBuilder()).append("    Y:      ").append(Y).append('\n').toString());
            stringbuilder.append((new StringBuilder()).append("    Z:      ").append(Z).append('\n').toString());
            return stringbuilder.toString();
        }
        else
        {
            return toString();
        }
    }
}
