package forge.oredict;

import forge.IOreHandler;
import java.util.*;
import net.minecraft.src.*;

public class OreDictionary
{
    private static int maxID = 0;
    private static HashMap oreIDs = new HashMap();
    private static HashMap oreStacks = new HashMap();
    private static ArrayList oreHandlers = new ArrayList();

    public OreDictionary()
    {
    }

    public static int getOreID(String s)
    {
        Integer integer = (Integer)oreIDs.get(s);

        if (integer == null)
        {
            integer = Integer.valueOf(maxID++);
            oreIDs.put(s, integer);
            oreStacks.put(integer, new ArrayList());
        }

        return integer.intValue();
    }

    public static String getOreName(int i)
    {
        for (Iterator iterator = oreIDs.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (i == ((Integer)entry.getValue()).intValue())
            {
                return (String)entry.getKey();
            }
        }

        return "Unknown";
    }

    public static ArrayList getOres(String s)
    {
        return getOres(Integer.valueOf(getOreID(s)));
    }

    public static ArrayList getOres(Integer integer)
    {
        ArrayList arraylist = (ArrayList)oreStacks.get(integer);

        if (arraylist == null)
        {
            arraylist = new ArrayList();
            oreStacks.put(integer, arraylist);
        }

        return arraylist;
    }

    public static void registerOreHandler(IOreHandler iorehandler)
    {
        oreHandlers.add(iorehandler);
        HashMap hashmap = (HashMap)oreIDs.clone();

        for (Iterator iterator = hashmap.entrySet().iterator(); iterator.hasNext();)
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
            Iterator iterator1 = getOres((Integer)entry.getValue()).iterator();

            while (iterator1.hasNext())
            {
                ItemStack itemstack = (ItemStack)iterator1.next();
                iorehandler.registerOre((String)entry.getKey(), itemstack);
            }
        }
    }

    public static void registerOre(String s, Item item)
    {
        registerOre(s, new ItemStack(item));
    }

    public static void registerOre(String s, Block block)
    {
        registerOre(s, new ItemStack(block));
    }

    public static void registerOre(String s, ItemStack itemstack)
    {
        registerOre(s, getOreID(s), itemstack);
    }

    public static void registerOre(int i, Item item)
    {
        registerOre(i, new ItemStack(item));
    }

    public static void registerOre(int i, Block block)
    {
        registerOre(i, new ItemStack(block));
    }

    public static void registerOre(int i, ItemStack itemstack)
    {
        registerOre(getOreName(i), i, itemstack);
    }

    private static void registerOre(String s, int i, ItemStack itemstack)
    {
        ArrayList arraylist = getOres(Integer.valueOf(i));
        itemstack = itemstack.copy();
        arraylist.add(itemstack);
        IOreHandler iorehandler;

        for (Iterator iterator = oreHandlers.iterator(); iterator.hasNext(); iorehandler.registerOre(s, itemstack))
        {
            iorehandler = (IOreHandler)iterator.next();
        }
    }
}
