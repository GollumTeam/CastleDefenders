package forge;

import net.minecraft.src.*;

public interface ISpecialArmor
{
    public abstract ArmorProperties getProperties(EntityLiving entityliving, ItemStack itemstack, DamageSource damagesource, double d, int i);

    public abstract int getArmorDisplay(EntityPlayer entityplayer, ItemStack itemstack, int i);

    public abstract void damageArmor(EntityLiving entityliving, ItemStack itemstack, DamageSource damagesource, int i, int j);
}
