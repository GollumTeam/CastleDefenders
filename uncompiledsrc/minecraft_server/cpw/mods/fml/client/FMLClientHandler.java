package cpw.mods.fml.client;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import cpw.mods.fml.client.modloader.ModLoaderClientHelper;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.DuplicateModsFoundException;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IFMLSidedHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.MetadataCollection;
import cpw.mods.fml.common.MissingModsException;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.WrongMinecraftVersionException;
import cpw.mods.fml.common.network.EntitySpawnAdjustmentPacket;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.common.network.ModMissingPacket;
import cpw.mods.fml.common.registry.EntityRegistry$EntityRegistration;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.common.registry.IThrowableEntity;
import cpw.mods.fml.common.registry.ItemData;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.toposort.ModSortingException;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class FMLClientHandler implements IFMLSidedHandler
{
    private static final FMLClientHandler INSTANCE = new FMLClientHandler();
    private Minecraft client;
    private DummyModContainer optifineContainer;
    private boolean guiLoaded;
    private boolean serverIsRunning;
    private MissingModsException modsMissing;
    private ModSortingException modSorting;
    private boolean loading;
    private WrongMinecraftVersionException wrongMC;
    private CustomModLoadingErrorDisplayException customError;
    private DuplicateModsFoundException dupesFound;
    private boolean serverShouldBeKilledQuietly;

    public void beginMinecraftLoading(Minecraft var1)
    {
        this.client = var1;

        if (var1.q())
        {
            FMLLog.severe("DEMO MODE DETECTED, FML will not work. Finishing now.", new Object[0]);
            this.haltGame("FML will not run in demo mode", new RuntimeException());
        }
        else
        {
            this.loading = true;
            FMLCommonHandler.instance().beginLoading(this);
            new ModLoaderClientHelper(this.client);

            try
            {
                Class var2 = Class.forName("Config", false, Loader.instance().getModClassLoader());
                String var3 = (String)var2.getField("VERSION").get((Object)null);
                ImmutableMap var4 = ImmutableMap.builder().put("name", "Optifine").put("version", var3).build();
                ModMetadata var5 = MetadataCollection.from(this.getClass().getResourceAsStream("optifinemod.info"), "optifine").getMetadataForId("optifine", var4);
                this.optifineContainer = new DummyModContainer(var5);
                FMLLog.info("Forge Mod Loader has detected optifine %s, enabling compatibility features", new Object[] {this.optifineContainer.getVersion()});
            }
            catch (Exception var12)
            {
                this.optifineContainer = null;
            }

            try
            {
                Loader.instance().loadMods();
            }
            catch (WrongMinecraftVersionException var6)
            {
                this.wrongMC = var6;
            }
            catch (DuplicateModsFoundException var7)
            {
                this.dupesFound = var7;
            }
            catch (MissingModsException var8)
            {
                this.modsMissing = var8;
            }
            catch (ModSortingException var9)
            {
                this.modSorting = var9;
            }
            catch (CustomModLoadingErrorDisplayException var10)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var10, "A custom exception was thrown by a mod, the game will now halt", new Object[0]);
                this.customError = var10;
            }
            catch (LoaderException var11)
            {
                this.haltGame("There was a severe problem during mod loading that has caused the game to fail", var11);
                return;
            }
        }
    }

    public void haltGame(String var1, Throwable var2)
    {
        this.client.c(new CrashReport(var1, var2));
        throw Throwables.propagate(var2);
    }

    public void finishMinecraftLoading()
    {
        if (this.modsMissing == null && this.wrongMC == null && this.customError == null && this.dupesFound == null && this.modSorting == null)
        {
            try
            {
                Loader.instance().initializeMods();
            }
            catch (CustomModLoadingErrorDisplayException var2)
            {
                FMLLog.log(Level.SEVERE, (Throwable)var2, "A custom exception was thrown by a mod, the game will now halt", new Object[0]);
                this.customError = var2;
                return;
            }
            catch (LoaderException var3)
            {
                this.haltGame("There was a severe problem during mod loading that has caused the game to fail", var3);
                return;
            }

            LanguageRegistry.reloadLanguageTable();
            RenderingRegistry.instance().loadEntityRenderers(bgy.a.q);
            this.loading = false;
            KeyBindingRegistry.instance().uploadKeyBindingsToGame(this.client.z);
        }
    }

    public void onInitializationComplete()
    {
        if (this.wrongMC != null)
        {
            this.client.a(new GuiWrongMinecraft(this.wrongMC));
        }
        else if (this.modsMissing != null)
        {
            this.client.a(new GuiModsMissing(this.modsMissing));
        }
        else if (this.dupesFound != null)
        {
            this.client.a(new GuiDupesFound(this.dupesFound));
        }
        else if (this.modSorting != null)
        {
            this.client.a(new GuiSortingProblem(this.modSorting));
        }
        else if (this.customError != null)
        {
            this.client.a(new GuiCustomModLoadingErrorScreen(this.customError));
        }
        else
        {
            this.client.p.c();
        }
    }

    public Minecraft getClient()
    {
        return this.client;
    }

    public Logger getMinecraftLogger()
    {
        return null;
    }

    public static FMLClientHandler instance()
    {
        return INSTANCE;
    }

    public void displayGuiScreen(EntityPlayer var1, GuiScreen var2)
    {
        if (this.client.g == var1 && var2 != null)
        {
            this.client.a(var2);
        }
    }

    public void addSpecialModEntries(ArrayList var1)
    {
        if (this.optifineContainer != null)
        {
            var1.add(this.optifineContainer);
        }
    }

    public List getAdditionalBrandingInformation()
    {
        return (List)(this.optifineContainer != null ? Arrays.asList(new String[] {String.format("Optifine %s", new Object[]{this.optifineContainer.getVersion()})}): ImmutableList.of());
    }

    public Side getSide()
    {
        return Side.CLIENT;
    }

    public boolean hasOptifine()
    {
        return this.optifineContainer != null;
    }

    public void showGuiScreen(Object var1)
    {
        GuiScreen var2 = (GuiScreen)var1;
        this.client.a(var2);
    }

    public Entity spawnEntityIntoClientWorld(EntityRegistry$EntityRegistration var1, EntitySpawnPacket var2)
    {
        bds var3 = this.client.e;
        Class var4 = var1.getEntityClass();

        try
        {
            Entity var5;

            if (var1.hasCustomSpawning())
            {
                var5 = var1.doCustomSpawning(var2);
            }
            else
            {
                var5 = (Entity)var4.getConstructor(new Class[] {World.class}).newInstance(new Object[] {var3});
                int var6 = var2.entityId - var5.entityId;
                var5.entityId = var2.entityId;
                var5.setLocationAndAngles(var2.scaledX, var2.scaledY, var2.scaledZ, var2.scaledYaw, var2.scaledPitch);

                if (var5 instanceof EntityLiving)
                {
                    ((EntityLiving)var5).rotationYawHead = var2.scaledHeadYaw;
                }

                Entity[] var7 = var5.getParts();

                if (var7 != null)
                {
                    for (int var8 = 0; var8 < var7.length; ++var8)
                    {
                        var7[var8].entityId += var6;
                    }
                }
            }

            var5.cx = var2.rawX;
            var5.cy = var2.rawY;
            var5.cz = var2.rawZ;

            if (var5 instanceof IThrowableEntity)
            {
                Object var10 = this.client.g.k == var2.throwerId ? this.client.g : var3.a(var2.throwerId);
                ((IThrowableEntity)var5).setThrower((Entity)var10);
            }

            if (var2.metadata != null)
            {
                var5.getDataWatcher().a(var2.metadata);
            }

            if (var2.throwerId > 0)
            {
                var5.h(var2.speedScaledX, var2.speedScaledY, var2.speedScaledZ);
            }

            if (var5 instanceof IEntityAdditionalSpawnData)
            {
                ((IEntityAdditionalSpawnData)var5).readSpawnData(var2.dataStream);
            }

            var3.a(var2.entityId, var5);
            return var5;
        }
        catch (Exception var9)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var9, "A severe problem occurred during the spawning of an entity", new Object[0]);
            throw Throwables.propagate(var9);
        }
    }

    public void adjustEntityLocationOnClient(EntitySpawnAdjustmentPacket var1)
    {
        Entity var2 = this.client.e.a(var1.entityId);

        if (var2 != null)
        {
            var2.cx = var1.serverX;
            var2.cy = var1.serverY;
            var2.cz = var1.serverZ;
        }
        else
        {
            FMLLog.fine("Attempted to adjust the position of entity %d which is not present on the client", new Object[] {Integer.valueOf(var1.entityId)});
        }
    }

    public void beginServerLoading(MinecraftServer var1)
    {
        this.serverShouldBeKilledQuietly = false;
    }

    public void finishServerLoading() {}

    public MinecraftServer getServer()
    {
        return this.client.D();
    }

    public void sendPacket(Packet var1)
    {
        if (this.client.g != null)
        {
            this.client.g.a.c(var1);
        }
    }

    public void displayMissingMods(ModMissingPacket var1)
    {
        this.client.a(new GuiModsMissingForServer(var1));
    }

    public boolean isLoading()
    {
        return this.loading;
    }

    public void handleTinyPacket(NetHandler var1, Packet131MapData var2)
    {
        ((bdk)var1).fmlPacket131Callback(var2);
    }

    public void setClientCompatibilityLevel(byte var1)
    {
        bdk.setConnectionCompatibilityLevel(var1);
    }

    public byte getClientCompatibilityLevel()
    {
        return bdk.getConnectionCompatibilityLevel();
    }

    public void warnIDMismatch(MapDifference var1, boolean var2)
    {
        GuiIdMismatchScreen var3 = new GuiIdMismatchScreen(var1, var2);
        this.client.a(var3);
    }

    public void callbackIdDifferenceResponse(boolean var1)
    {
        if (var1)
        {
            this.serverShouldBeKilledQuietly = false;
            GameData.releaseGate(true);
            this.client.continueWorldLoading();
        }
        else
        {
            this.serverShouldBeKilledQuietly = true;
            GameData.releaseGate(false);
            this.client.a((bds)null);
            this.client.a((GuiScreen)null);
        }
    }

    public boolean shouldServerShouldBeKilledQuietly()
    {
        return this.serverShouldBeKilledQuietly;
    }

    public void disconnectIDMismatch(MapDifference var1, NetHandler var2, INetworkManager var3)
    {
        boolean var4 = !var1.entriesOnlyOnLeft().isEmpty();
        Iterator var5 = var1.entriesDiffering().entrySet().iterator();

        while (var5.hasNext())
        {
            Entry var6 = (Entry)var5.next();
            ValueDifference var7 = (ValueDifference)var6.getValue();

            if (!((ItemData)var7.leftValue()).mayDifferByOrdinal((ItemData)var7.rightValue()))
            {
                var4 = true;
            }
        }

        if (var4)
        {
            ((bdk)var2).e();
            bdn.forceTermination((bdn)this.client.s);
            var3.processReadPackets();
            this.client.a((bds)null);
            this.warnIDMismatch(var1, false);
        }
    }

    public boolean isGUIOpen(Class var1)
    {
        return this.client.s != null && this.client.s.getClass().equals(var1);
    }
}
