package net.minecraft.src;

import java.util.Random;

public class WorldGenTaiga2 extends WorldGenerator
{
    public WorldGenTaiga2(boolean par1)
    {
        super(par1);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(4) + 6;
        int j = 1 + par2Random.nextInt(2);
        int k = i - j;
        int l = 2 + par2Random.nextInt(2);
        boolean flag = true;

        if (par4 >= 1 && par4 + i + 1 <= 256)
        {
            for (int i1 = par4; i1 <= par4 + 1 + i && flag; i1++)
            {
                boolean flag1 = true;
                int l2;

                if (i1 - par4 < j)
                {
                    l2 = 0;
                }
                else
                {
                    l2 = l;
                }

                for (int k1 = par3 - l2; k1 <= par3 + l2 && flag; k1++)
                {
                    for (int j3 = par5 - l2; j3 <= par5 + l2 && flag; j3++)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            int i2 = par1World.getBlockId(k1, i1, j3);
                            Block block = Block.blocksList[i2];

                            if (i2 != 0 && block != null && !block.isLeaves(par1World, k1, i1, j3))
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

            if ((j1 == Block.grass.blockID || j1 == Block.dirt.blockID) && par4 < 256 - i - 1)
            {
                func_50073_a(par1World, par3, par4 - 1, par5, Block.dirt.blockID);
                int i3 = par2Random.nextInt(2);
                int l1 = 1;
                boolean flag2 = false;

                for (int j2 = 0; j2 <= k; j2++)
                {
                    int i4 = (par4 + i) - j2;

                    for (int k3 = par3 - i3; k3 <= par3 + i3; k3++)
                    {
                        int k4 = k3 - par3;

                        for (int l4 = par5 - i3; l4 <= par5 + i3; l4++)
                        {
                            int i5 = l4 - par5;
                            Block block2 = Block.blocksList[par1World.getBlockId(k3, i4, l4)];

                            if ((Math.abs(k4) != i3 || Math.abs(i5) != i3 || i3 <= 0) && (block2 == null || block2.canBeReplacedByLeaves(par1World, k3, i4, l4)))
                            {
                                setBlockAndMetadata(par1World, k3, i4, l4, Block.leaves.blockID, 1);
                            }
                        }
                    }

                    if (i3 >= l1)
                    {
                        i3 = ((flag2) ? 1 : 0);
                        flag2 = true;

                        if (++l1 > l)
                        {
                            l1 = l;
                        }
                    }
                    else
                    {
                        i3++;
                    }
                }

                int k2 = par2Random.nextInt(3);

                for (int j4 = 0; j4 < i - k2; j4++)
                {
                    int l3 = par1World.getBlockId(par3, par4 + j4, par5);
                    Block block1 = Block.blocksList[l3];

                    if (l3 == 0 || block1 == null || block1.isLeaves(par1World, par3, par4 + j4, par5))
                    {
                        setBlockAndMetadata(par1World, par3, par4 + j4, par5, Block.wood.blockID, 1);
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
