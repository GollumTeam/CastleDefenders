package cpw.mods.fml.common.network;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.FMLPacket$Type;
import cpw.mods.fml.common.network.ModMissingPacket$1;
import cpw.mods.fml.common.network.ModMissingPacket$ModData;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.VersionRange;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;

public class ModMissingPacket extends FMLPacket
{
    private List missing;
    private List badVersion;

    public ModMissingPacket()
    {
        super(FMLPacket$Type.MOD_MISSING);
    }

    public byte[] generatePacket(Object ... var1)
    {
        ByteArrayDataOutput var2 = ByteStreams.newDataOutput();
        List var3 = (List)var1[0];
        List var4 = (List)var1[1];
        var2.writeInt(var3.size());
        Iterator var5 = var3.iterator();
        String var6;
        ModContainer var7;

        while (var5.hasNext())
        {
            var6 = (String)var5.next();
            var7 = (ModContainer)Loader.instance().getIndexedModList().get(var6);
            var2.writeUTF(var6);
            var2.writeUTF(var7.getVersion());
        }

        var2.writeInt(var4.size());
        var5 = var4.iterator();

        while (var5.hasNext())
        {
            var6 = (String)var5.next();
            var7 = (ModContainer)Loader.instance().getIndexedModList().get(var6);
            var2.writeUTF(var6);
            var2.writeUTF(var7.getVersion());
        }

        return var2.toByteArray();
    }

    public FMLPacket consumePacket(byte[] var1)
    {
        ByteArrayDataInput var2 = ByteStreams.newDataInput(var1);
        int var3 = var2.readInt();
        this.missing = Lists.newArrayListWithCapacity(var3);
        int var4;

        for (var4 = 0; var4 < var3; ++var4)
        {
            ModMissingPacket$ModData var5 = new ModMissingPacket$ModData((ModMissingPacket$1)null);
            var5.modId = var2.readUTF();
            var5.modVersion = var2.readUTF();
            this.missing.add(var5);
        }

        var4 = var2.readInt();
        this.badVersion = Lists.newArrayListWithCapacity(var4);

        for (int var7 = 0; var7 < var4; ++var7)
        {
            ModMissingPacket$ModData var6 = new ModMissingPacket$ModData((ModMissingPacket$1)null);
            var6.modId = var2.readUTF();
            var6.modVersion = var2.readUTF();
            this.badVersion.add(var6);
        }

        return this;
    }

    public void execute(INetworkManager var1, FMLNetworkHandler var2, NetHandler var3, String var4)
    {
        FMLCommonHandler.instance().getSidedDelegate().displayMissingMods(this);
    }

    public List getModList()
    {
        Builder var1 = ImmutableList.builder();
        Iterator var2 = this.missing.iterator();
        ModMissingPacket$ModData var3;

        while (var2.hasNext())
        {
            var3 = (ModMissingPacket$ModData)var2.next();
            var1.add(new DefaultArtifactVersion(var3.modId, VersionRange.createFromVersion(var3.modVersion, (ArtifactVersion)null)));
        }

        var2 = this.badVersion.iterator();

        while (var2.hasNext())
        {
            var3 = (ModMissingPacket$ModData)var2.next();
            var1.add(new DefaultArtifactVersion(var3.modId, VersionRange.createFromVersion(var3.modVersion, (ArtifactVersion)null)));
        }

        return var1.build();
    }
}
