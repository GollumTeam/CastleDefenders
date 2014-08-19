package net.minecraft.src;

import forge.ForgeHooks;
import forge.IShearable;
import java.util.ArrayList;
import java.util.Random;

public class BlockTallGrass extends BlockFlower implements IShearable
{
    protected BlockTallGrass(int par1, int par2)
    {
        super(par1, par2, Material.vine);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public int getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return par2 != 1 ? par2 != 2 ? par2 != 0 ? blockIndexInTexture : blockIndexInTexture + 16 : blockIndexInTexture + 16 + 1 : blockIndexInTexture;
    }

    public int getBlockColor()
    {
        double d = 0.5D;
        double d1 = 1.0D;
        return ColorizerGrass.getGrassColor(d, d1);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
        return par1 != 0 ? ColorizerFoliage.getFoliageColorBasic() : 0xffffff;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        return i != 0 ? par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor() : 0xffffff;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return -1;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        return 1 + par2Random.nextInt(par1 * 2 + 1);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }

    public ArrayList getBlockDropped(World world, int i, int j, int k, int l, int i1)
    {
        ArrayList arraylist = new ArrayList();

        if (world.rand.nextInt(8) != 0)
        {
            return arraylist;
        }

        ItemStack itemstack = ForgeHooks.getGrassSeed(world);

        if (itemstack != null)
        {
            arraylist.add(itemstack);
        }

        return arraylist;
    }

    public boolean isShearable(ItemStack itemstack, World world, int i, int j, int k)
    {
        return true;
    }

    public ArrayList onSheared(ItemStack itemstack, World world, int i, int j, int k, int l)
    {
        ArrayList arraylist = new ArrayList();
        arraylist.add(new ItemStack(this, 1, world.getBlockMetadata(i, j, k)));
        return arraylist;
    }
}
