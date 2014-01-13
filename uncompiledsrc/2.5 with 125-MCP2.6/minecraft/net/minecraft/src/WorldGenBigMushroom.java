package net.minecraft.src;

import java.util.Random;

public class WorldGenBigMushroom extends WorldGenerator
{
    /** The mushroom type. 0 for brown, 1 for red. */
    private int mushroomType;

    public WorldGenBigMushroom(int par1)
    {
        super(true);
        mushroomType = -1;
        mushroomType = par1;
    }

    public WorldGenBigMushroom()
    {
        super(false);
        mushroomType = -1;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(2);

        if (mushroomType >= 0)
        {
            i = mushroomType;
        }

        int j = par2Random.nextInt(3) + 4;
        boolean flag = true;

        if (par4 >= 1 && par4 + j + 1 < 256)
        {
            for (int k = par4; k <= par4 + 1 + j; k++)
            {
                byte byte0 = 3;

                if (k == par4)
                {
                    byte0 = 0;
                }

                for (int i1 = par3 - byte0; i1 <= par3 + byte0 && flag; i1++)
                {
                    for (int l1 = par5 - byte0; l1 <= par5 + byte0 && flag; l1++)
                    {
                        if (k >= 0 && k < 256)
                        {
                            int k2 = par1World.getBlockId(i1, k, l1);
                            Block block = Block.blocksList[k2];

                            if (k2 != 0 && block != null && !block.isLeaves(par1World, i1, k, l1))
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

            int l = par1World.getBlockId(par3, par4 - 1, par5);

            if (l != Block.dirt.blockID && l != Block.grass.blockID && l != Block.mycelium.blockID)
            {
                return false;
            }

            if (!Block.mushroomBrown.canPlaceBlockAt(par1World, par3, par4, par5))
            {
                return false;
            }

            setBlockAndMetadata(par1World, par3, par4 - 1, par5, Block.dirt.blockID, 0);
            int i3 = par4 + j;

            if (i == 1)
            {
                i3 = (par4 + j) - 3;
            }

            for (int j1 = i3; j1 <= par4 + j; j1++)
            {
                int i2 = 1;

                if (j1 < par4 + j)
                {
                    i2++;
                }

                if (i == 0)
                {
                    i2 = 3;
                }

                for (int l2 = par3 - i2; l2 <= par3 + i2; l2++)
                {
                    for (int j3 = par5 - i2; j3 <= par5 + i2; j3++)
                    {
                        int k3 = 5;

                        if (l2 == par3 - i2)
                        {
                            k3--;
                        }

                        if (l2 == par3 + i2)
                        {
                            k3++;
                        }

                        if (j3 == par5 - i2)
                        {
                            k3 -= 3;
                        }

                        if (j3 == par5 + i2)
                        {
                            k3 += 3;
                        }

                        if (i == 0 || j1 < par4 + j)
                        {
                            if ((l2 == par3 - i2 || l2 == par3 + i2) && (j3 == par5 - i2 || j3 == par5 + i2))
                            {
                                continue;
                            }

                            if (l2 == par3 - (i2 - 1) && j3 == par5 - i2)
                            {
                                k3 = 1;
                            }

                            if (l2 == par3 - i2 && j3 == par5 - (i2 - 1))
                            {
                                k3 = 1;
                            }

                            if (l2 == par3 + (i2 - 1) && j3 == par5 - i2)
                            {
                                k3 = 3;
                            }

                            if (l2 == par3 + i2 && j3 == par5 - (i2 - 1))
                            {
                                k3 = 3;
                            }

                            if (l2 == par3 - (i2 - 1) && j3 == par5 + i2)
                            {
                                k3 = 7;
                            }

                            if (l2 == par3 - i2 && j3 == par5 + (i2 - 1))
                            {
                                k3 = 7;
                            }

                            if (l2 == par3 + (i2 - 1) && j3 == par5 + i2)
                            {
                                k3 = 9;
                            }

                            if (l2 == par3 + i2 && j3 == par5 + (i2 - 1))
                            {
                                k3 = 9;
                            }
                        }

                        if (k3 == 5 && j1 < par4 + j)
                        {
                            k3 = 0;
                        }

                        Block block2 = Block.blocksList[par1World.getBlockId(l2, j1, j3)];

                        if ((k3 != 0 || par4 >= (par4 + j) - 1) && (block2 == null || block2.canBeReplacedByLeaves(par1World, l2, j1, j3)))
                        {
                            setBlockAndMetadata(par1World, l2, j1, j3, Block.mushroomCapBrown.blockID + i, k3);
                        }
                    }
                }
            }

            for (int k1 = 0; k1 < j; k1++)
            {
                int j2 = par1World.getBlockId(par3, par4 + k1, par5);
                Block block1 = Block.blocksList[j2];

                if (block1 == null || block1.canBeReplacedByLeaves(par1World, par3, par4 + k1, par5))
                {
                    setBlockAndMetadata(par1World, par3, par4 + k1, par5, Block.mushroomCapBrown.blockID + i, 10);
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
