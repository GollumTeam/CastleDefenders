package forge;

import net.minecraft.src.World;

public interface IBonemealHandler
{
    public abstract boolean onUseBonemeal(World world, int i, int j, int k, int l);
}
