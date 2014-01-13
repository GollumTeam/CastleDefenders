package forge;

import net.minecraft.src.*;

public interface ISaveEventHandler
{
    public abstract void onWorldLoad(World world);

    public abstract void onWorldSave(World world);

    public abstract void onChunkLoad(World world, Chunk chunk);

    public abstract void onChunkUnload(World world, Chunk chunk);

    public abstract void onChunkSaveData(World world, Chunk chunk, NBTTagCompound nbttagcompound);

    public abstract void onChunkLoadData(World world, Chunk chunk, NBTTagCompound nbttagcompound);
}
