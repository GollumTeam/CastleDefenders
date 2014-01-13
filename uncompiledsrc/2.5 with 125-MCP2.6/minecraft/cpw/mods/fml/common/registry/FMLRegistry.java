package cpw.mods.fml.common.registry;

import net.minecraft.src.*;

public class FMLRegistry
{
    private static IMinecraftRegistry instance;

    public FMLRegistry()
    {
    }

    public static void registerRegistry(IMinecraftRegistry iminecraftregistry)
    {
        if (instance != null)
        {
            throw new RuntimeException("Illegal attempt to replace FML registry");
        }
        else
        {
            instance = iminecraftregistry;
            return;
        }
    }

    public static void addRecipe(ItemStack itemstack, Object aobj[])
    {
        instance.addRecipe(itemstack, aobj);
    }

    public static void addShapelessRecipe(ItemStack itemstack, Object aobj[])
    {
        instance.addShapelessRecipe(itemstack, aobj);
    }

    public static void addSmelting(int i, ItemStack itemstack)
    {
        instance.addSmelting(i, itemstack);
    }

    public static void registerBlock(Block block)
    {
        instance.registerBlock(block);
    }

    public static void registerBlock(Block block, Class class1)
    {
        instance.registerBlock(block, class1);
    }

    public static void registerEntityID(Class class1, String s, int i)
    {
        instance.registerEntityID(class1, s, i);
    }

    public static void registerEntityID(Class class1, String s, int i, int j, int k)
    {
        instance.registerEntityID(class1, s, i, j, k);
    }

    public static void registerTileEntity(Class class1, String s)
    {
        instance.registerTileEntity(class1, s);
    }

    public static void addBiome(BiomeGenBase biomegenbase)
    {
        instance.addBiome(biomegenbase);
    }

    public static void addSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        instance.addSpawn(class1, i, j, k, enumcreaturetype, abiomegenbase);
    }

    public static void addSpawn(String s, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        instance.addSpawn(s, i, j, k, enumcreaturetype, abiomegenbase);
    }

    public static void removeBiome(BiomeGenBase biomegenbase)
    {
        instance.removeBiome(biomegenbase);
    }

    public static void removeSpawn(Class class1, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        instance.removeSpawn(class1, enumcreaturetype, abiomegenbase);
    }

    public static void removeSpawn(String s, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[])
    {
        instance.removeSpawn(s, enumcreaturetype, abiomegenbase);
    }

    public static IMinecraftRegistry instance()
    {
        return instance;
    }
}
