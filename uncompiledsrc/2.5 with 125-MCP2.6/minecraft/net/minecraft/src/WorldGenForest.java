package net.minecraft.src;

import java.util.Random;

public class WorldGenForest extends WorldGenerator
{
    public WorldGenForest(boolean par1)
    {
        super(par1);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(3) + 5;
        boolean flag = true;

        if (par4 >= 1 && par4 + i + 1 <= 256)
        {
            for (int j = par4; j <= par4 + 1 + i; j++)
            {
                byte byte0 = 1;

                if (j == par4)
                {
                    byte0 = 0;
                }

                if (j >= (par4 + 1 + i) - 2)
                {
                    byte0 = 2;
                }

                for (int l = par3 - byte0; l <= par3 + byte0 && flag; l++)
                {
                    for (int k1 = par5 - byte0; k1 <= par5 + byte0 && flag; k1++)
                    {
                        if (j >= 0 && j < 256)
                        {
                            int i2 = par1World.getBlockId(l, j, k1);
                            Block block = Block.blocksList[i2];

                            if (i2 != 0 && block != null && !block.isLeaves(par1World, l, j, k1))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }

            int k = par1World.getBlockId(par3, par4 - 1, par5);

            if ((k == Block.grass.blockID || k == Block.dirt.blockID) && par4 < 256 - i - 1)
            {
                func_50073_a(par1World, par3, par4 - 1, par5, Block.dirt.blockID);

                for (int k2 = (par4 - 3) + i; k2 <= par4 + i; k2++)
                {
                    int i1 = k2 - (par4 + i);
                    int l1 = 1 - i1 / 2;

                    for (int j2 = par3 - l1; j2 <= par3 + l1; j2++)
                    {
                        int i3 = j2 - par3;

                        for (int j3 = par5 - l1; j3 <= par5 + l1; j3++)
                        {
                            int k3 = j3 - par5;
                            Block block2 = Block.blocksList[par1World.getBlockId(j2, k2, j3)];

                            if ((Math.abs(i3) != l1 || Math.abs(k3) != l1 || par2Random.nextInt(2) != 0 && i1 != 0) && (block2 == null || block2.canBeReplacedByLeaves(par1World, j2, k2, j3)))
                            {
                                setBlockAndMetadata(par1World, j2, k2, j3, Block.leaves.blockID, 2);
                            }
                        }
                    }
                }

                for (int l2 = 0; l2 < i; l2++)
                {
                    int j1 = par1World.getBlockId(par3, par4 + l2, par5);
                    Block block1 = Block.blocksList[j1];

                    if (j1 == 0 || block1 == null || block1.isLeaves(par1World, par3, par4 + l2, par5))
                    {
                        setBlockAndMetadata(par1World, par3, par4 + l2, par5, Block.wood.blockID, 2);
                    }
                }

                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
}
