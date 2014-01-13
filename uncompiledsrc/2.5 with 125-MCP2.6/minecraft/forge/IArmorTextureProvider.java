package forge;

import net.minecraft.src.ItemStack;

public interface IArmorTextureProvider
{
    public abstract String getArmorTextureFile(ItemStack itemstack);
}
