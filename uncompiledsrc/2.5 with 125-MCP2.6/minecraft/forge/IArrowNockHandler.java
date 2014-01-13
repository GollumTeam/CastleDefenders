package forge;

import net.minecraft.src.*;

public interface IArrowNockHandler
{
    public abstract ItemStack onArrowNock(ItemStack itemstack, World world, EntityPlayer entityplayer);
}
