package cpw.mods.fml.common.network;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.FMLPacket$Type;
import cpw.mods.fml.common.registry.GameData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ModListResponsePacket extends FMLPacket
{
    private Map modVersions;
    private List missingMods;

    public ModListResponsePacket()
    {
        super(FMLPacket$Type.MOD_LIST_RESPONSE);
    }

    public byte[] generatePacket(Object ... var1)
    {
        Map var2 = (Map)var1[0];
        List var3 = (List)var1[1];
        ByteArrayDataOutput var4 = ByteStreams.newDataOutput();
        var4.writeInt(var2.size());
        Iterator var5 = var2.entrySet().iterator();

        while (var5.hasNext())
        {
            Entry var6 = (Entry)var5.next();
            var4.writeUTF((String)var6.getKey());
            var4.writeUTF((String)var6.getValue());
        }

        var4.writeInt(var3.size());
        var5 = var3.iterator();

        while (var5.hasNext())
        {
            String var7 = (String)var5.next();
            var4.writeUTF(var7);
        }

        return var4.toByteArray();
    }

    public FMLPacket consumePacket(byte[] var1)
    {
        ByteArrayDataInput var2 = ByteStreams.newDataInput(var1);
        int var3 = var2.readInt();
        this.modVersions = Maps.newHashMapWithExpectedSize(var3);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            String var5 = var2.readUTF();
            String var6 = var2.readUTF();
            this.modVersions.put(var5, var6);
        }

        var4 = var2.readInt();
        this.missingMods = Lists.newArrayListWithExpectedSize(var4);

        for (int var7 = 0; var7 < var4; ++var7)
        {
            this.missingMods.add(var2.readUTF());
        }

        return this;
    }

    public void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4)
    {
        HashMap var5 = Maps.newHashMap(Loader.instance().getIndexedModList());
        ArrayList var6 = Lists.newArrayList();
        ArrayList var7 = Lists.newArrayList();
        Iterator var8 = this.missingMods.iterator();
        ModContainer var10;
        NetworkModHandler var11;

        while (var8.hasNext())
        {
            String var9 = (String)var8.next();
            var10 = (ModContainer)var5.get(var9);
            var11 = var2.findNetworkModHandler(var10);

            if (var11.requiresClientSide())
            {
                var6.add(var9);
            }
        }

        var8 = this.modVersions.entrySet().iterator();

        while (var8.hasNext())
        {
            Entry var13 = (Entry)var8.next();
            var10 = (ModContainer)var5.get(var13.getKey());
            var11 = var2.findNetworkModHandler(var10);

            if (!var11.acceptVersion((String)var13.getValue()))
            {
                var7.add(var13.getKey());
            }
        }

        Packet250CustomPayload var12 = new Packet250CustomPayload();
        var12.channel = "FML";

        if (var6.size() <= 0 && var7.size() <= 0)
        {
            var12.data = FMLPacket.makePacket(FMLPacket$Type.MOD_IDENTIFIERS, new Object[] {var3});
            Logger.getLogger("Minecraft").info(String.format("User %s connecting with mods %s", new Object[] {var4, this.modVersions.keySet()}));
            FMLLog.info("User %s connecting with mods %s", new Object[] {var4, this.modVersions.keySet()});
            var12.length = var12.data.length;
            var1.addToSendQueue(var12);
            NBTTagList var14 = new NBTTagList();
            GameData.writeItemData(var14);
            byte[][] var15 = FMLPacket.makePacketSet(FMLPacket$Type.MOD_IDMAP, new Object[] {var14});

            for (int var16 = 0; var16 < var15.length; ++var16)
            {
                var1.addToSendQueue(PacketDispatcher.getPacket("FML", var15[var16]));
            }
        }
        else
        {
            var12.data = FMLPacket.makePacket(FMLPacket$Type.MOD_MISSING, new Object[] {var6, var7});
            Logger.getLogger("Minecraft").info(String.format("User %s connection failed: missing %s, bad versions %s", new Object[] {var4, var6, var7}));
            FMLLog.info("User %s connection failed: missing %s, bad versions %s", new Object[] {var4, var6, var7});
            FMLNetworkHandler.setHandlerState((NetLoginHandler)var3, -2);
            var12.length = var12.data.length;
            var1.addToSendQueue(var12);
        }

        NetLoginHandler.func_72531_a((NetLoginHandler)var3, true);
    }
}
