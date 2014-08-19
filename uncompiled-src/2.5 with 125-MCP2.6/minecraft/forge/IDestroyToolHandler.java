package forge;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public interface IDestroyToolHandler
{
    public abstract void onDestroyCurrentItem(EntityPlayer entityplayer, ItemStack itemstack);
}
