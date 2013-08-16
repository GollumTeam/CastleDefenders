package cpw.mods.fml.common.registry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.primitives.UnsignedBytes;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.EntityRegistry$EntityRegistration;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;

public class EntityRegistry
{
    private static final EntityRegistry INSTANCE = new EntityRegistry();
    private BitSet availableIndicies = new BitSet(256);
    private ListMultimap entityRegistrations = ArrayListMultimap.create();
    private Map entityNames = Maps.newHashMap();
    private BiMap entityClassRegistrations = HashBiMap.create();

    public static EntityRegistry instance()
    {
        return INSTANCE;
    }

    private EntityRegistry()
    {
        this.availableIndicies.set(1, 255);
        Iterator var1 = EntityList.IDtoClassMapping.keySet().iterator();

        while (var1.hasNext())
        {
            Object var2 = var1.next();
            this.availableIndicies.clear(((Integer)var2).intValue());
        }
    }

    public static void registerModEntity(Class var0, String var1, int var2, Object var3, int var4, int var5, boolean var6)
    {
        instance().doModEntityRegistration(var0, var1, var2, var3, var4, var5, var6);
    }

    private void doModEntityRegistration(Class var1, String var2, int var3, Object var4, int var5, int var6, boolean var7)
    {
        ModContainer var8 = FMLCommonHandler.instance().findContainerFor(var4);
        EntityRegistry$EntityRegistration var9 = new EntityRegistry$EntityRegistration(this, var8, var1, var2, var3, var5, var6, var7);

        try
        {
            this.entityClassRegistrations.put(var1, var9);
            this.entityNames.put(var2, var8);

            if (!EntityList.classToStringMapping.containsKey(var1))
            {
                String var10 = String.format("%s.%s", new Object[] {var8.getModId(), var2});
                EntityList.classToStringMapping.put(var1, var10);
                EntityList.stringToClassMapping.put(var10, var1);
                FMLLog.finest("Automatically registered mod %s entity %s as %s", new Object[] {var8.getModId(), var2, var10});
            }
            else
            {
                FMLLog.fine("Skipping automatic mod %s entity registration for already registered class %s", new Object[] {var8.getModId(), var1.getName()});
            }
        }
        catch (IllegalArgumentException var11)
        {
            FMLLog.log(Level.WARNING, (Throwable)var11, "The mod %s tried to register the entity (name,class) (%s,%s) one or both of which are already registered", new Object[] {var8.getModId(), var2, var1.getName()});
            return;
        }

        this.entityRegistrations.put(var8, var9);
    }

    public static void registerGlobalEntityID(Class var0, String var1, int var2)
    {
        if (EntityList.classToStringMapping.containsKey(var0))
        {
            ModContainer var3 = Loader.instance().activeModContainer();
            String var4 = "unknown";

            if (var3 != null)
            {
                var4 = var3.getModId();
            }
            else
            {
                FMLLog.severe("There is a rogue mod failing to register entities from outside the context of mod loading. This is incredibly dangerous and should be stopped.", new Object[0]);
            }

            FMLLog.warning("The mod %s tried to register the entity class %s which was already registered - if you wish to override default naming for FML mod entities, register it here first", new Object[] {var4, var0});
        }
        else
        {
            var2 = instance().validateAndClaimId(var2);
            EntityList.addMapping(var0, var1, var2);
        }
    }

    private int validateAndClaimId(int var1)
    {
        int var2 = var1;

        if (var1 < -128)
        {
            FMLLog.warning("Compensating for modloader out of range compensation by mod : entityId %d for mod %s is now %d", new Object[] {Integer.valueOf(var1), Loader.instance().activeModContainer().getModId(), Integer.valueOf(var1)});
            var2 = var1 + 3000;
        }

        if (var2 < 0)
        {
            var2 += 127;
        }

        try
        {
            UnsignedBytes.checkedCast((long)var2);
        }
        catch (IllegalArgumentException var4)
        {
            FMLLog.log(Level.SEVERE, "The entity ID %d for mod %s is not an unsigned byte and may not work", new Object[] {Integer.valueOf(var1), Loader.instance().activeModContainer().getModId()});
        }

        if (!this.availableIndicies.get(var2))
        {
            FMLLog.severe("The mod %s has attempted to register an entity ID %d which is already reserved. This could cause severe problems", new Object[] {Loader.instance().activeModContainer().getModId(), Integer.valueOf(var1)});
        }

        this.availableIndicies.clear(var2);
        return var2;
    }

