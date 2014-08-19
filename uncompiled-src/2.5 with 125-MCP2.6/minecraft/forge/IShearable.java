package forge;

import java.util.ArrayList;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public interface IShearable
{
    public abstract boolean isShearable(ItemStack itemstack, World world, int i, int j, int k);

    public abstract ArrayList onSheared(ItemStack itemstack, World world, int i, int j, int k, int l);
}
