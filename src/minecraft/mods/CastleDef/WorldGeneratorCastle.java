package mods.CastleDef;

import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldGeneratorCastle implements IWorldGenerator
{
    public void generate(Random var1, int var2, int var3, World var4, IChunkProvider var5, IChunkProvider var6)
    {
        switch (var4.provider.dimensionId)
        {
            case -1:
                this.generateNether(var4, var1, var2 * 16, var3 * 16);

            case 0:
                this.generateSurface(var4, var1, var2 * 16, var3 * 16);

            default:
        }
    }

    private void generateNether(World var1, Random var2, int var3, int var4) {}

    public void updateBlockMetadata(World var1, int var2, int var3, int var4, int var5, float var6, float var7, float var8)
    {
        int var9 = var1.getBlockMetadata(var2, var3, var4);

        if ((var9 == 0 || var5 == 2) && var1.isBlockNormalCube(var2, var3, var4 + 1))
        {
            var9 = 2;
        }

        if ((var9 == 0 || var5 == 3) && var1.isBlockNormalCube(var2, var3, var4 - 1))
        {
            var9 = 3;
        }

        if ((var9 == 0 || var5 == 4) && var1.isBlockNormalCube(var2 + 1, var3, var4))
        {
            var9 = 4;
        }

        if ((var9 == 0 || var5 == 5) && var1.isBlockNormalCube(var2 - 1, var3, var4))
        {
            var9 = 5;
        }

        var1.setBlock(var2, var3, var4, var9, 0, 2);
    }

    public void generateSurface(World var1, Random var2, int var3, int var4)
    {
        byte var5 = 64;
        int var6 = var2.nextInt(2);
        int var7;
        int var8;
        int var9;
        int var10;
        int var11;
        int var12;
        int var13;
        int var14;
        ItemStack var17;
        ItemStack var20;

        if (var2.nextInt(mod_castledef.CastleSpawnRaste) == 0 && var6 == 0)
        {
            for (var7 = 0; var7 < 1; ++var7)
            {
                var8 = var3 + var2.nextInt(8) - var2.nextInt(8);
                var9 = var5 + var2.nextInt(8) - var2.nextInt(8);
                var10 = var4 + var2.nextInt(8) - var2.nextInt(8);

                if (var1.getBlockId(var8 + 3, var9, var10 + 3) == Block.grass.blockID)
                {
                    var11 = var2.nextInt(2);

                    for (var12 = var9; var12 < var9 + 8; ++var12)
                    {
                        for (var13 = 0; var13 < 14; ++var13)
                        {
                            for (var14 = 0; var14 < 14; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, Block.cobblestone.blockID);
                            }
                        }
                    }

                    for (var12 = var9 + 1; var12 < var9 + 7; ++var12)
                    {
                        for (var13 = 1; var13 < 13; ++var13)
                        {
                            for (var14 = 1; var14 < 13; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, 0);
                            }
                        }
                    }

                    for (var12 = var9 + 7; var12 < var9 + 8; ++var12)
                    {
                        for (var13 = 3; var13 < 11; ++var13)
                        {
                            for (var14 = 3; var14 < 11; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, 0);
                            }
                        }
                    }

                    for (var12 = var9 + 8; var12 < var9 + 9; ++var12)
                    {
                        for (var13 = 0; var13 < 14; ++var13)
                        {
                            for (var14 = 0; var14 < 14; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, Block.cobblestone.blockID);
                            }
                        }
                    }

                    for (var12 = var9 + 8; var12 < var9 + 9; ++var12)
                    {
                        for (var13 = 1; var13 < 13; ++var13)
                        {
                            for (var14 = 1; var14 < 13; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, 0);
                            }
                        }
                    }

                    var1.setBlock(var8, var9 + 9, var10, Block.cobblestone.blockID);
                    var1.setBlock(var8 + 13, var9 + 9, var10, Block.cobblestone.blockID);
                    var1.setBlock(var8, var9 + 9, var10 + 13, Block.cobblestone.blockID);
                    var1.setBlock(var8 + 13, var9 + 9, var10 + 13, Block.cobblestone.blockID);
                    var1.setBlock(var8 + 12, var9 + 7, var10 + 12, mod_castledef.BlockEArcher.blockID);
                    var1.setBlock(var8 + 12, var9 + 7, var10 + 1, mod_castledef.BlockEArcher.blockID);
                    var1.setBlock(var8 + 1, var9 + 7, var10 + 12, mod_castledef.BlockEArcher.blockID);
                    var1.setBlock(var8 + 1, var9 + 7, var10 + 1, mod_castledef.BlockEArcher.blockID);

                    for (var12 = var9; var12 < var9 + 5; ++var12)
                    {
                        for (var13 = 0; var13 < 1; ++var13)
                        {
                            for (var14 = 5; var14 < 9; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, 98);
                            }
                        }
                    }

                    for (var12 = var9 + 1; var12 < var9 + 4; ++var12)
                    {
                        for (var13 = 0; var13 < 1; ++var13)
                        {
                            for (var14 = 6; var14 < 8; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, 0);
                            }
                        }
                    }

                    var1.setBlock(var8, var9 + 3, var10 + 3, 0);
                    var1.setBlock(var8, var9 + 3, var10 + 2, 0);
                    var1.setBlock(var8, var9 + 3, var10 + 10, 0);
                    var1.setBlock(var8, var9 + 3, var10 + 11, 0);
                    var1.setBlock(var8 + 1, var9 + 1, var10 + 2, mod_castledef.BlockEArcher.blockID);
                    var1.setBlock(var8 + 1, var9 + 1, var10 + 11, mod_castledef.BlockEArcher.blockID);
                    var1.setBlock(var8 + 1, var9 + 1, var10 + 3, Block.cobblestone.blockID);
                    var1.setBlock(var8 + 1, var9 + 1, var10 + 10, Block.cobblestone.blockID);

                    for (var12 = var9; var12 < var9 + 1; ++var12)
                    {
                        for (var13 = 0; var13 < 14; ++var13)
                        {
                            for (var14 = 0; var14 < 14; ++var14)
                            {
                                var1.setBlock(var8 + var13, var12, var10 + var14, 98);
                            }
                        }
                    }

                    var12 = var2.nextInt(4);
                    int var15;

                    if (var12 == 0)
                    {
                        for (var13 = var9 + 1; var13 < var9 + 14; ++var13)
                        {
                            for (var14 = 3; var14 < 11; ++var14)
                            {
                                for (var15 = 3; var15 < 11; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, Block.cobblestone.blockID);
                                }
                            }
                        }

                        for (var13 = var9 + 13; var13 < var9 + 14; ++var13)
                        {
                            for (var14 = 2; var14 < 12; ++var14)
                            {
                                for (var15 = 2; var15 < 12; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, Block.cobblestone.blockID);
                                }
                            }
                        }

                        for (var13 = var9 + 13; var13 < var9 + 14; ++var13)
                        {
                            for (var14 = 2; var14 < 12; ++var14)
                            {
                                for (var15 = 2; var15 < 12; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, Block.cobblestone.blockID);
                                }
                            }
                        }

                        for (var13 = var9 + 14; var13 < var9 + 15; ++var13)
                        {
                            for (var14 = 2; var14 < 12; ++var14)
                            {
                                for (var15 = 2; var15 < 12; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, Block.cobblestone.blockID);
                                }
                            }
                        }

                        for (var13 = var9 + 15; var13 < var9 + 16; ++var13)
                        {
                            for (var14 = 2; var14 < 12; ++var14)
                            {
                                for (var15 = 2; var15 < 12; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, Block.cobblestone.blockID);
                                }
                            }
                        }

                        for (var13 = var9 + 15; var13 < var9 + 16; ++var13)
                        {
                            for (var14 = 3; var14 < 11; ++var14)
                            {
                                for (var15 = 3; var15 < 11; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, 0);
                                }
                            }
                        }

                        for (var13 = var9 + 1; var13 < var9 + 13; ++var13)
                        {
                            for (var14 = 4; var14 < 10; ++var14)
                            {
                                for (var15 = 4; var15 < 10; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, 0);
                                }
                            }
                        }

                        for (var13 = var9 + 1; var13 < var9 + 4; ++var13)
                        {
                            for (var14 = 3; var14 < 4; ++var14)
                            {
                                for (var15 = 6; var15 < 8; ++var15)
                                {
                                    var1.setBlock(var8 + var14, var13, var10 + var15, 0);
                                }
                            }
                        }

                        var1.setBlock(var8 + 7, var9 + 15, var10 + 7, mod_castledef.BlockEMageID);
                        var1.setBlock(var8 + 2, var9 + 16, var10 + 2, Block.cobblestone.blockID);
                        var1.setBlock(var8 + 11, var9 + 16, var10 + 2, Block.cobblestone.blockID);
                        var1.setBlock(var8 + 2, var9 + 16, var10 + 11, Block.cobblestone.blockID);
                        var1.setBlock(var8 + 11, var9 + 16, var10 + 11, Block.cobblestone.blockID);
                        var1.setBlock(var8 + 5, var9 + 2, var10 + 4, Block.torchWood.blockID);
                        var1.setBlock(var8 + 5, var9 + 2, var10 + 9, Block.torchWood.blockID);
                        boolean var21 = true;

                        for (var13 = 1; var13 < 15; ++var13)
                        {
                            var1.setBlock(var8 + 7, var9 + var13, var10 + 4, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[3]], 2);
                        }

                        var1.setBlock(var8 + 5, var9 + 8, var10 + 3, 0);
                        var1.setBlock(var8 + 5, var9 + 9, var10 + 3, 0);
                        var1.setBlock(var8 + 5, var9 + 7, var10 + 4, Block.cobblestone.blockID);
                        var1.setBlock(var8 + 6, var9 + 7, var10 + 4, Block.cobblestone.blockID);
                        var1.setBlock(var8 + 6, var9 + 7, var10 + 5, Block.cobblestone.blockID);
                        var1.setBlock(var8 + 7, var9 + 7, var10 + 5, Block.cobblestone.blockID);
                    }

                    var1.setBlock(var8 + 6, var9 + 1, var10 + 1, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.offsetsXForSide[5]], 2);
                    var1.setBlock(var8 + 6, var9 + 2, var10 + 1, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    var1.setBlock(var8 + 6, var9 + 3, var10 + 1, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    var1.setBlock(var8 + 6, var9 + 4, var10 + 1, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    var1.setBlock(var8 + 6, var9 + 5, var10 + 1, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    var1.setBlock(var8 + 6, var9 + 6, var10 + 1, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    var1.setBlock(var8 + 6, var9 + 7, var10 + 1, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);

                    if (var1.getBlockId(var8 - 1, var9, var10 - 1) == Block.grass.blockID || var1.getBlockId(var8 - 1, var9, var10 - 1) == Block.dirt.blockID)
                    {
                        var1.setBlock(var8 - 1, var9 + 1, var10 - 1, mod_castledef.BlockEKnight.blockID);
                    }

                    if (var1.getBlockId(var8 + 15, var9, var10 + 15) == Block.grass.blockID || var1.getBlockId(var8 + 15, var9, var10 + 15) == Block.dirt.blockID)
                    {
                        var1.setBlock(var8 + 15, var9 + 1, var10 + 15, mod_castledef.BlockEKnight.blockID);
                    }

                    if (var1.getBlockId(var8 - 1, var9, var10 + 15) == Block.grass.blockID || var1.getBlockId(var8 - 1, var9, var10 + 15) == Block.dirt.blockID)
                    {
                        var1.setBlock(var8 - 1, var9 + 1, var10 + 15, mod_castledef.BlockEKnight.blockID);
                    }

                    if (var1.getBlockId(var8 + 15, var9, var10 - 1) == Block.grass.blockID || var1.getBlockId(var8 + 15, var9, var10 - 1) == Block.dirt.blockID)
                    {
                        var1.setBlock(var8 + 15, var9 + 1, var10 - 1, mod_castledef.BlockEKnight.blockID);
                    }

                    var1.setBlock(var8 + 11, var9 + 1, var10 + 4, mod_castledef.BlockEKnight.blockID);
                    var1.setBlock(var8 + 11, var9 + 1, var10 + 9, mod_castledef.BlockEKnight.blockID);

                    for (var13 = var9 + 1; var13 < var9 + 2; ++var13)
                    {
                        for (var14 = 12; var14 < 13; ++var14)
                        {
                            for (var15 = 1; var15 < 13; ++var15)
                            {
                                var1.setBlock(var8 + var14, var13, var10 + var15, 5);
                            }
                        }
                    }

                    var1.setBlock(var8 + 12, var9 + 2, var10 + 4, Block.torchWood.blockID);
                    var1.setBlock(var8 + 12, var9 + 2, var10 + 9, Block.torchWood.blockID);
                    var1.setBlock(var8 + 12, var9 + 1, var10 + 6, 54);
                    var1.setBlock(var8 + 12, var9 + 1, var10 + 7, 54);
                    TileEntityChest var19 = (TileEntityChest)var1.getBlockTileEntity(var8 + 12, var9 + 1, var10 + 7);

                    for (var14 = 0; var14 < 4; ++var14)
                    {
                        var17 = this.pickCheckLootItem2(var2);
                        ItemStack var16 = this.pickCheckLootItem3(var2);

                        if (var17 != null)
                        {
                            var19.setInventorySlotContents(var2.nextInt(var19.getSizeInventory()), var17);
                            var19.setInventorySlotContents(var2.nextInt(var19.getSizeInventory()), var16);
                        }
                    }

                    var20 = this.pickCheckLootItem(var2);

                    if (var20 != null)
                    {
                        var19.setInventorySlotContents(var2.nextInt(var19.getSizeInventory()), var20);
                    }

                    var15 = var2.nextInt(4);

                    if (var15 == 1)
                    {
                        var1.setBlock(var8 + 9, var9 + 1, var10 + 10, Block.tnt.blockID);
                        var1.setBlock(var8 + 9, var9 + 2, var10 + 10, Block.tnt.blockID);
                        var1.setBlock(var8 + 10, var9 + 1, var10 + 10, Block.tnt.blockID);
                    }

                    if (var15 == 2)
                    {
                        var1.setBlock(var8 + 9, var9 + 1, var10 + 10, 42);
                        var1.setBlock(var8 + 9, var9 + 1, var10 + 9, 42);
                        var1.setBlock(var8 + 9, var9 + 2, var10 + 9, 42);
                    }

                    if (var15 == 3)
                    {
                        var1.setBlock(var8 + 9, var9 + 1, var10 + 10, 41);
                        var1.setBlock(var8 + 9, var9 + 2, var10 + 10, 41);
                    }

                    if (var15 == 0)
                    {
                        var1.setBlock(var8 + 9, var9 + 1, var10 + 10, 22);
                        var1.setBlock(var8 + 9, var9 + 1, var10 + 11, 22);
                        var1.setBlock(var8 + 9, var9 + 2, var10 + 11, 22);
                    }
                }
            }
        }

        if (var6 == 1 && var2.nextInt(mod_castledef.CastleSpawnRaste) == 0)
        {
            for (var7 = 0; var7 < 1; ++var7)
            {
                var8 = var3 + var2.nextInt(8) - var2.nextInt(8);
                var9 = var5 + var2.nextInt(8) - var2.nextInt(8);
                var10 = var4 + var2.nextInt(8) - var2.nextInt(8);

                if (var1.getBlockId(var8, var9 - 1, var10) != 0 && var1.getBlockId(var8, var9, var10) == 0 && var1.getBlockId(var8 + 7, var9 - 1, var10 + 1) != 0 && var1.getBlockId(var8 + 7, var9, var10 + 3) != 0)
                {
                    for (var11 = var9; var11 < var9 + 8; ++var11)
                    {
                        for (var12 = -5; var12 < 9; ++var12)
                        {
                            for (var13 = -5; var13 < 9; ++var13)
                            {
                                var1.setBlock(var8 + var12, var11, var10 + var13, 0, 0, 2);
                            }
                        }
                    }

                    for (var11 = var9; var11 < var9 + 1; ++var11)
                    {
                        for (var12 = 4; var12 < 7; ++var12)
                        {
                            for (var13 = 0; var13 < 5; ++var13)
                            {
                                var1.setBlock(var8 + var12, var11, var10 + var13, 35);
                            }
                        }
                    }

                    for (var11 = var9; var11 < var9 + 1; ++var11)
                    {
                        for (var12 = 4; var12 < 7; ++var12)
                        {
                            for (var13 = 1; var13 < 4; ++var13)
                            {
                                var1.setBlock(var8 + var12, var11, var10 + var13, 0);
                            }
                        }
                    }

                    var1.setBlock(var8 + 7, var9, var10 + 1, 35);
                    var1.setBlock(var8 + 7, var9, var10 + 2, 35);
                    var1.setBlock(var8 + 7, var9 + 1, var10 + 2, 35);
                    var1.setBlock(var8 + 7, var9, var10 + 3, 35);
                    var1.setBlock(var8 + 8, var9, var10 + 2, 35);
                    var1.setBlock(var8 + 6, var9 + 2, var10 + 2, 35);
                    var1.setBlock(var8 + 5, var9 + 2, var10 + 2, 35);
                    var1.setBlock(var8 + 4, var9 + 2, var10 + 2, 35);
                    var1.setBlock(var8 + 6, var9 + 1, var10 + 1, 35);
                    var1.setBlock(var8 + 5, var9 + 1, var10 + 1, 35);
                    var1.setBlock(var8 + 4, var9 + 1, var10 + 1, 35);
                    var1.setBlock(var8 + 6, var9 + 1, var10 + 3, 35);
                    var1.setBlock(var8 + 5, var9 + 1, var10 + 3, 35);
                    var1.setBlock(var8 + 4, var9 + 1, var10 + 3, 35);
                    var1.setBlock(var8 + 6, var9 + 1, var10 + 2, 50);
                    var1.setBlock(var8 + 7, var9, var10 + 2, 85);
                    var1.setBlock(var8 + 5, var9, var10 + 1, 85);
                    var1.setBlock(var8 + 5, var9, var10 + 3, 85);

                    for (var11 = var9; var11 < var9 + 8; ++var11)
                    {
                        for (var12 = 0; var12 < 11; ++var12)
                        {
                            for (var13 = 0; var13 < 11; ++var13)
                            {
                                var1.setBlockMetadataWithNotify(var8 + var12, var11, var10 + var13, 14, 35);
                            }
                        }
                    }

                    var1.setBlock(var8 + 3, var9, var10 + 1, 54);
                    var1.setBlock(var8 + 2, var9, var10 + 2, mod_castledef.BlockEKnight.blockID);
                    var11 = var2.nextInt(2);

                    if (var11 == 1)
                    {
                        for (var12 = 0; var12 < 4; ++var12)
                        {
                            for (var13 = 0; var13 < 5; ++var13)
                            {
                                var1.setBlock(var8 + var12, var9, var10 + var13, 85);
                            }
                        }

                        for (var12 = 1; var12 < 4; ++var12)
                        {
                            for (var13 = 1; var13 < 4; ++var13)
                            {
                                var1.setBlock(var8 + var12, var9, var10 + var13, 0);
                            }
                        }

                        var1.setBlock(var8, var9, var10 + 2, 0);
                        var1.setBlock(var8 + 3, var9, var10 + 1, 54);
                        var1.setBlock(var8 + 2, var9, var10 + 2, mod_castledef.BlockEKnight.blockID);
                    }

                    if (var11 == 0)
                    {
                        for (var12 = var9; var12 < var9 + 8; ++var12)
                        {
                            for (var13 = 0; var13 < -11; ++var13)
                            {
                                for (var14 = 0; var14 < -11; ++var14)
                                {
                                    var1.setBlock(var8 - var13, var12, var10 - var14, 0, 0, 2);
                                }
                            }
                        }

                        for (var12 = var9; var12 < var9 + 1; ++var12)
                        {
                            for (var13 = 4; var13 < 7; ++var13)
                            {
                                for (var14 = 0; var14 < 5; ++var14)
                                {
                                    var1.setBlock(var8 - var13, var12, var10 - var14, 35);
                                }
                            }
                        }

                        for (var12 = var9; var12 < var9 + 1; ++var12)
                        {
                            for (var13 = 4; var13 < 7; ++var13)
                            {
                                for (var14 = 1; var14 < 4; ++var14)
                                {
                                    var1.setBlock(var8 - var13, var12, var10 - var14, 0);
                                }
                            }
                        }

                        var1.setBlock(var8 - 7, var9, var10 - 1, 35);
                        var1.setBlock(var8 - 7, var9, var10 - 2, 35);
                        var1.setBlock(var8 - 7, var9 + 1, var10 - 2, 35);
                        var1.setBlock(var8 - 7, var9, var10 - 3, 35);
                        var1.setBlock(var8 - 8, var9, var10 - 2, 35);
                        var1.setBlock(var8 - 6, var9 + 2, var10 - 2, 35);
                        var1.setBlock(var8 - 5, var9 + 2, var10 - 2, 35);
                        var1.setBlock(var8 - 4, var9 + 2, var10 - 2, 35);
                        var1.setBlock(var8 - 6, var9 + 1, var10 - 1, 35);
                        var1.setBlock(var8 - 5, var9 + 1, var10 - 1, 35);
                        var1.setBlock(var8 - 4, var9 + 1, var10 - 1, 35);
                        var1.setBlock(var8 - 6, var9 + 1, var10 - 3, 35);
                        var1.setBlock(var8 - 5, var9 + 1, var10 - 3, 35);
                        var1.setBlock(var8 - 4, var9 + 1, var10 - 3, 35);
                        var1.setBlock(var8 - 6, var9 + 1, var10 - 2, 50);
                        var1.setBlock(var8 - 7, var9, var10 - 2, 85);
                        var1.setBlock(var8 - 5, var9, var10 - 1, 85);
                        var1.setBlock(var8 - 5, var9, var10 - 3, 85);

                        for (var12 = var9; var12 < var9 + 8; ++var12)
                        {
                            for (var13 = 0; var13 < 11; ++var13)
                            {
                                for (var14 = 0; var14 < 11; ++var14)
                                {
                                    var1.setBlockMetadataWithNotify(var8 - var13, var12, var10 - var14, 14, 35);
                                }
                            }
                        }

                        for (var12 = 1; var12 < 7; ++var12)
                        {
                            for (var13 = 1; var13 < 4; ++var13)
                            {
                                var1.setBlock(var8 - var12, var9 - 1, var10 - var13, 2);
                            }
                        }

                        var1.setBlock(var8, var9, var10 - 2, 0);
                        var1.setBlock(var8 - 2, var9, var10 - 2, mod_castledef.BlockEKnight.blockID);
                        var1.setBlock(var8 + 1, var9 - 1, var10 - 2, 87);
                        var1.setBlock(var8 + 2, var9 - 1, var10 - 2, 4);
                        var1.setBlock(var8, var9 - 1, var10 - 2, 4);
                        var1.setBlock(var8 + 1, var9 - 1, var10 - 1, 4);
                        var1.setBlock(var8 + 1, var9 - 1, var10 - 3, 4);
                        var1.setBlock(var8 + 1, var9, var10 - 2, 51);
                    }

                    TileEntityChest var18 = (TileEntityChest)var1.getBlockTileEntity(var8 + 3, var9, var10 + 1);

                    for (var13 = 0; var13 < 4; ++var13)
                    {
                        var20 = this.pickCheckLootItem2(var2);
                        var17 = this.pickCheckLootItem3(var2);

                        if (var20 != null)
                        {
                            var18.setInventorySlotContents(var2.nextInt(var18.getSizeInventory()), var20);
                            var18.setInventorySlotContents(var2.nextInt(var18.getSizeInventory()), var17);
                        }
                    }
                }
            }
        }
    }

    private ItemStack pickCheckLootItem(Random var1)
    {
        return new ItemStack(mod_castledef.ItemMedallion);
    }

    private ItemStack pickCheckLootItem3(Random var1)
    {
        int var2 = var1.nextInt(9);
        return var2 == 0 ? new ItemStack(Item.arrow, var1.nextInt(30) + 10) : (var2 == 1 ? new ItemStack(Item.bread) : (var2 == 2 ? new ItemStack(Item.swordIron) : (var2 == 3 ? new ItemStack(Item.emerald) : (var2 == 4 ? new ItemStack(Item.bowlSoup) : (var2 == 5 ? new ItemStack(Item.diamond, var1.nextInt(1) + 1) : (var2 == 6 ? new ItemStack(Item.axeIron) : (var2 == 7 ? new ItemStack(Item.bowlSoup) : (var2 == 8 ? new ItemStack(Item.legsChain) : null))))))));
    }

    private ItemStack pickCheckLootItem2(Random var1)
    {
        int var2 = var1.nextInt(13);
        return var2 == 0 ? new ItemStack(Item.carrot) : (var2 == 1 ? new ItemStack(Item.ingotIron, var1.nextInt(4) + 1) : (var2 == 2 ? new ItemStack(Item.ingotGold, var1.nextInt(4) + 1) : (var2 == 3 ? new ItemStack(Item.compass) : (var2 == 4 ? new ItemStack(Item.gunpowder, var1.nextInt(7) + 1) : (var2 == 5 ? new ItemStack(Item.arrow, var1.nextInt(22) + 8) : (var2 == 6 ? new ItemStack(Item.bucketEmpty) : (var2 == 7 && var1.nextInt(10) == 0 ? new ItemStack(Item.appleGold) : (var2 == 8 && var1.nextInt(2) == 0 ? new ItemStack(Item.redstone, var1.nextInt(9) + 1) : (var2 == 9 && var1.nextInt(10) == 0 ? new ItemStack(Item.helmetChain) : (var2 == 10 ? new ItemStack(Item.egg, var1.nextInt(4) + 1) : (var2 == 11 ? new ItemStack(Item.bootsChain) : (var2 == 12 ? new ItemStack(Item.skull) : null))))))))))));
    }
}
