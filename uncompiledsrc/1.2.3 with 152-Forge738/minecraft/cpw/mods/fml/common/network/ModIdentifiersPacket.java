package cpw.mods.fml.common.network;

import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.FMLPacket$Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;

public class ModIdentifiersPacket extends FMLPacket
{
    private Map modIds = Maps.newHashMap();

    public ModIdentifiersPacket()
    {
        super(FMLPacket$Type.MOD_IDENTIFIERS);
    }

    public byte[] generatePacket(Object ... var1)
    {
        ByteArrayDataOutput var2 = ByteStreams.newDataOutput();
        Collection var3 = FMLNetworkHandler.instance().getNetworkIdMap().values();
        var2.writeInt(var3.size());
        Iterator var4 = var3.iterator();

        while (var4.hasNext())
        {
            NetworkModHandler var5 = (NetworkModHandler)var4.next();
            var2.writeUTF(var5.getContainer().getModId());
            var2.writeInt(var5.getNetworkId());
        }

        return var2.toByteArray();
    }

    public FMLPacket consumePacket(byte[] var1)
    {
        ByteArrayDataInput var2 = ByteStreams.newDataInput(var1);
        int var3 = var2.readInt();

        for (int var4 = 0; var4 < var3; ++var4)
        {
            String var5 = var2.readUTF();
            int var6 = var2.readInt();
            this.modIds.put(var5, Integer.valueOf(var6));
        }

        return this;
    }

    public void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4)
    {
        Iterator var5 = this.modIds.entrySet().iterator();

        while (var5.hasNext())
        {
            Entry var6 = (Entry)var5.next();
            var2.bindNetworkId((String)var6.getKey(), (Integer)var6.getValue());
        }
    }
}
