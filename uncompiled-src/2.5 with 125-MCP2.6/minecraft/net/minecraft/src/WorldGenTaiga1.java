package net.minecraft.src;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenerator
{
    public WorldGenTaiga1()
    {
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(5) + 7;
        int j = i - par2Random.nextInt(2) - 3;
        int k = i - j;
        int l = 1 + par2Random.nextInt(k + 1);
        boolean flag = true;

        if (par4 >= 1 && par4 + i + 1 <= 128)
        {
            for (int i1 = par4; i1 <= par4 + 1 + i && flag; i1++)
            {
                boolean flag1 = true;
                int k3;

                if (i1 - par4 < j)
                {
                    k3 = 0;
                }
                else
                {
                    k3 = l;
                }

                for (int k1 = par3 - k3; k1 <= par3 + k3 && flag; k1++)
                {
                    for (int j2 = par5 - k3; j2 <= par5 + k3 && flag; j2++)
                    {
                        if (i1 >= 0 && i1 < 128)
                        {
                            int i3 = par1World.getBlockId(k1, i1, j2);
                            Block block1 = Block.blocksList[i3];

                            if (i3 != 0 && (block1 == null || !block1.isLeaves(par1World, k1, i1, j2)))
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

            int j1 = par1World.getBlockId(par3, par4 - 1, par5);

            if ((j1 == Block.grass.blockID || j1 == Block.dirt.blockID) && par4 < 128 - i - 1)
            {
                func_50073_a(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
                int l3 = 0;

                for (int l1 = par4 + i; l1 >= par4 + j; l1--)
                {
                    for (int k2 = par3 - l3; k2 <= par3 + l3; k2++)
                    {
                        int j3 = k2 - par3;

                        for (int i4 = par5 - l3; i4 <= par5 + l3; i4++)
                        {
                            int j4 = i4 - par5;
                            Block block2 = Block.blocksList[par1World.getBlockId(k2, l1, i4)];

                            if ((Math.abs(j3) != l3 || Math.abs(j4) != l3 || l3 <= 0) && (block2 == null || block2.canBeReplacedByLeaves(par1World, k2, l1, i4)))
                            {
                                setBlockAndMetadata(par1World, k2, l1, i4, Block.leaves.blockID, 1);
                            }
                        }
                    }

                    if (l3 >= 1 && l1 == par4 + j + 1)
                    {
                        l3--;
                        continue;
                    }

                    if (l3 < l)
                    {
                        l3++;
                    }
                }

                for (int i2 = 0; i2 < i - 1; i2++)
                {
                    int l2 = par1World.getBlockId(par3, par4 + i2, par5);
                    Block block = Block.blocksList[l2];

                    if (l2 == 0 || block == null || block.isLeaves(par1World, par3, par4 + i2, par5))
                    {
                        setBlockAndMetadata(par1World, par3, par4 + i2, par5, Block.wood.blockID, 1);
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
