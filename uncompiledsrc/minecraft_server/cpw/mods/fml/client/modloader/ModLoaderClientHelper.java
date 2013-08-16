package cpw.mods.fml.client.modloader;

import com.google.common.base.Equivalence;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.MapDifference.ValueDifference;
import cpw.mods.fml.client.modloader.ModLoaderClientHelper$1;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.modloader.BaseModProxy;
import cpw.mods.fml.common.modloader.IModLoaderSidedHelper;
import cpw.mods.fml.common.modloader.ModLoaderHelper;
import cpw.mods.fml.common.modloader.ModLoaderModContainer;
import cpw.mods.fml.common.network.EntitySpawnPacket;
import cpw.mods.fml.common.registry.EntityRegistry$EntityRegistration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet250CustomPayload;

public class ModLoaderClientHelper implements IModLoaderSidedHelper
{
    private Minecraft client;
    private static Multimap keyBindingContainers;
    private Map managerLookups = (new MapMaker()).weakKeys().weakValues().makeMap();

    public static int obtainBlockModelIdFor(BaseMod var0, boolean var1)
    {
        int var2 = RenderingRegistry.getNextAvailableRenderId();
        ModLoaderBlockRendererHandler var3 = new ModLoaderBlockRendererHandler(var2, var1, var0);
        RenderingRegistry.registerBlockHandler(var3);
        return var2;
    }

    public static void handleFinishLoadingFor(ModLoaderModContainer var0, Minecraft var1)
    {
        FMLLog.log(var0.getModId(), Level.FINE, "Handling post startup activities for ModLoader mod %s", new Object[] {var0.getModId()});
        BaseMod var2 = (BaseMod)var0.getMod();
        HashMap var3 = Maps.newHashMap(bgy.a.q);

        try
        {
            FMLLog.log(var0.getModId(), Level.FINEST, "Requesting renderers from basemod %s", new Object[] {var0.getModId()});
            var2.addRenderer(var3);
            FMLLog.log(var0.getModId(), Level.FINEST, "Received %d renderers from basemod %s", new Object[] {Integer.valueOf(var3.size()), var0.getModId()});
        }
        catch (Exception var8)
        {
            FMLLog.log(var0.getModId(), Level.SEVERE, var8, "A severe problem was detected with the mod %s during the addRenderer call. Continuing, but expect odd results", new Object[] {var0.getModId()});
        }

        MapDifference var4 = Maps.difference(bgy.a.q, var3, Equivalence.identity());
        Iterator var5 = var4.entriesOnlyOnLeft().entrySet().iterator();
        Entry var6;

        while (var5.hasNext())
        {
            var6 = (Entry)var5.next();
            FMLLog.log(var0.getModId(), Level.WARNING, "The mod %s attempted to remove an entity renderer %s from the entity map. This will be ignored.", new Object[] {var0.getModId(), ((Class)var6.getKey()).getName()});
        }

        var5 = var4.entriesOnlyOnRight().entrySet().iterator();

        while (var5.hasNext())
        {
            var6 = (Entry)var5.next();
            FMLLog.log(var0.getModId(), Level.FINEST, "Registering ModLoader entity renderer %s as instance of %s", new Object[] {((Class)var6.getKey()).getName(), ((bgz)var6.getValue()).getClass().getName()});
            RenderingRegistry.registerEntityRenderingHandler((Class)var6.getKey(), (bgz)var6.getValue());
        }

        var5 = var4.entriesDiffering().entrySet().iterator();

        while (var5.hasNext())
        {
            var6 = (Entry)var5.next();
            FMLLog.log(var0.getModId(), Level.FINEST, "Registering ModLoader entity rendering override for %s as instance of %s", new Object[] {((Class)var6.getKey()).getName(), ((bgz)((ValueDifference)var6.getValue()).rightValue()).getClass().getName()});
            RenderingRegistry.registerEntityRenderingHandler((Class)var6.getKey(), (bgz)((ValueDifference)var6.getValue()).rightValue());
        }

        try
        {
            var2.registerAnimation(var1);
        }
        catch (Exception var7)
        {
            FMLLog.log(var0.getModId(), Level.SEVERE, var7, "A severe problem was detected with the mod %s during the registerAnimation call. Continuing, but expect odd results", new Object[] {var0.getModId()});
        }
    }

    public ModLoaderClientHelper(Minecraft var1)
    {
        this.client = var1;
        ModLoaderHelper.sidedHelper = this;
        keyBindingContainers = Multimaps.newMultimap(Maps.newHashMap(), new ModLoaderClientHelper$1(this));
    }

    public void finishModLoading(ModLoaderModContainer var1)
    {
        handleFinishLoadingFor(var1, this.client);
    }

    public static void registerKeyBinding(BaseModProxy var0, ava var1, boolean var2)
    {
        ModLoaderModContainer var3 = (ModLoaderModContainer)Loader.instance().activeModContainer();
        ModLoaderKeyBindingHandler var4 = (ModLoaderKeyBindingHandler)Iterables.getOnlyElement(keyBindingContainers.get(var3));
        var4.setModContainer(var3);
        var4.addKeyBinding(var1, var2);
        KeyBindingRegistry.registerKeyBinding(var4);
    }

    public Object getClientGui(BaseModProxy var1, EntityPlayer var2, int var3, int var4, int var5, int var6)
    {
        return ((BaseMod)var1).getContainerGUI((bdv)var2, var3, var4, var5, var6);
    }

    public Entity spawnEntity(BaseModProxy var1, EntitySpawnPacket var2, EntityRegistry$EntityRegistration var3)
    {
        return ((BaseMod)var1).spawnEntity(var3.getModEntityId(), this.client.e, var2.scaledX, var2.scaledY, var2.scaledZ);
    }

    public void sendClientPacket(BaseModProxy var1, Packet250CustomPayload var2)
    {
        ((BaseMod)var1).clientCustomPayload(this.client.g.a, var2);
    }

    public void clientConnectionOpened(NetHandler var1, INetworkManager var2, BaseModProxy var3)
    {
        this.managerLookups.put(var2, var1);
        ((BaseMod)var3).clientConnect((bdk)var1);
    }

    public boolean clientConnectionClosed(INetworkManager var1, BaseModProxy var2)
    {
        if (this.managerLookups.containsKey(var1))
        {
            ((BaseMod)var2).clientDisconnect((bdk)this.managerLookups.get(var1));
            return true;
        }
        else
        {
            return false;
        }
    }
}
