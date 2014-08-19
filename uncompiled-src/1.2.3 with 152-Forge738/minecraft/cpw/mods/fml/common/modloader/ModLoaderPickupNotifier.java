package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.IPickupNotifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;

public class ModLoaderPickupNotifier implements IPickupNotifier
{
    private BaseModProxy mod;

    public ModLoaderPickupNotifier(BaseModProxy var1)
    {
        this.mod = var1;
    }

    public void notifyPickup(EntityItem var1, EntityPlayer var2)
    {
        this.mod.onItemPickup(var2, var1.getEntityItem());
    }
}