    public static void registerGlobalEntityID(Class var0, String var1, int var2, int var3, int var4)
    {
        if (EntityList.classToStringMapping.containsKey(var0))
        {
            ModContainer var5 = Loader.instance().activeModContainer();
            String var6 = "unknown";

            if (var5 != null)
            {
                var6 = var5.getModId();
            }
            else
            {
                FMLLog.severe("There is a rogue mod failing to register entities from outside the context of mod loading. This is incredibly dangerous and should be stopped.", new Object[0]);
            }

            FMLLog.warning("The mod %s tried to register the entity class %s which was already registered - if you wish to override default naming for FML mod entities, register it here first", new Object[] {var6, var0});
        }
        else
        {
            instance().validateAndClaimId(var2);
            EntityList.addMapping(var0, var1, var2, var3, var4);
        }
    }

    public static void addSpawn(Class var0, int var1, int var2, int var3, EnumCreatureType var4, BiomeGenBase ... var5)
    {
        BiomeGenBase[] var6 = var5;
        int var7 = var5.length;
        int var8 = 0;

        while (var8 < var7)
        {
            BiomeGenBase var9 = var6[var8];
            List var10 = var9.getSpawnableList(var4);
            Iterator var11 = var10.iterator();

            while (true)
            {
                if (var11.hasNext())
                {
                    SpawnListEntry var12 = (SpawnListEntry)var11.next();

                    if (var12.entityClass != var0)
                    {
                        continue;
                    }

                    var12.itemWeight = var1;
                    var12.minGroupCount = var2;
                    var12.maxGroupCount = var3;
                }

                var10.add(new SpawnListEntry(var0, var1, var2, var3));
                ++var8;
                break;
            }
        }
    }

    public static void addSpawn(String var0, int var1, int var2, int var3, EnumCreatureType var4, BiomeGenBase ... var5)
    {
        Class var6 = (Class)EntityList.stringToClassMapping.get(var0);

        if (EntityLiving.class.isAssignableFrom(var6))
        {
            addSpawn(var6, var1, var2, var3, var4, var5);
        }
    }

    public static void removeSpawn(Class var0, EnumCreatureType var1, BiomeGenBase ... var2)
    {
        BiomeGenBase[] var3 = var2;
        int var4 = var2.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            BiomeGenBase var6 = var3[var5];
            Iterator var7 = var6.getSpawnableList(var1).iterator();

            while (var7.hasNext())
            {
                SpawnListEntry var8 = (SpawnListEntry)var7.next();

                if (var8.entityClass == var0)
                {
                    var7.remove();
                }
            }
        }
    }

    public static void removeSpawn(String var0, EnumCreatureType var1, BiomeGenBase ... var2)
    {
        Class var3 = (Class)EntityList.stringToClassMapping.get(var0);

        if (EntityLiving.class.isAssignableFrom(var3))
        {
            removeSpawn(var3, var1, var2);
        }
    }

    public static int findGlobalUniqueEntityId()
    {
        int var0 = instance().availableIndicies.nextSetBit(0);

        if (var0 < 0)
        {
            throw new RuntimeException("No more entity indicies left");
        }
        else
        {
            return var0;
        }
    }

    public EntityRegistry$EntityRegistration lookupModSpawn(Class var1, boolean var2)
    {
        Class var3 = var1;

        do
        {
            EntityRegistry$EntityRegistration var4 = (EntityRegistry$EntityRegistration)this.entityClassRegistrations.get(var3);

            if (var4 != null)
            {
                return var4;
            }

            var3 = var3.getSuperclass();
            var2 = !Object.class.equals(var3);
        }
        while (var2);

        return null;
    }

    public EntityRegistry$EntityRegistration lookupModSpawn(ModContainer var1, int var2)
    {
        Iterator var3 = this.entityRegistrations.get(var1).iterator();
        EntityRegistry$EntityRegistration var4;

        do
        {
            if (!var3.hasNext())
            {
                return null;
            }

            var4 = (EntityRegistry$EntityRegistration)var3.next();
        }
        while (var4.getModEntityId() != var2);

        return var4;
    }

    public boolean tryTrackingEntity(EntityTracker var1, Entity var2)
    {
        EntityRegistry$EntityRegistration var3 = this.lookupModSpawn(var2.getClass(), true);

        if (var3 != null)
        {
            var1.addEntityToTracker(var2, var3.getTrackingRange(), var3.getUpdateFrequency(), var3.sendsVelocityUpdates());
            return true;
        }
        else
        {
            return false;
        }
    }

    @Deprecated
    public static EntityRegistry$EntityRegistration registerModLoaderEntity(Object var0, Class var1, int var2, int var3, int var4, boolean var5)
    {
        String var6 = (String)EntityList.classToStringMapping.get(var1);

        if (var6 == null)
        {
            throw new IllegalArgumentException(String.format("The ModLoader mod %s has tried to register an entity tracker for a non-existent entity type %s", new Object[] {Loader.instance().activeModContainer().getModId(), var1.getCanonicalName()}));
        }
        else
        {
            instance().doModEntityRegistration(var1, var6, var2, var0, var3, var4, var5);
            return (EntityRegistry$EntityRegistration)instance().entityClassRegistrations.get(var1);
        }
    }
}
