package forge;

import net.minecraft.src.*;

public interface ICraftingHandler
{
    public abstract void onTakenFromCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory);
}
