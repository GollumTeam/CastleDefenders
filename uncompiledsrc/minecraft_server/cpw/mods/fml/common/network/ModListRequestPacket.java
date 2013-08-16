package cpw.mods.fml.common.network;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.FMLPacket$Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;

public class ModListRequestPacket extends FMLPacket
{
    private List sentModList;
    private byte compatibilityLevel;

    public ModListRequestPacket()
    {
        super(FMLPacket$Type.MOD_LIST_REQUEST);
    }

    public byte[] generatePacket(Object ... var1)
    {
        ByteArrayDataOutput var2 = ByteStreams.newDataOutput();
        Set var3 = FMLNetworkHandler.instance().getNetworkModList();
        var2.writeInt(var3.size());
        Iterator var4 = var3.iterator();

        while (var4.hasNext())
        {
            ModContainer var5 = (ModContainer)var4.next();
            var2.writeUTF(var5.getModId());
        }

        var2.writeByte(FMLNetworkHandler.getCompatibilityLevel());
        return var2.toByteArray();
    }

    public FMLPacket consumePacket(byte[] var1)
    {
        this.sentModList = Lists.newArrayList();
        ByteArrayDataInput var2 = ByteStreams.newDataInput(var1);
        int var3 = var2.readInt();

        for (int var4 = 0; var4 < var3; ++var4)
        {
            this.sentModList.add(var2.readUTF());
        }

        try
        {
            this.compatibilityLevel = var2.readByte();
        }
        catch (IllegalStateException var5)
        {
            FMLLog.fine("No compatibility byte found - the server is too old", new Object[0]);
        }

        return this;
    }

    public void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4)
    {
        ArrayList var5 = Lists.newArrayList();
        HashMap var6 = Maps.newHashMap();
        HashMap var7 = Maps.newHashMap(Loader.instance().getIndexedModList());
        Iterator var8 = this.sentModList.iterator();

        while (var8.hasNext())
        {
            String var9 = (String)var8.next();
            ModContainer var10 = (ModContainer)var7.get(var9);

            if (var10 == null)
            {
                var5.add(var9);
            }
            else
            {
                var7.remove(var9);
                var6.put(var9, var10.getVersion());
            }
        }

        if (var7.size() > 0)
        {
            var8 = var7.entrySet().iterator();

            while (var8.hasNext())
            {
                Entry var12 = (Entry)var8.next();

                if (((ModContainer)var12.getValue()).isNetworkMod())
                {
                    NetworkModHandler var11 = FMLNetworkHandler.instance().findNetworkModHandler(var12.getValue());

                    if (var11.requiresServerSide())
                    {
                        FMLLog.warning("The mod %s was not found on the server you connected to, but requested that the server side be present", new Object[] {var12.getKey()});
                    }
                }
            }
        }

        FMLLog.fine("The server has compatibility level %d", new Object[] {Byte.valueOf(this.compatibilityLevel)});
        FMLCommonHandler.instance().getSidedDelegate().setClientCompatibilityLevel(this.compatibilityLevel);
        var1.addToSendQueue(PacketDispatcher.getPacket("FML", FMLPacket.makePacket(FMLPacket$Type.MOD_LIST_RESPONSE, new Object[] {var6, var5})));
    }
}
