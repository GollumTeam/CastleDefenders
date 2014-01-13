package forge;

import net.minecraft.src.Entity;

public interface IThrowableEntity
{
    public abstract Entity getThrower();

    public abstract void setThrower(Entity entity);
}
