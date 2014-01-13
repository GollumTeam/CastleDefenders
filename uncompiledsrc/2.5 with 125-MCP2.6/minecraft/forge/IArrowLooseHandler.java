package forge;

import net.minecraft.src.*;

public interface IArrowLooseHandler
{
    public abstract boolean onArrowLoose(ItemStack itemstack, World world, EntityPlayer entityplayer, int i);
}
