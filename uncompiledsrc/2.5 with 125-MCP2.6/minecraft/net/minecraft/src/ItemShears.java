package net.minecraft.src;

import forge.IShearable;
import java.util.*;

public class ItemShears extends Item
{
    public ItemShears(int par1)
    {
        super(par1);
        setMaxStackSize(1);
        setMaxDamage(238);
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
    {
        if (par2 != Block.leaves.blockID && par2 != Block.web.blockID && par2 != Block.tallGrass.blockID && par2 != Block.vine.blockID && !(Block.blocksList[par2] instanceof IShearable))
        {
            return super.onBlockDestroyed(par1ItemStack, par2, par3, par4, par5, par6EntityLiving);
        }
        else
        {
            return true;
        }
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockID == Block.web.blockID;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block.blockID == Block.web.blockID || par2Block.blockID == Block.leaves.blockID ? 15F : par2Block.blockID != Block.cloth.blockID ? super.getStrVsBlock(par1ItemStack, par2Block) : 5F;
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public void useItemOnEntity(ItemStack itemstack, EntityLiving entityliving)
    {
        if (entityliving.worldObj.isRemote)
        {
            return;
        }

        if (entityliving instanceof IShearable)
        {
            IShearable ishearable = (IShearable)entityliving;

            if (ishearable.isShearable(itemstack, entityliving.worldObj, (int)entityliving.posX, (int)entityliving.posY, (int)entityliving.posZ))
            {
                ArrayList arraylist = ishearable.onSheared(itemstack, entityliving.worldObj, (int)entityliving.posX, (int)entityliving.posY, (int)entityliving.posZ, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));

                for (Iterator iterator = arraylist.iterator(); iterator.hasNext();)
                {
                    ItemStack itemstack1 = (ItemStack)iterator.next();
                    EntityItem entityitem = entityliving.entityDropItem(itemstack1, 1.0F);
                    entityitem.motionY += entityliving.rand.nextFloat() * 0.05F;
                    entityitem.motionX += (entityliving.rand.nextFloat() - entityliving.rand.nextFloat()) * 0.1F;
                    entityitem.motionZ += (entityliving.rand.nextFloat() - entityliving.rand.nextFloat()) * 0.1F;
                }

                itemstack.damageItem(1, entityliving);
            }
        }
    }

    public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer entityplayer)
    {
        if (entityplayer.worldObj.isRemote)
        {
            return false;
        }

        int l = entityplayer.worldObj.getBlockId(i, j, k);

        if (Block.blocksList[l] != null && (Block.blocksList[l] instanceof IShearable))
        {
            IShearable ishearable = (IShearable)Block.blocksList[l];

            if (ishearable.isShearable(itemstack, entityplayer.worldObj, i, j, k))
            {
                ArrayList arraylist = ishearable.onSheared(itemstack, entityplayer.worldObj, i, j, k, EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemstack));
                EntityItem entityitem;

                for (Iterator iterator = arraylist.iterator(); iterator.hasNext(); entityplayer.worldObj.spawnEntityInWorld(entityitem))
                {
                    ItemStack itemstack1 = (ItemStack)iterator.next();
                    float f = 0.7F;
                    double d = (double)(entityplayer.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(entityplayer.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d2 = (double)(entityplayer.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    entityitem = new EntityItem(entityplayer.worldObj, (double)i + d, (double)j + d1, (double)k + d2, itemstack1);
                    entityitem.delayBeforeCanPickup = 10;
                }

                itemstack.damageItem(1, entityplayer);
                entityplayer.addStat(StatList.mineBlockStatArray[l], 1);
            }
        }

        return false;
    }
}
