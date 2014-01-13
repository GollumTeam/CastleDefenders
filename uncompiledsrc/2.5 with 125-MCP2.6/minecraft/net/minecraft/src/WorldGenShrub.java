package net.minecraft.src;

import java.util.Random;

public class WorldGenShrub extends WorldGenerator
{
    private int field_48197_a;
    private int field_48196_b;

    public WorldGenShrub(int par1, int par2)
    {
        field_48196_b = par1;
        field_48197_a = par2;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        Block block = null;

        do
        {
            block = Block.blocksList[par1World.getBlockId(par3, par4, par5)];
        }
        while ((block == null || block.isLeaves(par1World, par3, par4, par5)) && --par4 > 0);

        int i = par1World.getBlockId(par3, par4, par5);

        if (i == Block.dirt.blockID || i == Block.grass.blockID)
        {
            par4++;
            setBlockAndMetadata(par1World, par3, par4, par5, Block.wood.blockID, field_48196_b);

            for (int j = par4; j <= par4 + 2; j++)
            {
                int k = j - par4;
                int l = 2 - k;

                for (int i1 = par3 - l; i1 <= par3 + l; i1++)
                {
                    int j1 = i1 - par3;

                    for (int k1 = par5 - l; k1 <= par5 + l; k1++)
                    {
                        int l1 = k1 - par5;
                        Block block1 = Block.blocksList[par1World.getBlockId(i1, j, k1)];

                        if ((Math.abs(j1) != l || Math.abs(l1) != l || par2Random.nextInt(2) != 0) && (block1 == null || block1.canBeReplacedByLeaves(par1World, i1, j, k1)))
                        {
                            setBlockAndMetadata(par1World, i1, j, k1, Block.leaves.blockID, field_48197_a);
                        }
                    }
                }
            }
        }

        return true;
    }
}
