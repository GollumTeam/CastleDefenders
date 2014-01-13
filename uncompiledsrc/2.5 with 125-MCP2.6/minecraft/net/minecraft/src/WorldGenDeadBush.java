package net.minecraft.src;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator
{
    /** stores the ID for WorldGenDeadBush */
    private int deadBushID;

    public WorldGenDeadBush(int par1)
    {
        deadBushID = par1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        Block block = null;

        do
        {
            block = Block.blocksList[par1World.getBlockId(par3, par4, par5)];
        }
        while ((block == null || block.isLeaves(par1World, par3, par4, par5)) && --par4 > 0);

        for (int i = 0; i < 4; i++)
        {
            int j = (par3 + par2Random.nextInt(8)) - par2Random.nextInt(8);
            int k = (par4 + par2Random.nextInt(4)) - par2Random.nextInt(4);
            int l = (par5 + par2Random.nextInt(8)) - par2Random.nextInt(8);

            if (par1World.isAirBlock(j, k, l) && ((BlockFlower)Block.blocksList[deadBushID]).canBlockStay(par1World, j, k, l))
            {
                par1World.setBlock(j, k, l, deadBushID);
            }
        }

        return true;
    }
}
