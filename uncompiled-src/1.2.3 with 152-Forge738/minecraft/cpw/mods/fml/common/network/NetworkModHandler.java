package cpw.mods.fml.common.network;

import com.google.common.base.Strings;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.discovery.ASMDataTable$ASMData;
import cpw.mods.fml.common.network.NetworkMod$VersionCheckHandler;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.InvalidVersionSpecificationException;
import cpw.mods.fml.common.versioning.VersionRange;
import cpw.mods.fml.relauncher.Side;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.item.Item;

public class NetworkModHandler
{
    private static Object connectionHandlerDefaultValue;
    private static Object packetHandlerDefaultValue;
    private static Object clientHandlerDefaultValue;
    private static Object serverHandlerDefaultValue;
    private static Object tinyPacketHandlerDefaultValue;
    private static int assignedIds = 1;
    private int localId;
    private int networkId;
    private ModContainer container;
    private NetworkMod mod;
    private Method checkHandler;
    private VersionRange acceptableRange;
    private ITinyPacketHandler tinyPacketHandler;

    public NetworkModHandler(ModContainer var1, NetworkMod var2)
    {
        this.container = var1;
        this.mod = var2;
        this.localId = assignedIds++;
        this.networkId = this.localId;

        if (Item.map.itemID == assignedIds)
        {
            ++assignedIds;
        }
    }

    public NetworkModHandler(ModContainer var1, Class var2, ASMDataTable var3)
    {
        this(var1, (NetworkMod)var2.getAnnotation(NetworkMod.class));

        if (this.mod != null)
        {
            Set var4 = var3.getAnnotationsFor(var1).get(NetworkMod$VersionCheckHandler.class.getName());
            String var5 = null;
            Iterator var6 = var4.iterator();

            while (var6.hasNext())
            {
                ASMDataTable$ASMData var7 = (ASMDataTable$ASMData)var6.next();

                if (var7.getClassName().equals(var2.getName()))
                {
                    var5 = var7.getObjectName();
                    break;
                }
            }

            if (var5 != null)
            {
                try
                {
                    Method var9 = var2.getDeclaredMethod(var5, new Class[] {String.class});

                    if (var9.isAnnotationPresent(NetworkMod$VersionCheckHandler.class))
                    {
                        this.checkHandler = var9;
                    }
                }
                catch (Exception var8)
                {
                    FMLLog.log(Level.WARNING, (Throwable)var8, "The declared version check handler method %s on network mod id %s is not accessible", new Object[] {var5, var1.getModId()});
                }
            }

            this.configureNetworkMod(var1);
        }
    }

    protected void configureNetworkMod(ModContainer var1)
    {
        if (this.checkHandler == null)
        {
            String var2 = this.mod.versionBounds();

            if (!Strings.isNullOrEmpty(var2))
            {
                try
                {
                    this.acceptableRange = VersionRange.createFromVersionSpec(var2);
                }
                catch (InvalidVersionSpecificationException var7)
                {
                    FMLLog.log(Level.WARNING, (Throwable)var7, "Invalid bounded range %s specified for network mod id %s", new Object[] {var2, var1.getModId()});
                }
            }
        }

        FMLLog.finest("Testing mod %s to verify it accepts its own version in a remote connection", new Object[] {var1.getModId()});
        boolean var8 = this.acceptVersion(var1.getVersion());

        if (!var8)
        {
            FMLLog.severe("The mod %s appears to reject its own version number (%s) in its version handling. This is likely a severe bug in the mod!", new Object[] {var1.getModId(), var1.getVersion()});
        }
        else
        {
            FMLLog.finest("The mod %s accepts its own version (%s)", new Object[] {var1.getModId(), var1.getVersion()});
        }

        this.tryCreatingPacketHandler(var1, this.mod.packetHandler(), this.mod.channels(), (Side)null);

        if (FMLCommonHandler.instance().getSide().isClient() && this.mod.clientPacketHandlerSpec() != this.getClientHandlerSpecDefaultValue())
        {
            this.tryCreatingPacketHandler(var1, this.mod.clientPacketHandlerSpec().packetHandler(), this.mod.clientPacketHandlerSpec().channels(), Side.CLIENT);
        }

        if (this.mod.serverPacketHandlerSpec() != this.getServerHandlerSpecDefaultValue())
        {
            this.tryCreatingPacketHandler(var1, this.mod.serverPacketHandlerSpec().packetHandler(), this.mod.serverPacketHandlerSpec().channels(), Side.SERVER);
        }

        if (this.mod.connectionHandler() != this.getConnectionHandlerDefaultValue())
        {
            IConnectionHandler var3;

            try
            {
                var3 = (IConnectionHandler)this.mod.connectionHandler().newInstance();
            }
            catch (Exception var6)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var6, "Unable to create connection handler instance %s", new Object[] {this.mod.connectionHandler().getName()});
                throw new FMLNetworkException(var6);
            }

