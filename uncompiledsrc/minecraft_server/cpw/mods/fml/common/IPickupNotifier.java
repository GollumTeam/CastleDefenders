package cpw.mods.fml.common;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public interface IPickupNotifier
{
    void notifyPickup(EntityItem var1, EntityPlayer var2);
}
