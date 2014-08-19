package cpw.mods.fml.common.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.VillagerRegistry$IVillageCreationHandler;
import cpw.mods.fml.common.registry.VillagerRegistry$IVillageTradeHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.gen.structure.ComponentVillageStartPiece;
import net.minecraft.world.gen.structure.StructureVillagePieceWeight;

public class VillagerRegistry
{
    private static final VillagerRegistry INSTANCE = new VillagerRegistry();
    private Multimap tradeHandlers = ArrayListMultimap.create();
    private Map villageCreationHandlers = Maps.newHashMap();
    private Map newVillagers = Maps.newHashMap();
    private List newVillagerIds = Lists.newArrayList();

    public static VillagerRegistry instance()
    {
        return INSTANCE;
    }

    public void registerVillagerType(int var1, String var2)
    {
        if (this.newVillagers.containsKey(Integer.valueOf(var1)))
        {
            FMLLog.severe("Attempt to register duplicate villager id %d", new Object[] {Integer.valueOf(var1)});
            throw new RuntimeException();
        }
        else
        {
            this.newVillagers.put(Integer.valueOf(var1), var2);
            this.newVillagerIds.add(Integer.valueOf(var1));
        }
    }

    public void registerVillageCreationHandler(VillagerRegistry$IVillageCreationHandler var1)
    {
        this.villageCreationHandlers.put(var1.getComponentClass(), var1);
    }

    public void registerVillageTradeHandler(int var1, VillagerRegistry$IVillageTradeHandler var2)
    {
        this.tradeHandlers.put(Integer.valueOf(var1), var2);
    }

    public static String getVillagerSkin(int var0, String var1)
    {
        return instance().newVillagers.containsKey(Integer.valueOf(var0)) ? (String)instance().newVillagers.get(Integer.valueOf(var0)) : var1;
    }

    public static Collection getRegisteredVillagers()
    {
        return Collections.unmodifiableCollection(instance().newVillagerIds);
    }

    public static void manageVillagerTrades(MerchantRecipeList var0, EntityVillager var1, int var2, Random var3)
    {
        Iterator var4 = instance().tradeHandlers.get(Integer.valueOf(var2)).iterator();

        while (var4.hasNext())
        {
            VillagerRegistry$IVillageTradeHandler var5 = (VillagerRegistry$IVillageTradeHandler)var4.next();
            var5.manipulateTradesForVillager(var1, var0, var3);
        }
    }

    public static void addExtraVillageComponents(ArrayList var0, Random var1, int var2)
    {
        ArrayList var3 = var0;
        Iterator var4 = instance().villageCreationHandlers.values().iterator();

        while (var4.hasNext())
        {
            VillagerRegistry$IVillageCreationHandler var5 = (VillagerRegistry$IVillageCreationHandler)var4.next();
            var3.add(var5.getVillagePieceWeight(var1, var2));
        }
    }

    public static Object getVillageComponent(StructureVillagePieceWeight var0, ComponentVillageStartPiece var1, List var2, Random var3, int var4, int var5, int var6, int var7, int var8)
    {
        return ((VillagerRegistry$IVillageCreationHandler)instance().villageCreationHandlers.get(var0.villagePieceClass)).buildComponent(var0, var1, var2, var3, var4, var5, var6, var7, var8);
    }

    public static void addEmeraldBuyRecipe(EntityVillager var0, MerchantRecipeList var1, Random var2, Item var3, float var4, int var5, int var6)
    {
        if (var5 > 0 && var6 > 0)
        {
            EntityVillager.villagerStockList.put(Integer.valueOf(var3.itemID), new Tuple(Integer.valueOf(var5), Integer.valueOf(var6)));
        }

        EntityVillager.addMerchantItem(var1, var3.getMaxDamage(), var2, var4);
    }

    public static void addEmeraldSellRecipe(EntityVillager var0, MerchantRecipeList var1, Random var2, Item var3, float var4, int var5, int var6)
    {
        if (var5 > 0 && var6 > 0)
        {
            EntityVillager.blacksmithSellingList.put(Integer.valueOf(var3.itemID), new Tuple(Integer.valueOf(var5), Integer.valueOf(var6)));
        }

        EntityVillager.addBlacksmithItem(var1, var3.getMaxDamage(), var2, var4);
    }

    public static void applyRandomTrade(EntityVillager var0, Random var1)
    {
        int var2 = instance().newVillagerIds.size();
        int var3 = var1.nextInt(5 + var2);
        var0.setProfession(var3 < 5 ? var3 : ((Integer)instance().newVillagerIds.get(var3 - 5)).intValue());
    }
}
