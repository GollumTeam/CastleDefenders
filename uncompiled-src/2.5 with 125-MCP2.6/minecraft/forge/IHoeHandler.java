package forge;

import net.minecraft.src.*;

public interface IHoeHandler
{
    public abstract boolean onUseHoe(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k);
}
