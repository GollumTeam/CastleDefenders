package net.minecraft.src;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.FMLRegistry;
import cpw.mods.fml.common.registry.IMinecraftRegistry;
import java.lang.reflect.Constructor;
import java.util.*;

public class ClientRegistry implements IMinecraftRegistry
{
    static final boolean $assertionsDisabled;

    public ClientRegistry()
    {
    }

    public static ClientRegistry instance()
    {
        return (ClientRegistry)FMLRegistry.instance();
    }

    public void addRecipe(ItemStack itemstack, Object aobj[])
    {
        CraftingManager.getInstance().addRecipe(itemstack, aobj);
    }

    public void addShapelessRecipe(ItemStack itemstack, Object aobj[])
    {
        CraftingManager.getInstance().addShapelessRecipe(itemstack, aobj);
    }

    public void addSmelting(int i, ItemStack itemstack)
    {
        FurnaceRecipes.smelting().addSmelting(i, itemstack);
    }

    public void registerBlock(Block block)
    {
        registerBlock(block, net.minecraft.src.ItemBlock.class);
    }

    public void registerBlock(Block block, Class class1)
    {
        try
        {
            if (!$assertionsDisabled && block == null)
            {
                throw new AssertionError("registerBlock: block cannot be null");
            }

            if (!$assertionsDisabled && class1 == null)
            {
                throw new AssertionError("registerBlock: itemclass cannot be null");
            }

            int i = block.blockID - 256;
            class1.getConstructor(new Class[]
                    {
                        Integer.TYPE
                    }).newInstance(new Object[]
                            {
                                Integer.valueOf(i)
                            });
        }
        catch (Exception exception) { }
    }

    public void registerEntityID(Class class1, String s, int i)
    {
        EntityList.addNewEntityListMapping(class1, s, i);
    }

    public void registerEntityID(Class class1, String s, int i, int j, int k)
    {
        EntityList.addNewEntityListMapping(class1, s, i, j, k);
    }

    public void registerTileEntity(Class class1, String s)
    {
        TileEntity.addNewTileEntityMapping(class1, s);
    }

    public void registerTileEntity(Class class1, String s, TileEntitySpecialRenderer tileentityspecialrenderer)
    {
        registerTileEntity(class1, s);
        TileEntityRenderer.setTileEntityRenderer(class1, tileentityspecialrenderer);
    }

    public void addBiome(BiomeGenBase biomegenbase)
    {
        FMLClientHandler.instance().addBiomeToDefaultWorldGenerator(biomegenbase);
    }

    public void addSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        BiomeGenBase abiomegenbase1[] = abiomegenbase;
        int l = abiomegenbase1.length;

        for (int i1 = 0; i1 < l; i1++)
        {
            BiomeGenBase biomegenbase = abiomegenbase1[i1];
            List list = biomegenbase.getSpawnableList(enumcreaturetype);
            Iterator iterator = list.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                SpawnListEntry spawnlistentry = (SpawnListEntry)iterator.next();

                if (spawnlistentry.entityClass != class1)
                {
                    continue;
                }

                spawnlistentry.itemWeight = i;
                spawnlistentry.minGroupCount = j;
                spawnlistentry.maxGroupCount = k;
                break;
            }
            while (true);

            list.add(new SpawnListEntry(class1, i, j, k));
        }
    }

    public void addSpawn(String s, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        Class class1 = (Class)EntityList.getEntityToClassMapping().get(s);

        if ((net.minecraft.src.EntityLiving.class).isAssignableFrom(class1))
        {
            addSpawn(class1, i, j, k, enumcreaturetype, abiomegenbase);
        }
    }

    public void removeBiome(BiomeGenBase biomegenbase)
    {
        FMLClientHandler.instance().removeBiomeFromDefaultWorldGenerator(biomegenbase);
    }

    public void removeSpawn(Class class1, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        BiomeGenBase abiomegenbase1[] = abiomegenbase;
        int i = abiomegenbase1.length;
        label0:

        for (int j = 0; j < i; j++)
        {
            BiomeGenBase biomegenbase = abiomegenbase1[j];
            List list = biomegenbase.getSpawnableList(enumcreaturetype);
            Iterator iterator = Collections.unmodifiableList(list).iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    continue label0;
                }

                SpawnListEntry spawnlistentry = (SpawnListEntry)iterator.next();

                if (spawnlistentry.entityClass == class1)
                {
                    list.remove(spawnlistentry);
                }
            }
            while (true);
        }
    }

    public void removeSpawn(String s, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        Class class1 = (Class)EntityList.getEntityToClassMapping().get(s);

        if ((net.minecraft.src.EntityLiving.class).isAssignableFrom(class1))
        {
            removeSpawn(class1, enumcreaturetype, abiomegenbase);
        }
    }

    static
    {
        $assertionsDisabled = !(net.minecraft.src.ClientRegistry.class).desiredAssertionStatus();
    }
}
