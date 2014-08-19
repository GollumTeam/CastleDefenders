package forge;

import net.minecraft.src.ItemStack;

public interface IFuelHandler
{
    public abstract int getItemBurnTime(ItemStack itemstack);
}
