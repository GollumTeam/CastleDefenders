package forge;

import net.minecraft.src.ItemStack;

public interface IOreHandler
{
    public abstract void registerOre(String s, ItemStack itemstack);
}
