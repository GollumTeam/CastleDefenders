package forge;

import net.minecraft.src.*;

public interface IMinecartCollisionHandler
{
    public abstract void onEntityCollision(EntityMinecart entityminecart, Entity entity);

    public abstract AxisAlignedBB getCollisionBox(EntityMinecart entityminecart, Entity entity);

    public abstract AxisAlignedBB getMinecartCollisionBox(EntityMinecart entityminecart);

    public abstract AxisAlignedBB getBoundingBox(EntityMinecart entityminecart);
}
