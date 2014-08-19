package net.minecraft.src;

import forge.ISidedInventory;
import java.util.List;

public class TileEntityBrewingStand extends TileEntity implements IInventory, ISidedInventory
{
    private ItemStack brewingItemStacks[];
    private int brewTime;

    /**
     * an integer with each bit specifying whether that slot of the stand contains a potion
     */
    private int filledSlots;
    private int ingredientID;

    public TileEntityBrewingStand()
    {
        brewingItemStacks = new ItemStack[4];
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "container.brewing";
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return brewingItemStacks.length;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        if (brewTime > 0)
        {
            brewTime--;

            if (brewTime == 0)
            {
                brewPotions();
                onInventoryChanged();
            }
            else if (!canBrew())
            {
                brewTime = 0;
                onInventoryChanged();
            }
            else if (ingredientID != brewingItemStacks[3].itemID)
            {
                brewTime = 0;
                onInventoryChanged();
            }
        }
        else if (canBrew())
        {
            brewTime = 400;
            ingredientID = brewingItemStacks[3].itemID;
        }

        int i = getFilledSlots();

        if (i != filledSlots)
        {
            filledSlots = i;
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, i);
        }

        super.updateEntity();
    }

    public int getBrewTime()
    {
        return brewTime;
    }

    private boolean canBrew()
    {
        if (brewingItemStacks[3] != null && brewingItemStacks[3].stackSize > 0)
        {
            ItemStack itemstack = brewingItemStacks[3];

            if (!Item.itemsList[itemstack.itemID].isPotionIngredient())
            {
                return false;
            }

            boolean flag = false;

            for (int i = 0; i < 3; i++)
            {
                if (brewingItemStacks[i] == null || brewingItemStacks[i].itemID != Item.potion.shiftedIndex)
                {
                    continue;
                }

                int j = brewingItemStacks[i].getItemDamage();
                int k = getPotionResult(j, itemstack);

                if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
                {
                    flag = true;
                    break;
                }

                List list = Item.potion.getEffects(j);
                List list1 = Item.potion.getEffects(k);

                if (j > 0 && list == list1 || list != null && (list.equals(list1) || list1 == null) || j == k)
                {
                    continue;
                }

                flag = true;
                break;
            }

            return flag;
        }
        else
        {
            return false;
        }
    }

    private void brewPotions()
    {
        if (canBrew())
        {
            ItemStack itemstack = brewingItemStacks[3];

            for (int i = 0; i < 3; i++)
            {
                if (brewingItemStacks[i] == null || brewingItemStacks[i].itemID != Item.potion.shiftedIndex)
                {
                    continue;
                }

                int j = brewingItemStacks[i].getItemDamage();
                int k = getPotionResult(j, itemstack);
                List list = Item.potion.getEffects(j);
                List list1 = Item.potion.getEffects(k);

                if ((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null))
                {
                    if (j != k)
                    {
                        brewingItemStacks[i].setItemDamage(k);
                    }

                    continue;
                }

                if (!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
                {
                    brewingItemStacks[i].setItemDamage(k);
                }
            }

            if (Item.itemsList[itemstack.itemID].hasContainerItem())
            {
                brewingItemStacks[3] = new ItemStack(Item.itemsList[itemstack.itemID].getContainerItem());
            }
            else
            {
                brewingItemStacks[3].stackSize--;

                if (brewingItemStacks[3].stackSize <= 0)
                {
                    brewingItemStacks[3] = null;
                }
            }
        }
    }

    /**
     * The result of brewing a potion of the specified damage value with an ingredient itemstack.
     */
    private int getPotionResult(int par1, ItemStack par2ItemStack)
    {
        return par2ItemStack != null ? Item.itemsList[par2ItemStack.itemID].isPotionIngredient() ? PotionHelper.applyIngredient(par1, Item.itemsList[par2ItemStack.itemID].getPotionEffect()) : par1 : par1;
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        brewingItemStacks = new ItemStack[getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound.getByte("Slot");

            if (byte0 >= 0 && byte0 < brewingItemStacks.length)
            {
                brewingItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }

        brewTime = par1NBTTagCompound.getShort("BrewTime");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BrewTime", (short)brewTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < brewingItemStacks.length; i++)
        {
            if (brewingItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                brewingItemStacks[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return par1 < 0 || par1 >= brewingItemStacks.length ? null : brewingItemStacks[par1];
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (par1 >= 0 && par1 < brewingItemStacks.length)
        {
            ItemStack itemstack = brewingItemStacks[par1];
            brewingItemStacks[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (par1 >= 0 && par1 < brewingItemStacks.length)
        {
            ItemStack itemstack = brewingItemStacks[par1];
            brewingItemStacks[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        if (par1 >= 0 && par1 < brewingItemStacks.length)
        {
            brewingItemStacks[par1] = par2ItemStack;
        }
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 1;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this ? par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D : false;
    }

    public void openChest()
    {
    }

    public void closeChest()
    {
    }

    public void setBrewTime(int par1)
    {
        brewTime = par1;
    }

    /**
     * returns an integer with each bit specifying wether that slot of the stand contains a potion
     */
    public int getFilledSlots()
    {
        int i = 0;

        for (int j = 0; j < 3; j++)
        {
            if (brewingItemStacks[j] != null)
            {
                i |= 1 << j;
            }
        }

        return i;
    }

    public int getStartInventorySide(int i)
    {
        return i != 1 ? 0 : 3;
    }

    public int getSizeInventorySide(int i)
    {
        return i != 1 ? 3 : 1;
    }
}
