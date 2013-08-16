package cpw.mods.fml.common;

import java.util.Map;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;

public interface WorldAccessContainer
{
    NBTTagCompound getDataForWriting(SaveHandler var1, WorldInfo var2);

    void readData(SaveHandler var1, WorldInfo var2, Map var3, NBTTagCompound var4);
}
