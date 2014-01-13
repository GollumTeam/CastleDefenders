package forge;

import net.minecraft.src.*;

public interface IHighlightHandler
{
    public abstract boolean onBlockHighlight(RenderGlobal renderglobal, EntityPlayer entityplayer, MovingObjectPosition movingobjectposition, int i, ItemStack itemstack, float f);
}
