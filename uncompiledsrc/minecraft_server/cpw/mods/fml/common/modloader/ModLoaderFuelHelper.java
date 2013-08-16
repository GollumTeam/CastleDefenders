package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.ItemStack;

public class ModLoaderFuelHelper implements IFuelHandler
{
    private BaseModProxy mod;

    public ModLoaderFuelHelper(BaseModProxy var1)
    {
        this.mod = var1;
    }

    public int getBurnTime(ItemStack var1)
    {
        return this.mod.addFuel(var1.itemID, var1.itemID);
    }
}
