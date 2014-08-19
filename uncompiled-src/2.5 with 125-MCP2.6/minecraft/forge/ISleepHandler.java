package forge;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumStatus;

public interface ISleepHandler
{
    public abstract EnumStatus sleepInBedAt(EntityPlayer entityplayer, int i, int j, int k);
}
