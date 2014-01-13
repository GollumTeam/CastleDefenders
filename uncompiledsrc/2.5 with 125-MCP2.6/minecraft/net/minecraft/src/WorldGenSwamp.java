package net.minecraft.src;

import java.util.Random;

public class WorldGenSwamp extends WorldGenerator
{
    public WorldGenSwamp()
    {
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(4) + 5;

        for (; par1World.getBlockMaterial(par3, par4 - 1, par5) == Material.water; par4--) { }

        boolean flag = true;

        if (par4 >= 1 && par4 + i + 1 <= 128)
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
                    byte0 = 3;
                }

                for (int l = par3 - byte0; l <= par3 + byte0 && flag; l++)
                {
                    for (int l1 = par5 - byte0; l1 <= par5 + byte0 && flag; l1++)
                    {
                        if (j >= 0 && j < 128)
                        {
                            int k2 = par1World.getBlockId(l, j, l1);

                            if (k2 == 0 || Block.blocksList[k2] == null || Block.blocksList[k2].isLeaves(par1World, l, j, l1))
                            {
                                continue;
                            }

                            if (k2 != Block.waterStill.blockID && k2 != Block.waterMoving.blockID)
                            {
                                flag = false;
                                continue;
                            }

                            if (j > par4)
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

            if ((k == Block.grass.blockID || k == Block.dirt.blockID) && par4 < 128 - i - 1)
            {
                func_50073_a(par1World, par3, par4 - 1, par5, Block.dirt.blockID);

                for (int l3 = (par4 - 3) + i; l3 <= par4 + i; l3++)
                {
                    int i1 = l3 - (par4 + i);
                    int i2 = 2 - i1 / 2;

                    for (int l2 = par3 - i2; l2 <= par3 + i2; l2++)
                    {
                        int j3 = l2 - par3;

                        for (int k4 = par5 - i2; k4 <= par5 + i2; k4++)
                        {
                            int l4 = k4 - par5;
                            Block block2 = Block.blocksList[par1World.getBlockId(l2, l3, k4)];

                            if ((Math.abs(j3) != i2 || Math.abs(l4) != i2 || par2Random.nextInt(2) != 0 && i1 != 0) && (block2 == null || block2.canBeReplacedByLeaves(par1World, l2, l3, k4)))
                            {
                                func_50073_a(par1World, l2, l3, k4, Block.leaves.blockID);
                            }
                        }
                    }
                }

                for (int i4 = 0; i4 < i; i4++)
                {
                    int j1 = par1World.getBlockId(par3, par4 + i4, par5);
                    Block block = Block.blocksList[j1];

                    if (j1 == 0 || block != null && block.isLeaves(par1World, par3, par4 + i4, par5) || j1 == Block.waterMoving.blockID || j1 == Block.waterStill.blockID)
                    {
                        func_50073_a(par1World, par3, par4 + i4, par5, Block.wood.blockID);
                    }
                }

                for (int j4 = (par4 - 3) + i; j4 <= par4 + i; j4++)
                {
                    int k1 = j4 - (par4 + i);
                    int j2 = 2 - k1 / 2;

                    for (int i3 = par3 - j2; i3 <= par3 + j2; i3++)
                    {
                        for (int k3 = par5 - j2; k3 <= par5 + j2; k3++)
                        {
                            Block block1 = Block.blocksList[par1World.getBlockId(i3, j4, k3)];

                            if (block1 == null || !block1.isLeaves(par1World, i3, j4, k3))
                            {
                                continue;
                            }

                            if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i3 - 1, j4, k3) == 0)
                            {
                                generateVines(par1World, i3 - 1, j4, k3, 8);
                            }

                            if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i3 + 1, j4, k3) == 0)
                            {
                                generateVines(par1World, i3 + 1, j4, k3, 2);
                            }

                            if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i3, j4, k3 - 1) == 0)
                            {
                                generateVines(par1World, i3, j4, k3 - 1, 1);
                            }

                            if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i3, j4, k3 + 1) == 0)
                            {
                                generateVines(par1World, i3, j4, k3 + 1, 4);
                            }
                        }
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

    /**
     * Generates vines at the given position until it hits a block.
     */
    private void generateVines(World par1World, int par2, int par3, int par4, int par5)
    {
        setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
        int i = 4;

        do
        {
            par3--;

            if (par1World.getBlockId(par2, par3, par4) != 0 || i <= 0)
            {
                return;
            }

            setBlockAndMetadata(par1World, par2, par3, par4, Block.vine.blockID, par5);
            i--;
        }
        while (true);
    }
}
