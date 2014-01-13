package net.minecraft.src;

import java.util.Random;

public class WorldGenHugeTrees extends WorldGenerator
{
    private final int field_48195_a;

    /** Sets the metadata for the wood blocks used */
    private final int woodMetadata;

    /** Sets the metadata for the leaves used in huge trees */
    private final int leavesMetadata;

    public WorldGenHugeTrees(boolean par1, int par2, int par3, int par4)
    {
        super(par1);
        field_48195_a = par2;
        woodMetadata = par3;
        leavesMetadata = par4;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(3) + field_48195_a;
        boolean flag = true;

        if (par4 >= 1 && par4 + i + 1 <= 256)
        {
            for (int j = par4; j <= par4 + 1 + i; j++)
            {
                byte byte0 = 2;

                if (j == par4)
                {
                    byte0 = 1;
                }

                if (j >= (par4 + 1 + i) - 2)
                {
                    byte0 = 2;
                }

                for (int l = par3 - byte0; l <= par3 + byte0 && flag; l++)
                {
                    for (int j1 = par5 - byte0; j1 <= par5 + byte0 && flag; j1++)
                    {
                        if (j >= 0 && j < 256)
                        {
                            int j2 = par1World.getBlockId(l, j, j1);

                            if (j2 != 0 && Block.blocksList[j2] != null && !Block.blocksList[j2].isLeaves(par1World, l, j, j1) && j2 != Block.grass.blockID && j2 != Block.dirt.blockID && Block.blocksList[j2] != null && !Block.blocksList[j2].isWood(par1World, l, j, j1) && j2 != Block.sapling.blockID)
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
                par1World.setBlock(par3, par4 - 1, par5, Block.dirt.blockID);
                par1World.setBlock(par3 + 1, par4 - 1, par5, Block.dirt.blockID);
                par1World.setBlock(par3, par4 - 1, par5 + 1, Block.dirt.blockID);
                par1World.setBlock(par3 + 1, par4 - 1, par5 + 1, Block.dirt.blockID);
                func_48192_a(par1World, par3, par5, par4 + i, 2, par2Random);

                for (int i3 = (par4 + i) - 2 - par2Random.nextInt(4); i3 > par4 + i / 2; i3 -= 2 + par2Random.nextInt(4))
                {
                    float f = par2Random.nextFloat() * (float)Math.PI * 2.0F;
                    int k1 = par3 + (int)(0.5F + MathHelper.cos(f) * 4F);
                    int k2 = par5 + (int)(0.5F + MathHelper.sin(f) * 4F);
                    func_48192_a(par1World, k1, k2, i3, 0, par2Random);

                    for (int j3 = 0; j3 < 5; j3++)
                    {
                        int l1 = par3 + (int)(1.5F + MathHelper.cos(f) * (float)j3);
                        int l2 = par5 + (int)(1.5F + MathHelper.sin(f) * (float)j3);
                        setBlockAndMetadata(par1World, l1, (i3 - 3) + j3 / 2, l2, Block.wood.blockID, woodMetadata);
                    }
                }

                for (int i1 = 0; i1 < i; i1++)
                {
                    int i2 = par1World.getBlockId(par3, par4 + i1, par5);

                    if (i2 == 0 || Block.blocksList[i2] == null || Block.blocksList[i2].isLeaves(par1World, par3, par4 + i1, par5))
                    {
                        setBlockAndMetadata(par1World, par3, par4 + i1, par5, Block.wood.blockID, woodMetadata);

                        if (i1 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + i1, par5))
                            {
                                setBlockAndMetadata(par1World, par3 - 1, par4 + i1, par5, Block.vine.blockID, 8);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + i1, par5 - 1))
                            {
                                setBlockAndMetadata(par1World, par3, par4 + i1, par5 - 1, Block.vine.blockID, 1);
                            }
                        }
                    }

                    if (i1 >= i - 1)
                    {
                        continue;
                    }

                    i2 = par1World.getBlockId(par3 + 1, par4 + i1, par5);

                    if (i2 == 0 || Block.blocksList[i2] == null || Block.blocksList[i2].isLeaves(par1World, par3 + 1, par4 + i1, par5))
                    {
                        setBlockAndMetadata(par1World, par3 + 1, par4 + i1, par5, Block.wood.blockID, woodMetadata);

                        if (i1 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + i1, par5))
                            {
                                setBlockAndMetadata(par1World, par3 + 2, par4 + i1, par5, Block.vine.blockID, 2);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + i1, par5 - 1))
                            {
                                setBlockAndMetadata(par1World, par3 + 1, par4 + i1, par5 - 1, Block.vine.blockID, 1);
                            }
                        }
                    }

                    i2 = par1World.getBlockId(par3 + 1, par4 + i1, par5 + 1);

                    if (i2 == 0 || Block.blocksList[i2] == null || Block.blocksList[i2].isLeaves(par1World, par3 + 1, par4 + i1, par5 + 1))
                    {
                        setBlockAndMetadata(par1World, par3 + 1, par4 + i1, par5 + 1, Block.wood.blockID, woodMetadata);

                        if (i1 > 0)
                        {
                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 2, par4 + i1, par5 + 1))
                            {
                                setBlockAndMetadata(par1World, par3 + 2, par4 + i1, par5 + 1, Block.vine.blockID, 2);
                            }

                            if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + i1, par5 + 2))
                            {
                                setBlockAndMetadata(par1World, par3 + 1, par4 + i1, par5 + 2, Block.vine.blockID, 4);
                            }
                        }
                    }

                    i2 = par1World.getBlockId(par3, par4 + i1, par5 + 1);

                    if (i2 != 0 && Block.blocksList[i2] != null && !Block.blocksList[i2].isLeaves(par1World, par3, par4 + i1, par5 + 1))
                    {
                        continue;
                    }

                    setBlockAndMetadata(par1World, par3, par4 + i1, par5 + 1, Block.wood.blockID, woodMetadata);

                    if (i1 <= 0)
                    {
                        continue;
                    }

                    if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + i1, par5 + 1))
                    {
                        setBlockAndMetadata(par1World, par3 - 1, par4 + i1, par5 + 1, Block.vine.blockID, 8);
                    }

                    if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + i1, par5 + 2))
                    {
                        setBlockAndMetadata(par1World, par3, par4 + i1, par5 + 2, Block.vine.blockID, 4);
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

    private void func_48192_a(World par1World, int par2, int par3, int par4, int par5, Random par6Random)
    {
        byte byte0 = 2;

        for (int i = par4 - byte0; i <= par4; i++)
        {
            int j = i - par4;
            int k = (par5 + 1) - j;

            for (int l = par2 - k; l <= par2 + k + 1; l++)
            {
                int i1 = l - par2;

                for (int j1 = par3 - k; j1 <= par3 + k + 1; j1++)
                {
                    int k1 = j1 - par3;
                    Block block = Block.blocksList[par1World.getBlockId(l, i, j1)];

                    if ((i1 >= 0 || k1 >= 0 || i1 * i1 + k1 * k1 <= k * k) && (i1 <= 0 && k1 <= 0 || i1 * i1 + k1 * k1 <= (k + 1) * (k + 1)) && (par6Random.nextInt(4) != 0 || i1 * i1 + k1 * k1 <= (k - 1) * (k - 1)) && (block == null || block.canBeReplacedByLeaves(par1World, l, i, j1)))
                    {
                        setBlockAndMetadata(par1World, l, i, j1, Block.leaves.blockID, leavesMetadata);
                    }
                }
            }
        }
    }
}
