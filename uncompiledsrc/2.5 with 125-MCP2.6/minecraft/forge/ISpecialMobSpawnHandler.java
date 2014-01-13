package forge;

import net.minecraft.src.EntityLiving;
import net.minecraft.src.World;

public interface ISpecialMobSpawnHandler
{
    public abstract boolean onSpecialEntitySpawn(EntityLiving entityliving, World world, float f, float f1, float f2);
}
