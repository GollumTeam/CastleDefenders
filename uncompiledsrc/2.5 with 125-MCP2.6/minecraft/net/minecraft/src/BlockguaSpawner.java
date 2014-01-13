package net.minecraft.src;

import java.util.Random;

public class BlockguaSpawner extends BlockContainer
{
    protected BlockguaSpawner(int i, int j)
    {
        super(i, j, Material.circuits);
    }

    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public int getBlockTextureFromSide(int i)
    {
        if (i == 1)
        {
            return mod_castle2.texturetop;
        }
        else
        {
            return mod_castle2.textureside;
        }
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity getBlockEntity()
    {
        return new TileEntityguaSpawner();
    }

    public int idDropped(int i, Random random)
    {
        return 234;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random random)
    {
        return 1;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }
}
