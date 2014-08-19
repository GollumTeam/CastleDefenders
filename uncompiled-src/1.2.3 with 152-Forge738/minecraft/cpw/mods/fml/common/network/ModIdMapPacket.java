package cpw.mods.fml.common.network;

import com.google.common.collect.MapDifference;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.UnsignedBytes;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.FMLPacket$Type;
import cpw.mods.fml.common.registry.GameData;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;

public class ModIdMapPacket extends FMLPacket
{
    private byte[][] partials;

    public ModIdMapPacket()
    {
        super(FMLPacket$Type.MOD_IDMAP);
    }

    public byte[] generatePacket(Object ... var1)
    {
        NBTTagList var2 = (NBTTagList)var1[0];
        NBTTagCompound var3 = new NBTTagCompound();
        var3.setTag("List", var2);

        try
        {
            return CompressedStreamTools.compress(var3);
        }
        catch (Exception var5)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var5, "A critical error writing the id map", new Object[0]);
            throw new FMLNetworkException(var5);
        }
    }

    public FMLPacket consumePacket(byte[] var1)
    {
        ByteArrayDataInput var2 = ByteStreams.newDataInput(var1);
        int var3 = UnsignedBytes.toInt(var2.readByte());
        int var4 = UnsignedBytes.toInt(var2.readByte());
        int var5 = var2.readInt();

        if (this.partials == null)
        {
            this.partials = new byte[var4][];
        }

        this.partials[var3] = new byte[var5];
        var2.readFully(this.partials[var3]);

        for (int var6 = 0; var6 < this.partials.length; ++var6)
        {
            if (this.partials[var6] == null)
            {
                return null;
            }
        }

        return this;
    }

    public void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4)
    {
        byte[] var5 = Bytes.concat(this.partials);
        GameData.initializeServerGate(1);

        try
        {
            NBTTagCompound var6 = CompressedStreamTools.decompress(var5);
            NBTTagList var7 = var6.getTagList("List");
            Set var8 = GameData.buildWorldItemData(var7);
            GameData.validateWorldSave(var8);
            MapDifference var9 = GameData.gateWorldLoadingForValidation();

            if (var9 != null)
            {
                FMLCommonHandler.instance().disconnectIDMismatch(var9, var3, var1);
            }
        }
        catch (IOException var10)
        {
            ;
        }
    }
}
