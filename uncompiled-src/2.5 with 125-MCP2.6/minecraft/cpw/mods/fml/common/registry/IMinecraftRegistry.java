package cpw.mods.fml.common.registry;

import net.minecraft.src.*;

public interface IMinecraftRegistry
{
    public abstract void removeSpawn(String s, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[]);

    public abstract void removeSpawn(Class class1, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[]);

    public abstract void removeBiome(BiomeGenBase biomegenbase);

    public abstract void addSpawn(String s, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[]);

    public abstract void addSpawn(Class class1, int i, int j, int k, EnumCreatureType enumcreaturetype, BiomeGenBase abiomegenbase[]);

    public abstract void addBiome(BiomeGenBase biomegenbase);

    public abstract void registerTileEntity(Class class1, String s);

    public abstract void registerEntityID(Class class1, String s, int i, int j, int k);

    public abstract void registerEntityID(Class class1, String s, int i);

    public abstract void registerBlock(Block block, Class class1);

    public abstract void registerBlock(Block block);

    public abstract void addSmelting(int i, ItemStack itemstack);

    public abstract void addShapelessRecipe(ItemStack itemstack, Object aobj[]);

    public abstract void addRecipe(ItemStack itemstack, Object aobj[]);
}
