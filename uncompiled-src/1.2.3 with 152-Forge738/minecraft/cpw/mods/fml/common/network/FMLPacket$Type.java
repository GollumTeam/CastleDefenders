package cpw.mods.fml.common.network;

import com.google.common.base.Throwables;
import com.google.common.collect.MapMaker;
import cpw.mods.fml.common.FMLLog;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import net.minecraft.network.INetworkManager;

enum FMLPacket$Type
{
    MOD_LIST_REQUEST(ModListRequestPacket.class, false),
    MOD_LIST_RESPONSE(ModListResponsePacket.class, false),
    MOD_IDENTIFIERS(ModIdentifiersPacket.class, false),
    MOD_MISSING(ModMissingPacket.class, false),
    GUIOPEN(OpenGuiPacket.class, false),
    ENTITYSPAWN(EntitySpawnPacket.class, false),
    ENTITYSPAWNADJUSTMENT(EntitySpawnAdjustmentPacket.class, false),
    MOD_IDMAP(ModIdMapPacket.class, true);
    private Class packetType;
    private boolean isMultipart;
    private ConcurrentMap partTracker;

    private FMLPacket$Type(Class var3, boolean var4)
    {
        this.packetType = var3;
        this.isMultipart = var4;
    }

    FMLPacket make()
    {
        try
        {
            return (FMLPacket)this.packetType.newInstance();
        }
        catch (Exception var2)
        {
            Throwables.propagateIfPossible(var2);
            FMLLog.log(Level.SEVERE, (Throwable)var2, "A bizarre critical error occured during packet encoding", new Object[0]);
            throw new FMLNetworkException(var2);
        }
    }

    public boolean isMultipart()
    {
        return this.isMultipart;
    }

    private FMLPacket findCurrentPart(INetworkManager var1)
    {
        if (this.partTracker == null)
        {
            this.partTracker = (new MapMaker()).weakKeys().weakValues().makeMap();
        }

        if (!this.partTracker.containsKey(var1))
        {
            this.partTracker.put(var1, this.make());
        }

        return (FMLPacket)this.partTracker.get(var1);
    }

    static FMLPacket access$000(FMLPacket$Type var0, INetworkManager var1)
    {
        return var0.findCurrentPart(var1);
    }
}
