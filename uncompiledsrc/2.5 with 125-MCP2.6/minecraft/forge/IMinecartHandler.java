package forge;

import net.minecraft.src.*;

public interface IMinecartHandler
{
    public abstract void onMinecartUpdate(EntityMinecart entityminecart, int i, int j, int k);

    public abstract void onMinecartEntityCollision(EntityMinecart entityminecart, Entity entity);

    public abstract boolean onMinecartInteract(EntityMinecart entityminecart, EntityPlayer entityplayer, boolean flag);
}
