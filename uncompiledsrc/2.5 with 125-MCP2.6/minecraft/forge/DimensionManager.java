package forge;

import java.util.*;
import net.minecraft.src.*;

public class DimensionManager
{
    private static Hashtable providers = new Hashtable();
    private static Hashtable spawnSettings = new Hashtable();
    private static Hashtable worlds = new Hashtable();
    private static boolean hasInit = false;

    public DimensionManager()
    {
    }

    public static boolean registerDimension(int i, WorldProvider worldprovider, boolean flag)
    {
        if (providers.containsValue(Integer.valueOf(i)))
        {
            return false;
        }
        else
        {
            providers.put(Integer.valueOf(i), worldprovider);
            spawnSettings.put(Integer.valueOf(i), Boolean.valueOf(flag));
            return true;
        }
    }

    public static void init()
    {
        if (hasInit)
        {
            return;
        }
        else
        {
            registerDimension(0, new WorldProviderSurface(), true);
            registerDimension(-1, new WorldProviderHell(), true);
            registerDimension(1, new WorldProviderEnd(), false);
            return;
        }
    }

    public static WorldProvider getProvider(int i)
    {
        return (WorldProvider)providers.get(Integer.valueOf(i));
    }

    public static Integer[] getIDs()
    {
        return (Integer[])providers.keySet().toArray(new Integer[0]);
    }

    public static void setWorld(int i, World world)
    {
        worlds.put(Integer.valueOf(i), world);
    }

    public static World getWorld(int i)
    {
        return (World)worlds.get(Integer.valueOf(i));
    }

    public static World[] getWorlds()
    {
        return (World[])worlds.values().toArray(new World[0]);
    }

    public static boolean shouldLoadSpawn(int i)
    {
        return spawnSettings.contains(Integer.valueOf(i)) && ((Boolean)spawnSettings.get(Integer.valueOf(i))).booleanValue();
    }

    static
    {
        init();
    }
}
