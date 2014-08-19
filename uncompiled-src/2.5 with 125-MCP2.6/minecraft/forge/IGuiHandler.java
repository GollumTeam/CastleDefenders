package forge;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;

public interface IGuiHandler
{
    public abstract Object getGuiElement(int i, EntityPlayer entityplayer, World world, int j, int k, int l);
}