            NetworkRegistry.instance().registerConnectionHandler(var3);
        }

        if (this.mod.tinyPacketHandler() != this.getTinyPacketHandlerDefaultValue())
        {
            try
            {
                this.tinyPacketHandler = (ITinyPacketHandler)this.mod.tinyPacketHandler().newInstance();
            }
            catch (Exception var5)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var5, "Unable to create tiny packet handler instance %s", new Object[] {this.mod.tinyPacketHandler().getName()});
                throw new FMLNetworkException(var5);
            }
        }
    }

    private void tryCreatingPacketHandler(ModContainer var1, Class var2, String[] var3, Side var4)
    {
        if (var4 == null || !var4.isClient() || FMLCommonHandler.instance().getSide().isClient())
        {
            if (var2 != this.getPacketHandlerDefaultValue())
            {
                if (var3.length == 0)
                {
                    FMLLog.log(Level.WARNING, "The mod id %s attempted to register a packet handler without specifying channels for it", new Object[] {var1.getModId()});
                }
                else
                {
                    IPacketHandler var5;

                    try
                    {
                        var5 = (IPacketHandler)var2.newInstance();
                    }
                    catch (Exception var10)
                    {
                        FMLLog.log(Level.SEVERE, (Throwable)var10, "Unable to create a packet handler instance %s for mod %s", new Object[] {var2.getName(), var1.getModId()});
                        throw new FMLNetworkException(var10);
                    }

                    String[] var6 = var3;
                    int var7 = var3.length;

                    for (int var8 = 0; var8 < var7; ++var8)
                    {
                        String var9 = var6[var8];
                        NetworkRegistry.instance().registerChannel(var5, var9, var4);
                    }
                }
            }
            else if (var3.length > 0)
            {
                FMLLog.warning("The mod id %s attempted to register channels without specifying a packet handler", new Object[] {var1.getModId()});
            }
        }
    }

    private Object getConnectionHandlerDefaultValue()
    {
        try
        {
            if (connectionHandlerDefaultValue == null)
            {
                connectionHandlerDefaultValue = NetworkMod.class.getMethod("connectionHandler", new Class[0]).getDefaultValue();
            }

            return connectionHandlerDefaultValue;
        }
        catch (NoSuchMethodException var2)
        {
            throw new RuntimeException("Derp?", var2);
        }
    }

    private Object getPacketHandlerDefaultValue()
    {
        try
        {
            if (packetHandlerDefaultValue == null)
            {
                packetHandlerDefaultValue = NetworkMod.class.getMethod("packetHandler", new Class[0]).getDefaultValue();
            }

            return packetHandlerDefaultValue;
        }
        catch (NoSuchMethodException var2)
        {
            throw new RuntimeException("Derp?", var2);
        }
    }

    private Object getTinyPacketHandlerDefaultValue()
    {
        try
        {
            if (tinyPacketHandlerDefaultValue == null)
            {
                tinyPacketHandlerDefaultValue = NetworkMod.class.getMethod("tinyPacketHandler", new Class[0]).getDefaultValue();
            }

            return tinyPacketHandlerDefaultValue;
        }
        catch (NoSuchMethodException var2)
        {
            throw new RuntimeException("Derp?", var2);
        }
    }

    private Object getClientHandlerSpecDefaultValue()
    {
        try
        {
            if (clientHandlerDefaultValue == null)
            {
                clientHandlerDefaultValue = NetworkMod.class.getMethod("clientPacketHandlerSpec", new Class[0]).getDefaultValue();
            }

            return clientHandlerDefaultValue;
        }
        catch (NoSuchMethodException var2)
        {
            throw new RuntimeException("Derp?", var2);
        }
    }

    private Object getServerHandlerSpecDefaultValue()
    {
        try
        {
            if (serverHandlerDefaultValue == null)
            {
                serverHandlerDefaultValue = NetworkMod.class.getMethod("serverPacketHandlerSpec", new Class[0]).getDefaultValue();
            }

            return serverHandlerDefaultValue;
        }
        catch (NoSuchMethodException var2)
        {
            throw new RuntimeException("Derp?", var2);
        }
    }

    public boolean requiresClientSide()
    {
        return this.mod.clientSideRequired();
    }

    public boolean requiresServerSide()
    {
        return this.mod.serverSideRequired();
    }

    public boolean acceptVersion(String var1)
    {
        if (this.checkHandler != null)
        {
            try
            {
                return ((Boolean)this.checkHandler.invoke(this.container.getMod(), new Object[] {var1})).booleanValue();
            }
            catch (Exception var3)
            {
                FMLLog.log(Level.WARNING, (Throwable)var3, "There was a problem invoking the checkhandler method %s for network mod id %s", new Object[] {this.checkHandler.getName(), this.container.getModId()});
                return false;
            }
        }
        else
        {
            return this.acceptableRange != null ? this.acceptableRange.containsVersion(new DefaultArtifactVersion(var1)) : this.container.getVersion().equals(var1);
        }
    }

    public int getLocalId()
    {
        return this.localId;
    }

    public int getNetworkId()
    {
        return this.networkId;
    }

    public ModContainer getContainer()
    {
        return this.container;
    }

    public NetworkMod getMod()
    {
        return this.mod;
    }

    public boolean isNetworkMod()
    {
        return this.mod != null;
    }

    public void setNetworkId(int var1)
    {
        this.networkId = var1;
    }

    public boolean hasTinyPacketHandler()
    {
        return this.tinyPacketHandler != null;
    }

    public ITinyPacketHandler getTinyPacketHandler()
    {
        return this.tinyPacketHandler;
    }
}
