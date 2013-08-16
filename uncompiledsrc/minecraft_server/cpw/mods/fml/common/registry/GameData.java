package cpw.mods.fml.common.registry;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.ImmutableTable.Builder;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Table.Cell;
import com.google.common.io.Files;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData$1;
import cpw.mods.fml.common.registry.GameData$2;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GameData
{
    private static Map idMap = Maps.newHashMap();
    private static CountDownLatch serverValidationLatch;
    private static CountDownLatch clientValidationLatch;
    private static MapDifference difference;
    private static boolean shouldContinue = true;
    private static boolean isSaveValid = true;
    private static ImmutableTable modObjectTable;
    private static Table customItemStacks = HashBasedTable.create();
    private static Map ignoredMods;

    private static boolean isModIgnoredForIdValidation(String var0)
    {
        if (ignoredMods == null)
        {
            File var1 = new File(Loader.instance().getConfigDir(), "fmlIDChecking.properties");

            if (var1.exists())
            {
                Properties var2 = new Properties();

                try
                {
                    var2.load(new FileInputStream(var1));
                    ignoredMods = Maps.fromProperties(var2);

                    if (ignoredMods.size() > 0)
                    {
                        FMLLog.log("fml.ItemTracker", Level.WARNING, "Using non-empty ignored mods configuration file %s", new Object[] {ignoredMods.keySet()});
                    }
                }
                catch (Exception var4)
                {
                    Throwables.propagateIfPossible(var4);
                    FMLLog.log("fml.ItemTracker", Level.SEVERE, var4, "Failed to read ignored ID checker mods properties file", new Object[0]);
                    ignoredMods = ImmutableMap.of();
                }
            }
            else
            {
                ignoredMods = ImmutableMap.of();
            }
        }

        return ignoredMods.containsKey(var0);
    }

    public static void newItemAdded(Item var0)
    {
        Object var1 = Loader.instance().activeModContainer();

        if (var1 == null)
        {
            var1 = Loader.instance().getMinecraftModContainer();

            if (Loader.instance().hasReachedState(LoaderState.AVAILABLE))
            {
                FMLLog.severe("It appears something has tried to allocate an Item outside of the initialization phase of Minecraft, this could be very bad for your network connectivity.", new Object[0]);
            }
        }

        String var2 = var0.getClass().getName();
        ItemData var3 = new ItemData(var0, (ModContainer)var1);

        if (idMap.containsKey(Integer.valueOf(var0.itemID)))
        {
            ItemData var4 = (ItemData)idMap.get(Integer.valueOf(var0.itemID));
            FMLLog.log("fml.ItemTracker", Level.INFO, "The mod %s is overwriting existing item at %d (%s from %s) with %s", new Object[] {((ModContainer)var1).getModId(), Integer.valueOf(var4.getItemId()), var4.getItemType(), var4.getModId(), var2});
        }

        idMap.put(Integer.valueOf(var0.itemID), var3);

        if (!"Minecraft".equals(((ModContainer)var1).getModId()))
        {
            FMLLog.log("fml.ItemTracker", Level.FINE, "Adding item %s(%d) owned by %s", new Object[] {var0.getClass().getName(), Integer.valueOf(var0.itemID), ((ModContainer)var1).getModId()});
        }
    }

    public static void validateWorldSave(Set var0)
    {
        isSaveValid = true;
        shouldContinue = true;

        if (var0 == null)
        {
            serverValidationLatch.countDown();

            try
            {
                clientValidationLatch.await();
            }
            catch (InterruptedException var6)
            {
                ;
            }
        }
        else
        {
            GameData$1 var1 = new GameData$1();
            ImmutableMap var2 = Maps.uniqueIndex(var0, var1);
            difference = Maps.difference(var2, idMap);
            FMLLog.log("fml.ItemTracker", Level.FINE, "The difference set is %s", new Object[] {difference});

            if (difference.entriesDiffering().isEmpty() && difference.entriesOnlyOnLeft().isEmpty())
            {
                isSaveValid = true;
                serverValidationLatch.countDown();
            }
            else
            {
                FMLLog.log("fml.ItemTracker", Level.SEVERE, "FML has detected item discrepancies", new Object[0]);
                FMLLog.log("fml.ItemTracker", Level.SEVERE, "Missing items : %s", new Object[] {difference.entriesOnlyOnLeft()});
                FMLLog.log("fml.ItemTracker", Level.SEVERE, "Mismatched items : %s", new Object[] {difference.entriesDiffering()});
                boolean var3 = false;
                Iterator var4 = difference.entriesOnlyOnLeft().values().iterator();

                while (var4.hasNext())
                {
                    ItemData var5 = (ItemData)var4.next();

                    if (!isModIgnoredForIdValidation(var5.getModId()))
                    {
                        var3 = true;
                    }
                }

                var4 = difference.entriesDiffering().values().iterator();

                while (var4.hasNext())
                {
                    ValueDifference var8 = (ValueDifference)var4.next();

                    if (!isModIgnoredForIdValidation(((ItemData)var8.leftValue()).getModId()) && !isModIgnoredForIdValidation(((ItemData)var8.rightValue()).getModId()))
                    {
                        var3 = true;
                    }
                }

                if (!var3)
                {
                    FMLLog.log("fml.ItemTracker", Level.SEVERE, "FML is ignoring these ID discrepancies because of configuration. YOUR GAME WILL NOW PROBABLY CRASH. HOPEFULLY YOU WON\'T HAVE CORRUPTED YOUR WORLD. BLAME %s", new Object[] {ignoredMods.keySet()});
                }

                isSaveValid = !var3;
                serverValidationLatch.countDown();
            }

            try
            {
                clientValidationLatch.await();

                if (!shouldContinue)
                {
                    throw new RuntimeException("This server instance is going to stop abnormally because of a fatal ID mismatch");
                }
            }
            catch (InterruptedException var7)
            {
                ;
            }
        }
    }

    public static void writeItemData(NBTTagList var0)
    {
        Iterator var1 = idMap.values().iterator();

        while (var1.hasNext())
        {
            ItemData var2 = (ItemData)var1.next();
            var0.appendTag(var2.toNBT());
        }
    }

    public static void initializeServerGate(int var0)
    {
        serverValidationLatch = new CountDownLatch(var0 - 1);
        clientValidationLatch = new CountDownLatch(var0 - 1);
    }

    public static MapDifference gateWorldLoadingForValidation()
    {
        try
        {
            serverValidationLatch.await();

            if (!isSaveValid)
            {
                return difference;
            }
        }
        catch (InterruptedException var1)
        {
            ;
        }

        difference = null;
        return null;
    }

    public static void releaseGate(boolean var0)
    {
        shouldContinue = var0;
        clientValidationLatch.countDown();
    }

    public static Set buildWorldItemData(NBTTagList var0)
    {
        HashSet var1 = Sets.newHashSet();

        for (int var2 = 0; var2 < var0.tagCount(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound)var0.tagAt(var2);
            ItemData var4 = new ItemData(var3);
            var1.add(var4);
        }

        return var1;
    }

    static void setName(Item var0, String var1, String var2)
    {
        int var3 = var0.itemID;
        ItemData var4 = (ItemData)idMap.get(Integer.valueOf(var3));
        var4.setName(var1, var2);
    }

    public static void buildModObjectTable()
    {
        if (modObjectTable != null)
        {
            throw new IllegalStateException("Illegal call to buildModObjectTable!");
        }
        else
        {
            Map var0 = Maps.transformValues(idMap, new GameData$2());
            Builder var1 = ImmutableTable.builder();
            Iterator var2 = var0.values().iterator();

            while (var2.hasNext())
            {
                Cell var3 = (Cell)var2.next();

                if (var3 != null)
                {
                    var1.put(var3);
                }
            }

            modObjectTable = var1.build();
        }
    }

    static Item findItem(String var0, String var1)
    {
        return modObjectTable != null && modObjectTable.contains(var0, var1) ? Item.itemsList[((Integer)modObjectTable.get(var0, var1)).intValue()] : null;
    }

    static Block findBlock(String var0, String var1)
    {
        if (modObjectTable == null)
        {
            return null;
        }
        else
        {
            Integer var2 = (Integer)modObjectTable.get(var0, var1);
            return var2 != null && var2.intValue() < Block.blocksList.length ? Block.blocksList[var2.intValue()] : null;
        }
    }

    static ItemStack findItemStack(String var0, String var1)
    {
        ItemStack var2 = (ItemStack)customItemStacks.get(var0, var1);

        if (var2 == null)
        {
            Item var3 = findItem(var0, var1);

            if (var3 != null)
            {
                var2 = new ItemStack(var3, 0, 0);
            }
        }

        if (var2 == null)
        {
            Block var4 = findBlock(var0, var1);

            if (var4 != null)
            {
                var2 = new ItemStack(var4, 0, 32767);
            }
        }

        return var2;
    }

    static void registerCustomItemStack(String var0, ItemStack var1)
    {
        customItemStacks.put(Loader.instance().activeModContainer().getModId(), var0, var1);
    }

    public static void dumpRegistry(File var0)
    {
        if (customItemStacks != null)
        {
            if (Boolean.valueOf(System.getProperty("fml.dumpRegistry", "false")).booleanValue())
            {
                com.google.common.collect.ImmutableListMultimap.Builder var1 = ImmutableListMultimap.builder();
                Iterator var2 = customItemStacks.rowKeySet().iterator();

                while (var2.hasNext())
                {
                    String var3 = (String)var2.next();
                    var1.putAll(var3, customItemStacks.row(var3).keySet());
                }

                File var6 = new File(var0, "itemStackRegistry.csv");
                MapJoiner var7 = Joiner.on("\n").withKeyValueSeparator(",");

                try
                {
                    Files.write(var7.join(var1.build().entries()), var6, Charsets.UTF_8);
                    FMLLog.log(Level.INFO, "Dumped item registry data to %s", new Object[] {var6.getAbsolutePath()});
                }
                catch (IOException var5)
                {
                    FMLLog.log(Level.SEVERE, (Throwable)var5, "Failed to write registry data to %s", new Object[] {var6.getAbsolutePath()});
                }
            }
        }
    }
}
