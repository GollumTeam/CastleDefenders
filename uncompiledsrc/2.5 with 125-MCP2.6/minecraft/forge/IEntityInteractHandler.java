package forge;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;

public interface IEntityInteractHandler
{
    public abstract boolean onEntityInteract(EntityPlayer entityplayer, Entity entity, boolean flag);
}
