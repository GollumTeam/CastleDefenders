package cpw.mods.fml.common.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.LoaderState;
import cpw.mods.fml.common.Mod$Block;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class GameRegistry
{
    private static Multimap blockRegistry = ArrayListMultimap.create();
    private static Set worldGenerators = Sets.newHashSet();
    private static List fuelHandlers = Lists.newArrayList();
    private static List craftingHandlers = Lists.newArrayList();
    private static List pickupHandlers = Lists.newArrayList();
    private static List playerTrackers = Lists.newArrayList();

    public static void registerWorldGenerator(IWorldGenerator var0)
    {
        worldGenerators.add(var0);
    }

    public static void generateWorld(int var0, int var1, World var2, IChunkProvider var3, IChunkProvider var4)
    {
        long var5 = var2.getSeed();
        Random var7 = new Random(var5);
        long var8 = var7.nextLong() >> 3;
        long var10 = var7.nextLong() >> 3;
        var7.setSeed(var8 * (long)var0 + var10 * (long)var1 ^ var5);
        Iterator var12 = worldGenerators.iterator();

        while (var12.hasNext())
        {
            IWorldGenerator var13 = (IWorldGenerator)var12.next();
            var13.generate(var7, var0, var1, var2, var3, var4);
        }
    }

    public static Object buildBlock(ModContainer var0, Class var1, Mod$Block var2) throws Exception
    {
        Object var3 = var1.getConstructor(new Class[] {Integer.TYPE}).newInstance(new Object[] {Integer.valueOf(findSpareBlockId())});
        registerBlock((Block)var3);
        return var3;
    }

    private static int findSpareBlockId()
    {
        return BlockTracker.nextBlockId();
    }

    public static void registerItem(Item var0, String var1)
    {
        registerItem(var0, var1, (String)null);
    }

    public static void registerItem(Item var0, String var1, String var2)
    {
        GameData.setName(var0, var1, var2);
    }

    @Deprecated
    public static void registerBlock(Block var0)
    {
        registerBlock(var0, ItemBlock.class);
    }

    public static void registerBlock(Block var0, String var1)
    {
        registerBlock(var0, ItemBlock.class, var1);
    }

    @Deprecated
    public static void registerBlock(Block var0, Class var1)
    {
        registerBlock(var0, var1, (String)null);
    }

    public static void registerBlock(Block var0, Class var1, String var2)
    {
        registerBlock(var0, var1, var2, (String)null);
    }

    public static void registerBlock(Block var0, Class var1, String var2, String var3)
    {
        if (Loader.instance().isInState(LoaderState.CONSTRUCTING))
        {
            FMLLog.warning("The mod %s is attempting to register a block whilst it it being constructed. This is bad modding practice - please use a proper mod lifecycle event.", new Object[] {Loader.instance().activeModContainer()});
        }

        try
        {
            assert var0 != null : "registerBlock: block cannot be null";
            assert var1 != null : "registerBlock: itemclass cannot be null";
            int var4 = var0.blockID - 256;
            Constructor var5;
            Item var6;

            try
            {
                var5 = var1.getConstructor(new Class[] {Integer.TYPE});
                var6 = (Item)var5.newInstance(new Object[] {Integer.valueOf(var4)});
            }
            catch (NoSuchMethodException var8)
            {
                var5 = var1.getConstructor(new Class[] {Integer.TYPE, Block.class});
                var6 = (Item)var5.newInstance(new Object[] {Integer.valueOf(var4), var0});
            }

            registerItem(var6, var2, var3);
        }
        catch (Exception var9)
        {
            FMLLog.log(Level.SEVERE, (Throwable)var9, "Caught an exception during block registration", new Object[0]);
            throw new LoaderException(var9);
        }

        blockRegistry.put(Loader.instance().activeModContainer(), (BlockProxy)var0);
    }

    public static void addRecipe(ItemStack var0, Object ... var1)
    {
        addShapedRecipe(var0, var1);
    }

    public static IRecipe addShapedRecipe(ItemStack var0, Object ... var1)
    {
        return CraftingManager.getInstance().addRecipe(var0, var1);
    }

    public static void addShapelessRecipe(ItemStack var0, Object ... var1)
    {
        CraftingManager.getInstance().addShapelessRecipe(var0, var1);
    }

    public static void addRecipe(IRecipe var0)
    {
        CraftingManager.getInstance().getRecipeList().add(var0);
    }

    public static void addSmelting(int var0, ItemStack var1, float var2)
    {
        FurnaceRecipes.smelting().addSmelting(var0, var1, var2);
    }

    public static void registerTileEntity(Class var0, String var1)
    {
        TileEntity.addMapping(var0, var1);
    }

    public static void registerTileEntityWithAlternatives(Class var0, String var1, String ... var2)
    {
        TileEntity.addMapping(var0, var1);
        Map var3 = (Map)ObfuscationReflectionHelper.getPrivateValue(TileEntity.class, (Object)null, new String[] {"nameToClassMap", "nameToClassMap", "a"});
        String[] var4 = var2;
        int var5 = var2.length;

        for (int var6 = 0; var6 < var5; ++var6)
        {
            String var7 = var4[var6];

            if (!var3.containsKey(var7))
            {
                var3.put(var7, var0);
            }
        }
    }

    public static void addBiome(BiomeGenBase var0)
    {
        WorldType.DEFAULT.addNewBiome(var0);
    }

    public static void removeBiome(BiomeGenBase var0)
    {
        WorldType.DEFAULT.removeBiome(var0);
    }

    public static void registerFuelHandler(IFuelHandler var0)
    {
        fuelHandlers.add(var0);
    }

    public static int getFuelValue(ItemStack var0)
    {
        int var1 = 0;
        IFuelHandler var3;

        for (Iterator var2 = fuelHandlers.iterator(); var2.hasNext(); var1 = Math.max(var1, var3.getBurnTime(var0)))
        {
            var3 = (IFuelHandler)var2.next();
        }

        return var1;
    }

    public static void registerCraftingHandler(ICraftingHandler var0)
    {
        craftingHandlers.add(var0);
    }

    public static void onItemCrafted(EntityPlayer var0, ItemStack var1, IInventory var2)
    {
        Iterator var3 = craftingHandlers.iterator();

        while (var3.hasNext())
        {
            ICraftingHandler var4 = (ICraftingHandler)var3.next();
            var4.onCrafting(var0, var1, var2);
        }
    }

    public static void onItemSmelted(EntityPlayer var0, ItemStack var1)
    {
        Iterator var2 = craftingHandlers.iterator();

        while (var2.hasNext())
        {
            ICraftingHandler var3 = (ICraftingHandler)var2.next();
            var3.onSmelting(var0, var1);
        }
    }

    public static void registerPickupHandler(IPickupNotifier var0)
    {
        pickupHandlers.add(var0);
    }

    public static void onPickupNotification(EntityPlayer var0, EntityItem var1)
    {
        Iterator var2 = pickupHandlers.iterator();

        while (var2.hasNext())
        {
            IPickupNotifier var3 = (IPickupNotifier)var2.next();
            var3.notifyPickup(var1, var0);
        }
    }

    public static void registerPlayerTracker(IPlayerTracker var0)
    {
        playerTrackers.add(var0);
    }

    public static void onPlayerLogin(EntityPlayer var0)
    {
        Iterator var1 = playerTrackers.iterator();

        while (var1.hasNext())
        {
            IPlayerTracker var2 = (IPlayerTracker)var1.next();
            var2.onPlayerLogin(var0);
        }
    }

    public static void onPlayerLogout(EntityPlayer var0)
    {
        Iterator var1 = playerTrackers.iterator();

        while (var1.hasNext())
        {
            IPlayerTracker var2 = (IPlayerTracker)var1.next();
            var2.onPlayerLogout(var0);
        }
    }

    public static void onPlayerChangedDimension(EntityPlayer var0)
    {
        Iterator var1 = playerTrackers.iterator();

        while (var1.hasNext())
        {
            IPlayerTracker var2 = (IPlayerTracker)var1.next();
            var2.onPlayerChangedDimension(var0);
        }
    }

    public static void onPlayerRespawn(EntityPlayer var0)
    {
        Iterator var1 = playerTrackers.iterator();

        while (var1.hasNext())
        {
            IPlayerTracker var2 = (IPlayerTracker)var1.next();
            var2.onPlayerRespawn(var0);
        }
    }

    public static Block findBlock(String var0, String var1)
    {
        return GameData.findBlock(var0, var1);
    }

    public static Item findItem(String var0, String var1)
    {
        return GameData.findItem(var0, var1);
    }

    public static void registerCustomItemStack(String var0, ItemStack var1)
    {
        GameData.registerCustomItemStack(var0, var1);
    }

    public static ItemStack findItemStack(String var0, String var1, int var2)
    {
        ItemStack var3 = GameData.findItemStack(var0, var1);

        if (var3 != null)
        {
            ItemStack var4 = var3.copy();
            var4.stackSize = Math.min(var2, var4.getMaxStackSize());
            return var4;
        }
        else
        {
            return null;
        }
    }
}
