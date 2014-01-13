package cpw.mods.fml.common.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.FMLPacket$Type;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;

public class OpenGuiPacket extends FMLPacket
{
    private int windowId;
    private int networkId;
    private int modGuiId;
    private int x;
    private int y;
    private int z;

    public OpenGuiPacket()
    {
        super(FMLPacket$Type.GUIOPEN);
    }

    public byte[] generatePacket(Object ... var1)
    {
        ByteArrayDataOutput var2 = ByteStreams.newDataOutput();
        var2.writeInt(((Integer)var1[0]).intValue());
        var2.writeInt(((Integer)var1[1]).intValue());
        var2.writeInt(((Integer)var1[2]).intValue());
        var2.writeInt(((Integer)var1[3]).intValue());
        var2.writeInt(((Integer)var1[4]).intValue());
        var2.writeInt(((Integer)var1[5]).intValue());
        return var2.toByteArray();
    }

    public FMLPacket consumePacket(byte[] var1)
    {
        ByteArrayDataInput var2 = ByteStreams.newDataInput(var1);
        this.windowId = var2.readInt();
        this.networkId = var2.readInt();
        this.modGuiId = var2.readInt();
        this.x = var2.readInt();
        this.y = var2.readInt();
        this.z = var2.readInt();
        return this;
    }

    public void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4)
    {
        EntityPlayer var5 = var3.getPlayer();
        var5.openGui(Integer.valueOf(this.networkId), this.modGuiId, var5.worldObj, this.x, this.y, this.z);
        var5.openContainer.windowId = this.windowId;
    }
}
