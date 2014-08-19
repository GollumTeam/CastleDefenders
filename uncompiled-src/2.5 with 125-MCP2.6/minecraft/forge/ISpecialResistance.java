package forge;

import net.minecraft.src.Entity;
import net.minecraft.src.World;

public interface ISpecialResistance
{
    public abstract float getSpecialExplosionResistance(World world, int i, int j, int k, double d, double d1, double d2, Entity entity);
}
