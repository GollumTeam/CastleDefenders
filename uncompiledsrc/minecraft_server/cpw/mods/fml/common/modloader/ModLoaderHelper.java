package cpw.mods.fml.common.modloader;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.IChatListener;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.EntityRegistry$EntityRegistration;
import cpw.mods.fml.common.registry.VillagerRegistry;
import java.util.EnumSet;
import java.util.Map;
import net.minecraft.command.ICommand;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ModLoaderHelper
{
    public static IModLoaderSidedHelper sidedHelper;
    private static Map guiHelpers = Maps.newHashMap();
    private static Map guiIDs = Maps.newHashMap();
    private static ModLoaderVillageTradeHandler[] tradeHelpers = new ModLoaderVillageTradeHandler[6];

    public static void updateStandardTicks(BaseModProxy var0, boolean var1, boolean var2)
    {
        ModLoaderModContainer var3 = (ModLoaderModContainer)Loader.instance().getReversedModObjectList().get(var0);

        if (var3 == null)
        {
            var3 = (ModLoaderModContainer)Loader.instance().activeModContainer();
        }

        if (var3 == null)
        {
            FMLLog.severe("Attempted to register ModLoader ticking for invalid BaseMod %s", new Object[] {var0});
        }
        else
        {
            BaseModTicker var4 = var3.getGameTickHandler();
            EnumSet var5 = var4.ticks();

            if (var1 && !var2)
            {
                var5.add(TickType.RENDER);
            }
            else
            {
                var5.remove(TickType.RENDER);
            }

            if (var1 && (var2 || FMLCommonHandler.instance().getSide().isServer()))
            {
                var5.add(TickType.CLIENT);
                var5.add(TickType.WORLDLOAD);
            }
            else
            {
                var5.remove(TickType.CLIENT);
                var5.remove(TickType.WORLDLOAD);
            }
        }
    }

    public static void updateGUITicks(BaseModProxy var0, boolean var1, boolean var2)
    {
        ModLoaderModContainer var3 = (ModLoaderModContainer)Loader.instance().getReversedModObjectList().get(var0);

        if (var3 == null)
        {
            var3 = (ModLoaderModContainer)Loader.instance().activeModContainer();
        }

        if (var3 == null)
        {
            FMLLog.severe("Attempted to register ModLoader ticking for invalid BaseMod %s", new Object[] {var0});
        }
        else
        {
            EnumSet var4 = var3.getGUITickHandler().ticks();

            if (var1 && !var2)
            {
                var4.add(TickType.RENDER);
            }
            else
            {
                var4.remove(TickType.RENDER);
            }

            if (var1 && var2)
            {
                var4.add(TickType.CLIENT);
                var4.add(TickType.WORLDLOAD);
            }
            else
            {
                var4.remove(TickType.CLIENT);
                var4.remove(TickType.WORLDLOAD);
            }
        }
    }

    public static IPacketHandler buildPacketHandlerFor(BaseModProxy var0)
    {
        return new ModLoaderPacketHandler(var0);
    }

    public static IWorldGenerator buildWorldGenHelper(BaseModProxy var0)
    {
        return new ModLoaderWorldGenerator(var0);
    }

    public static IFuelHandler buildFuelHelper(BaseModProxy var0)
    {
        return new ModLoaderFuelHelper(var0);
    }

    public static ICraftingHandler buildCraftingHelper(BaseModProxy var0)
    {
        return new ModLoaderCraftingHelper(var0);
    }

    public static void finishModLoading(ModLoaderModContainer var0)
    {
        if (sidedHelper != null)
        {
            sidedHelper.finishModLoading(var0);
        }
    }

    public static IConnectionHandler buildConnectionHelper(BaseModProxy var0)
    {
        return new ModLoaderConnectionHandler(var0);
    }

    public static IPickupNotifier buildPickupHelper(BaseModProxy var0)
    {
        return new ModLoaderPickupNotifier(var0);
    }

    public static void buildGuiHelper(BaseModProxy var0, int var1)
    {
        ModLoaderGuiHelper var2 = (ModLoaderGuiHelper)guiHelpers.get(var0);

        if (var2 == null)
        {
            var2 = new ModLoaderGuiHelper(var0);
            guiHelpers.put(var0, var2);
            NetworkRegistry.instance().registerGuiHandler(var0, var2);
        }

        var2.associateId(var1);
        guiIDs.put(Integer.valueOf(var1), var2);
    }

    public static void openGui(int var0, EntityPlayer var1, Container var2, int var3, int var4, int var5)
    {
        ModLoaderGuiHelper var6 = (ModLoaderGuiHelper)guiIDs.get(Integer.valueOf(var0));
        var6.injectContainerAndID(var2, var0);
        var1.openGui(var6.getMod(), var0, var1.worldObj, var3, var4, var5);
    }

    public static Object getClientSideGui(BaseModProxy var0, EntityPlayer var1, int var2, int var3, int var4, int var5)
    {
        return sidedHelper != null ? sidedHelper.getClientGui(var0, var1, var2, var3, var4, var5) : null;
    }

    public static void buildEntityTracker(BaseModProxy var0, Class var1, int var2, int var3, int var4, boolean var5)
    {
        EntityRegistry$EntityRegistration var6 = EntityRegistry.registerModLoaderEntity(var0, var1, var2, var3, var4, var5);
        var6.setCustomSpawning(new ModLoaderEntitySpawnCallback(var0, var6), EntityDragon.class.isAssignableFrom(var1) || IAnimals.class.isAssignableFrom(var1));
    }

    public static void registerTrade(int var0, TradeEntry var1)
    {
        assert var0 < tradeHelpers.length : "The profession is out of bounds";

        if (tradeHelpers[var0] == null)
        {
            tradeHelpers[var0] = new ModLoaderVillageTradeHandler();
            VillagerRegistry.instance().registerVillageTradeHandler(var0, tradeHelpers[var0]);
        }

        tradeHelpers[var0].addTrade(var1);
    }

    public static void addCommand(ICommand var0)
    {
        ModLoaderModContainer var1 = (ModLoaderModContainer)Loader.instance().activeModContainer();

        if (var1 != null)
        {
            var1.addServerCommand(var0);
        }
    }

    public static IChatListener buildChatListener(BaseModProxy var0)
    {
        return new ModLoaderChatListener(var0);
    }
}
