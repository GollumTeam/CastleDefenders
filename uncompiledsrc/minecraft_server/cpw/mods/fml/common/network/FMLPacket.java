package cpw.mods.fml.common.network;

import com.google.common.primitives.Bytes;
import com.google.common.primitives.Ints;
import com.google.common.primitives.UnsignedBytes;
import cpw.mods.fml.common.network.FMLPacket$Type;
import java.util.Arrays;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;

public abstract class FMLPacket
{
    private FMLPacket$Type type;

    public static byte[][] makePacketSet(FMLPacket$Type var0, Object ... var1)
    {
        if (!var0.isMultipart())
        {
            return new byte[0][];
        }
        else
        {
            byte[] var2 = var0.make().generatePacket(var1);
            byte[][] var3 = new byte[var2.length / 32000 + 1][];

            for (int var4 = 0; var4 < var2.length / 32000 + 1; ++var4)
            {
                int var5 = Math.min(32000, var2.length - var4 * 32000);
                var3[var4] = Bytes.concat(new byte[][] {{UnsignedBytes.checkedCast((long)var0.ordinal()), UnsignedBytes.checkedCast((long)var4), UnsignedBytes.checkedCast((long)var3.length)}, Ints.toByteArray(var5), Arrays.copyOfRange(var2, var4 * 32000, var5 + var4 * 32000)});
            }

            return var3;
        }
    }

    public static byte[] makePacket(FMLPacket$Type var0, Object ... var1)
    {
        byte[] var2 = var0.make().generatePacket(var1);
        return Bytes.concat(new byte[][] {{UnsignedBytes.checkedCast((long)var0.ordinal())}, var2});
    }

    public static FMLPacket readPacket(INetworkManager var0, byte[] var1)
    {
        int var2 = UnsignedBytes.toInt(var1[0]);
        FMLPacket$Type var3 = FMLPacket$Type.values()[var2];
        FMLPacket var4;

        if (var3.isMultipart())
        {
            var4 = FMLPacket$Type.access$000(var3, var0);
        }
        else
        {
            var4 = var3.make();
        }

        return var4.consumePacket(Arrays.copyOfRange(var1, 1, var1.length));
    }

    public FMLPacket(FMLPacket$Type var1)
    {
        this.type = var1;
    }

    public abstract byte[] generatePacket(Object ... var1);

    public abstract FMLPacket consumePacket(byte[] var1);

    public abstract void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4);
}
