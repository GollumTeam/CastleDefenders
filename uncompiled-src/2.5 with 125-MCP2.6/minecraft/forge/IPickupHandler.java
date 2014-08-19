package forge;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;

public interface IPickupHandler
{
    public abstract boolean onItemPickup(EntityPlayer entityplayer, EntityItem entityitem);
}
