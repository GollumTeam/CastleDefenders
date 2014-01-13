package net.minecraft.src;

import forge.IShearable;
import java.util.ArrayList;

public class EntityMooshroom extends EntityCow implements IShearable
{
    public EntityMooshroom(World par1World)
    {
        super(par1World);
        texture = "/mob/redcow.png";
        setSize(0.9F, 1.3F);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

        if (itemstack != null && itemstack.itemID == Item.bowlEmpty.shiftedIndex && getGrowingAge() >= 0)
        {
            if (itemstack.stackSize == 1)
            {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.bowlSoup));
                return true;
            }

            if (par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bowlSoup)) && !par1EntityPlayer.capabilities.isCreativeMode)
            {
                par1EntityPlayer.inventory.decrStackSize(par1EntityPlayer.inventory.currentItem, 1);
                return true;
            }
        }

        return super.interact(par1EntityPlayer);
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal)
    {
        return new EntityMooshroom(worldObj);
    }

    public boolean isShearable(ItemStack itemstack, World world, int i, int j, int k)
    {
        return getGrowingAge() >= 0;
    }

    public ArrayList onSheared(ItemStack itemstack, World world, int i, int j, int k, int l)
    {
        setDead();
        EntityCow entitycow = new EntityCow(worldObj);
        entitycow.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
        entitycow.setEntityHealth(getHealth());
        entitycow.renderYawOffset = renderYawOffset;
        worldObj.spawnEntityInWorld(entitycow);
        worldObj.spawnParticle("largeexplode", posX, posY + (double)(height / 2.0F), posZ, 0.0D, 0.0D, 0.0D);
        ArrayList arraylist = new ArrayList();

        for (int i1 = 0; i1 < 5; i1++)
        {
            arraylist.add(new ItemStack(Block.mushroomRed));
        }

        return arraylist;
    }
}
