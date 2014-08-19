package net.minecraft.src;

import java.util.Random;

public class WorldGenTrees extends WorldGenerator
{
    private final int field_48202_a;
    private final boolean field_48200_b;
    private final int field_48201_c;
    private final int field_48199_d;

    public WorldGenTrees(boolean par1)
    {
        this(par1, 4, 0, 0, false);
    }

    public WorldGenTrees(boolean par1, int par2, int par3, int par4, boolean par5)
    {
        super(par1);
        field_48202_a = par2;
        field_48201_c = par3;
        field_48199_d = par4;
        field_48200_b = par5;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int i = par2Random.nextInt(3) + field_48202_a;
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

                for (int l2 = par3 - byte0; l2 <= par3 + byte0 && flag; l2++)
                {
                    for (int l = par5 - byte0; l <= par5 + byte0 && flag; l++)
                    {
                        if (j >= 0 && j < 256)
                        {
                            int l1 = par1World.getBlockId(l2, j, l);
                            Block block = Block.blocksList[l1];

                            if (l1 != 0 && !block.isLeaves(par1World, l2, j, l) && l1 != Block.grass.blockID && l1 != Block.dirt.blockID && !block.isWood(par1World, l2, j, l))
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
                byte byte1 = 3;
                int i3 = 0;

                for (int i1 = (par4 - byte1) + i; i1 <= par4 + i; i1++)
                {
                    int i2 = i1 - (par4 + i);
                    int j3 = (i3 + 1) - i2 / 2;

                    for (int l3 = par3 - j3; l3 <= par3 + j3; l3++)
                    {
                        int j4 = l3 - par3;

                        for (int l4 = par5 - j3; l4 <= par5 + j3; l4++)
                        {
                            int i5 = l4 - par5;
                            Block block3 = Block.blocksList[par1World.getBlockId(l3, i1, l4)];

                            if ((Math.abs(j4) != j3 || Math.abs(i5) != j3 || par2Random.nextInt(2) != 0 && i2 != 0) && (block3 == null || block3.canBeReplacedByLeaves(par1World, l3, i1, l4)))
                            {
                                setBlockAndMetadata(par1World, l3, i1, l4, Block.leaves.blockID, field_48199_d);
                            }
                        }
                    }
                }

                for (int j1 = 0; j1 < i; j1++)
                {
                    int j2 = par1World.getBlockId(par3, par4 + j1, par5);
                    Block block1 = Block.blocksList[j2];

                    if (j2 != 0 && block1 != null && !block1.isLeaves(par1World, par3, par4 + j1, par5))
                    {
                        continue;
                    }

                    setBlockAndMetadata(par1World, par3, par4 + j1, par5, Block.wood.blockID, field_48201_c);

                    if (!field_48200_b || j1 <= 0)
                    {
                        continue;
                    }

                    if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 - 1, par4 + j1, par5))
                    {
                        setBlockAndMetadata(par1World, par3 - 1, par4 + j1, par5, Block.vine.blockID, 8);
                    }

                    if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3 + 1, par4 + j1, par5))
                    {
                        setBlockAndMetadata(par1World, par3 + 1, par4 + j1, par5, Block.vine.blockID, 2);
                    }

                    if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + j1, par5 - 1))
                    {
                        setBlockAndMetadata(par1World, par3, par4 + j1, par5 - 1, Block.vine.blockID, 1);
                    }

                    if (par2Random.nextInt(3) > 0 && par1World.isAirBlock(par3, par4 + j1, par5 + 1))
                    {
                        setBlockAndMetadata(par1World, par3, par4 + j1, par5 + 1, Block.vine.blockID, 4);
                    }
                }

                if (field_48200_b)
                {
                    for (int k1 = (par4 - 3) + i; k1 <= par4 + i; k1++)
                    {
                        int k2 = k1 - (par4 + i);
                        int k3 = 2 - k2 / 2;

                        for (int i4 = par3 - k3; i4 <= par3 + k3; i4++)
                        {
                            for (int k4 = par5 - k3; k4 <= par5 + k3; k4++)
                            {
                                Block block2 = Block.blocksList[par1World.getBlockId(i4, k1, k4)];

                                if (block2 == null || !block2.isLeaves(par1World, i4, k1, k4))
                                {
                                    continue;
                                }

                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4 - 1, k1, k4) == 0)
                                {
                                    func_48198_a(par1World, i4 - 1, k1, k4, 8);
                                }

                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4 + 1, k1, k4) == 0)
                                {
                                    func_48198_a(par1World, i4 + 1, k1, k4, 2);
                                }

                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4, k1, k4 - 1) == 0)
                                {
                                    func_48198_a(par1World, i4, k1, k4 - 1, 1);
                                }

                                if (par2Random.nextInt(4) == 0 && par1World.getBlockId(i4, k1, k4 + 1) == 0)
                                {
                                    func_48198_a(par1World, i4, k1, k4 + 1, 4);
                                }
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

    private void func_48198_a(World par1World, int par2, int par3, int par4, int par5)
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
