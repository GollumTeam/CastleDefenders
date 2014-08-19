package forge;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.src.*;

public class ArmorProperties implements Comparable
{
    public int Priority;
    public int AbsorbMax;
    public double AbsorbRatio;
    public int Slot;
    private static final boolean DEBUG = false;

    public ArmorProperties(int i, double d, int j)
    {
        Priority = 0;
        AbsorbMax = 0x7fffffff;
        AbsorbRatio = 0.0D;
        Slot = 0;
        Priority = i;
        AbsorbRatio = d;
        AbsorbMax = j;
    }

    public static int ApplyArmor(EntityLiving entityliving, ItemStack aitemstack[], DamageSource damagesource, double d)
    {
        d *= 25D;
        ArrayList arraylist = new ArrayList();

        for (int i = 0; i < aitemstack.length; i++)
        {
            ItemStack itemstack = aitemstack[i];

            if (itemstack == null)
            {
                continue;
            }

            ArmorProperties armorproperties = null;

            if (itemstack.getItem() instanceof ISpecialArmor)
            {
                ISpecialArmor ispecialarmor = (ISpecialArmor)itemstack.getItem();
                armorproperties = ispecialarmor.getProperties(entityliving, itemstack, damagesource, d / 25D, i).copy();
            }
            else if ((itemstack.getItem() instanceof ItemArmor) && !damagesource.isUnblockable())
            {
                ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
                armorproperties = new ArmorProperties(0, (double)itemarmor.damageReduceAmount / 25D, (itemarmor.getMaxDamage() + 1) - itemstack.getItemDamage());
            }

            if (armorproperties != null)
            {
                armorproperties.Slot = i;
                arraylist.add(armorproperties);
            }
        }

        if (arraylist.size() > 0)
        {
            ArmorProperties aarmorproperties[] = (ArmorProperties[])arraylist.toArray(new ArmorProperties[0]);
            StandardizeList(aarmorproperties, d);
            int j = aarmorproperties[0].Priority;
            double d1 = 0.0D;
            ArmorProperties aarmorproperties1[] = aarmorproperties;
            int k = aarmorproperties1.length;

            for (int l = 0; l < k; l++)
            {
                ArmorProperties armorproperties1 = aarmorproperties1[l];

                if (j != armorproperties1.Priority)
                {
                    d -= d * d1;
                    d1 = 0.0D;
                    j = armorproperties1.Priority;
                }

                d1 += armorproperties1.AbsorbRatio;
                double d2 = d * armorproperties1.AbsorbRatio;

                if (d2 <= 0.0D)
                {
                    continue;
                }

                ItemStack itemstack1 = aitemstack[armorproperties1.Slot];
                int i1 = (int)(d2 / 25D >= 1.0D ? d2 / 25D : 1.0D);

                if (itemstack1.getItem() instanceof ISpecialArmor)
                {
                    ((ISpecialArmor)itemstack1.getItem()).damageArmor(entityliving, itemstack1, damagesource, i1, armorproperties1.Slot);
                }
                else
                {
                    itemstack1.damageItem(i1, entityliving);
                }

                if (itemstack1.stackSize > 0)
                {
                    continue;
                }

                if (entityliving instanceof EntityPlayer)
                {
                    itemstack1.onItemDestroyedByUse((EntityPlayer)entityliving);
                }

                aitemstack[armorproperties1.Slot] = null;
            }

            d -= d * d1;
        }

        d += entityliving.carryoverDamage;
        entityliving.carryoverDamage = (int)d % 25;
        return (int)(d / 25D);
    }

    private static void StandardizeList(ArmorProperties aarmorproperties[], double d)
    {
        Arrays.sort(aarmorproperties);
        int i = 0;
        double d1 = 0.0D;
        int j = aarmorproperties[0].Priority;
        int k = 0;
        boolean flag = false;
        boolean flag1 = false;

        for (int l = 0; l < aarmorproperties.length; l++)
        {
            d1 += aarmorproperties[l].AbsorbRatio;

            if (l != aarmorproperties.length - 1 && aarmorproperties[l].Priority == j)
            {
                continue;
            }

            if (aarmorproperties[l].Priority != j)
            {
                d1 -= aarmorproperties[l].AbsorbRatio;
                l--;
                flag = true;
            }

            if (d1 > 1.0D)
            {
                int i1 = i;

                do
                {
                    if (i1 > l)
                    {
                        break;
                    }

                    double d2 = aarmorproperties[i1].AbsorbRatio / d1;

                    if (d2 * d > (double)aarmorproperties[i1].AbsorbMax)
                    {
                        aarmorproperties[i1].AbsorbRatio = (double)aarmorproperties[i1].AbsorbMax / d;
                        d1 = 0.0D;

                        for (int i2 = k; i2 <= i1; i2++)
                        {
                            d1 += aarmorproperties[i2].AbsorbRatio;
                        }

                        i = i1 + 1;
                        l = i1;
                        break;
                    }

                    aarmorproperties[i1].AbsorbRatio = d2;
                    flag1 = true;
                    i1++;
                }
                while (true);

                if (!flag || !flag1)
                {
                    continue;
                }

                d -= d * d1;
                d1 = 0.0D;
                i = l + 1;
                j = aarmorproperties[i].Priority;
                k = i;
                flag = false;
                flag1 = false;

                if (d > 0.0D)
                {
                    continue;
                }

                for (int j1 = l + 1; j1 < aarmorproperties.length; j1++)
                {
                    aarmorproperties[j1].AbsorbRatio = 0.0D;
                }

                break;
            }

            for (int k1 = i; k1 <= l; k1++)
            {
                d1 -= aarmorproperties[k1].AbsorbRatio;

                if (d * aarmorproperties[k1].AbsorbRatio > (double)aarmorproperties[k1].AbsorbMax)
                {
                    aarmorproperties[k1].AbsorbRatio = (double)aarmorproperties[k1].AbsorbMax / d;
                }

                d1 += aarmorproperties[k1].AbsorbRatio;
            }

            d -= d * d1;
            d1 = 0.0D;

            if (l == aarmorproperties.length - 1)
            {
                continue;
            }

            i = l + 1;
            j = aarmorproperties[i].Priority;
            k = i;
            flag = false;

            if (d > 0.0D)
            {
                continue;
            }

            for (int l1 = l + 1; l1 < aarmorproperties.length; l1++)
            {
                aarmorproperties[l1].AbsorbRatio = 0.0D;
            }

            break;
        }
    }

    public int compareTo(ArmorProperties armorproperties)
    {
        if (armorproperties.Priority != Priority)
        {
            return armorproperties.Priority - Priority;
        }
        else
        {
            double d = AbsorbRatio != 0.0D ? ((double)AbsorbMax * 100D) / AbsorbRatio : 0.0D;
            double d1 = armorproperties.AbsorbRatio != 0.0D ? ((double)armorproperties.AbsorbMax * 100D) / armorproperties.AbsorbRatio : 0.0D;
            return (int)(d - d1);
        }
    }

    public String toString()
    {
        return String.format("%d, %d, %f, %d", new Object[]
                {
                    Integer.valueOf(Priority), Integer.valueOf(AbsorbMax), Double.valueOf(AbsorbRatio), Integer.valueOf(AbsorbRatio != 0.0D ? (int)(((double)AbsorbMax * 100D) / AbsorbRatio) : 0)
                });
    }

    public ArmorProperties copy()
    {
        return new ArmorProperties(Priority, AbsorbRatio, AbsorbMax);
    }

    public int compareTo(Object obj)
    {
        return compareTo((ArmorProperties)obj);
    }
}
