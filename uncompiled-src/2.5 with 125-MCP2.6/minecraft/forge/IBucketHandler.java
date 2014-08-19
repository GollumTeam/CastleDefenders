package forge;

import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface IBucketHandler
{
    public abstract ItemStack fillCustomBucket(World world, int i, int j, int k);
}
