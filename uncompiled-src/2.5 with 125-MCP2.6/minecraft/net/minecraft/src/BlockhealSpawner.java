package net.minecraft.src;

import java.util.Random;

public class BlockhealSpawner extends BlockContainer
{
    protected BlockhealSpawner(int i, int j)
    {
        super(i, j, Material.circuits);
    }

    /**
     * Returns the TileEntity used by this block.
     */
    public TileEntity getBlockEntity()
    {
        return new TileEntityhealSpawner();
    }

    public int idDropped(int i, Random random)
    {
        return 230;
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
