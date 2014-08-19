package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockNetherStalk extends BlockFlower
{
    protected BlockNetherStalk(int par1)
    {
        super(par1, 226);
        setTickRandomly(true);
        float f = 0.5F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean canThisPlantGrowOnThisBlockID(int par1)
    {
        return par1 == Block.slowSand.blockID;
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int i = par1World.getBlockMetadata(par2, par3, par4);

        if (i < 3)
        {
            BiomeGenBase biomegenbase = par1World.getBiomeGenForCoords(par2, par4);

            if ((biomegenbase instanceof BiomeGenHell) && par5Random.nextInt(10) == 0)
            {
                i++;
                par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
            }
        }

        super.updateTick(par1World, par2, par3, par4, par5Random);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par2 < 3 ? par2 <= 0 ? blockIndexInTexture : blockIndexInTexture + 1 : blockIndexInTexture + 2;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 6;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
    {
        super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
    }

    public ArrayList getBlockDropped(World world, int i, int j, int k, int l, int i1)
    {
        ArrayList arraylist = new ArrayList();
        int j1 = 1;

        if (l >= 3)
        {
            j1 = 2 + world.rand.nextInt(3) + (i1 <= 0 ? 0 : world.rand.nextInt(i1 + 1));
        }

        for (int k1 = 0; k1 < j1; k1++)
        {
            arraylist.add(new ItemStack(Item.netherStalkSeeds));
        }

        return arraylist;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
}
