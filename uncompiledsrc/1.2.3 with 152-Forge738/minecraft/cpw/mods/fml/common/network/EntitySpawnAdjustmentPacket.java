package cpw.mods.fml.common.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLPacket$Type;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;

public class EntitySpawnAdjustmentPacket extends FMLPacket
{
    public int entityId;
    public int serverX;
    public int serverY;
    public int serverZ;

    public EntitySpawnAdjustmentPacket()
    {
        super(FMLPacket$Type.ENTITYSPAWNADJUSTMENT);
    }

    public byte[] generatePacket(Object ... var1)
    {
        ByteArrayDataOutput var2 = ByteStreams.newDataOutput();
        var2.writeInt(((Integer)var1[0]).intValue());
        var2.writeInt(((Integer)var1[1]).intValue());
        var2.writeInt(((Integer)var1[2]).intValue());
        var2.writeInt(((Integer)var1[3]).intValue());
        return var2.toByteArray();
    }

    public FMLPacket consumePacket(byte[] var1)
    {
        ByteArrayDataInput var2 = ByteStreams.newDataInput(var1);
        this.entityId = var2.readInt();
        this.serverX = var2.readInt();
        this.serverY = var2.readInt();
        this.serverZ = var2.readInt();
        return this;
    }

    public void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4)
    {
        FMLCommonHandler.instance().adjustEntityLocationOnClient(this);
    }
}
