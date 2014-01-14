package mods.castledefenders.common.worldgenerator;

import java.util.Random;

import mods.castledefenders.common.ModCastleDefenders;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorMercBase implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		// Generation diffenrente ntre le nether et la surface
		switch (world.provider.dimensionId)
        {
            case -1:
                //this.generateNether(world, random, chunkX * 16, chunkZ * 16);
            	return;

            case 0:
                this.generateSurface(world, random, chunkX * 16, chunkZ * 16);
            	return;

            default:
        }
	}
	
	
	private void generateSurface(World var1, Random var2, int var3, int var4)
    {
        byte var5 = 64;
        int var6 = var2.nextInt(3);

        if (var6 == 0)
        {
            int var7;
            int var8;
            int var9;
            int var10;
            int var11;
            int var12;
            int var13;

            if (var2.nextInt(ModCastleDefenders.mercenarySpawnRate) == 0 && var6 == 0)
            {
                for (var7 = 0; var7 < 1; ++var7)
                {
                    var8 = var3 + var2.nextInt(8) - var2.nextInt(8);
                    var9 = var5 + var2.nextInt(8) - var2.nextInt(8);
                    var10 = var4 + var2.nextInt(8) - var2.nextInt(8);

                    if (var1.getBlockId(var8 + 3, var9 - 1, var10 + 3) == Block.grass.blockID)
                    {
                        for (var11 = var9; var11 < var9 + 8; ++var11)
                        {
                            for (var12 = 0; var12 < 11; ++var12)
                            {
                                for (var13 = 0; var13 < 11; ++var13)
                                {
                                    var1.setBlock(var8 + var12, var11, var10 + var13, 0, 0, 2);
                                }
                            }
                        }

                        for (var11 = var9; var11 < var9 + 4; ++var11)
                        {
                            for (var12 = 0; var12 < 6; ++var12)
                            {
                                for (var13 = 0; var13 < 11; ++var13)
                                {
                                    var1.setBlock(var8 + var12, var11, var10 + var13, 5);
                                }
                            }
                        }

                        for (var11 = var9; var11 < var9 + 3; ++var11)
                        {
                            for (var12 = 1; var12 < 5; ++var12)
                            {
                                for (var13 = 1; var13 < 10; ++var13)
                                {
                                    var1.setBlock(var8 + var12, var11, var10 + var13, 0);
                                }
                            }
                        }

                        var1.setBlock(var8 + 5, var9, var10 + 5, 0);
                        var1.setBlock(var8 + 5, var9 + 1, var10 + 5, 0);
                        var1.setBlock(var8 + 4, var9 + 1, var10 + 4, 50);
                        var1.setBlock(var8 + 4, var9 + 1, var10 + 6, 50);

                        if (var1.getBlockId(var8 + 3, var9 + 1, var10 - 1) == 0)
                        {
                            var1.setBlock(var8 + 3, var9 + 1, var10, 0);
                        }
                        else
                        {
                            var1.setBlock(var8 + 3, var9 + 1, var10, 50);
                        }

                        var1.setBlock(var8 + 1, var9, var10 + 1, 61);
                        var1.setBlock(var8 + 1, var9, var10 + 2, 54);
                        var1.setBlock(var8 + 1, var9, var10 + 3, 54);
                        var1.setBlock(var8 + 4, var9, var10 + 8, 26);
                        var1.setBlock(var8 + 4, var9, var10 + 9, 26);
                        var1.setBlock(var8 + 2, var9, var10 + 8, 26);
                        var1.setBlock(var8 + 2, var9, var10 + 9, 26);

                        for (var11 = 6; var11 < 11; ++var11)
                        {
                            for (var12 = 0; var12 < 11; ++var12)
                            {
                                if (var1.getBlockId(var8 + var11, var9 - 1, var10 + var12) != 0)
                                {
                                    var1.setBlock(var8 + var11, var9, var10 + var12, 85);
                                }
                            }
                        }

                        for (var11 = 6; var11 < 10; ++var11)
                        {
                            for (var12 = 1; var12 < 10; ++var12)
                            {
                                var1.setBlock(var8 + var11, var9, var10 + var12, 0);
                            }
                        }

                        var1.setBlock(var8 + 10, var9, var10 + 5, 0);
                        var1.setBlock(var8 + 4, var9 - 1, var10 + 2, ModCastleDefenders.blockMerc.blockID);

                        if (var1.getBlockId(var8 + 8, var9 - 1, var10 + 2) != 0)
                        {
                            var1.setBlock(var8 + 8, var9 - 1, var10 + 2, ModCastleDefenders.blockMerc.blockID);
                        }

                        for (var11 = 0; var11 < 2; ++var11)
                        {
                            TileEntityChest var15 = (TileEntityChest)var1.getBlockTileEntity(var8 + 1, var9, var10 + 3);
                            ItemStack var14 = this.pickCheckLootItem(var2);

                            if (var14 != null)
                            {
                                var15.setInventorySlotContents(var2.nextInt(var15.getSizeInventory()), var14);
                            }
                        }

                        var1.setBlock(var8 + 1, var9 + 4, var10 + 1, 126);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 2, 126);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 3, 126);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 4, 5);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 5, 5);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 6, 5);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 7, 126);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 8, 126);
                        var1.setBlock(var8 + 1, var9 + 4, var10 + 9, 126);
                        var1.setBlock(var8 + 2, var9 + 4, var10 + 1, 126);
                        var1.setBlock(var8 + 3, var9 + 4, var10 + 1, 126);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 1, 126);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 2, 126);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 3, 126);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 4, 5);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 5, 5);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 6, 5);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 7, 126);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 8, 126);
                        var1.setBlock(var8 + 4, var9 + 4, var10 + 9, 126);
                        var1.setBlock(var8 + 2, var9 + 4, var10 + 9, 126);
                        var1.setBlock(var8 + 3, var9 + 4, var10 + 9, 126);
                        var1.setBlock(var8 + 4, var9 + 5, var10 + 4, 5);
                        var1.setBlock(var8 + 4, var9 + 5, var10 + 5, 5);
                        var1.setBlock(var8 + 4, var9 + 5, var10 + 6, 5);
                        var1.setBlock(var8 + 1, var9 + 5, var10 + 4, 5);
                        var1.setBlock(var8 + 1, var9 + 5, var10 + 5, 5);
                        var1.setBlock(var8 + 1, var9 + 5, var10 + 6, 5);
                        var1.setBlock(var8 + 2, var9 + 4, var10 + 6, 5);
                        var1.setBlock(var8 + 2, var9 + 4, var10 + 4, 5);
                        var1.setBlock(var8 + 2, var9 + 5, var10 + 6, 5);
                        var1.setBlock(var8 + 2, var9 + 5, var10 + 4, 5);
                        var1.setBlock(var8 + 2, var9 + 5, var10 + 4, 0);
                        var1.setBlock(var8 + 2, var9 + 4, var10 + 4, 0);
                        var1.setBlock(var8 + 2, var9 + 5, var10 + 6, 0);
                        var1.setBlock(var8 + 2, var9 + 4, var10 + 6, 0);
                        var1.setBlock(var8 + 2, var9 + 2, var10 + 5, 5);
                        var1.setBlock(var8 + 2, var9 + 1, var10 + 5, 5);
                        var1.setBlock(var8 + 2, var9 + 0, var10 + 5, 5);
                        var1.setBlock(var8, var9 + 3, var10, 126);
                        var1.setBlock(var8, var9 + 3, var10 + 10, 126);
                        var1.setBlock(var8 + 5, var9 + 3, var10, 126);
                        var1.setBlock(var8 + 5, var9 + 3, var10 + 10, 126);

                        for (var11 = var9 + 6; var11 < var9 + 7; ++var11)
                        {
                            for (var12 = 1; var12 < 5; ++var12)
                            {
                                for (var13 = 3; var13 < 8; ++var13)
                                {
                                    var1.setBlock(var8 + var12, var11, var10 + var13, 5);
                                }
                            }
                        }

                        for (var11 = var9 + 7; var11 < var9 + 8; ++var11)
                        {
                            for (var12 = 1; var12 < 5; ++var12)
                            {
                                for (var13 = 3; var13 < 8; ++var13)
                                {
                                    var1.setBlock(var8 + var12, var11, var10 + var13, 126);
                                }
                            }
                        }

                        for (var11 = var9 + 7; var11 < var9 + 8; ++var11)
                        {
                            for (var12 = 2; var12 < 4; ++var12)
                            {
                                for (var13 = 4; var13 < 7; ++var13)
                                {
                                    var1.setBlock(var8 + var12, var11, var10 + var13, 0);
                                }
                            }
                        }

                        var1.setBlock(var8 + 1, var9 + 7, var10 + 3, 5);
                        var1.setBlock(var8 + 1, var9 + 7, var10 + 7, 5);
                        var1.setBlock(var8 + 4, var9 + 7, var10 + 3, 5);
                        var1.setBlock(var8 + 4, var9 + 7, var10 + 7, 5);
                        var1.setBlock(var8 + 2, var9 + 6, var10 + 6, ModCastleDefenders.blockArcherM.blockID);
                        var1.setBlock(var8 + 3, var9 + 6, var10 + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                        var1.setBlock(var8 + 3, var9 + 5, var10 + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                        var1.setBlock(var8 + 3, var9 + 4, var10 + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                        var1.setBlock(var8 + 3, var9 + 3, var10 + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                        var1.setBlock(var8 + 3, var9 + 2, var10 + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                        var1.setBlock(var8 + 3, var9 + 1, var10 + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                        var1.setBlock(var8 + 3, var9 + 0, var10 + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    }
                }
            }

            if (var2.nextInt(ModCastleDefenders.mercenarySpawnRate) == 0 && (var6 == 1 || var6 == 2))
            {
                for (var7 = 0; var7 < 1; ++var7)
                {
                    var8 = var3 + var2.nextInt(8) - var2.nextInt(8);
                    var9 = var5 + var2.nextInt(8) - var2.nextInt(8);
                    var10 = var4 + var2.nextInt(8) - var2.nextInt(8);

                    if (var1.getBlockId(var8, var9 - 1, var10) == Block.grass.blockID && var1.getBlockId(var8 + 7, var9 - 1, var10 + 1) != 0 && var1.getBlockId(var8 + 7, var9, var10 + 3) != 0)
                    {
                        for (var11 = var9; var11 < var9 + 8; ++var11)
                        {
                            for (var12 = 0; var12 < 9; ++var12)
                            {
                                for (var13 = 0; var13 < 4; ++var13)
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

                        for (var11 = 0; var11 < 4; ++var11)
                        {
                            for (var12 = 0; var12 < 5; ++var12)
                            {
                                var1.setBlock(var8 + var11, var9, var10 + var12, 85);
                            }
                        }

                        for (var11 = 1; var11 < 4; ++var11)
                        {
                            for (var12 = 1; var12 < 4; ++var12)
                            {
                                var1.setBlock(var8 + var11, var9, var10 + var12, 0);
                            }
                        }

                        var1.setBlock(var8, var9, var10 + 2, 0);
                        var1.setBlock(var8 + 2, var9 - 1, var10 + 2, ModCastleDefenders.blockMerc.blockID);
                    }
                }
            }
        }
    }


    private ItemStack pickCheckLootItem(Random var1) {
        int var2 = var1.nextInt(6);
        return var2 == 0 ? new ItemStack(Item.arrow, var1.nextInt(30) + 10) : (var2 == 1 ? new ItemStack(Item.swordIron) : (var2 == 2 ? new ItemStack(Item.goldNugget) : (var2 == 3 ? new ItemStack(Item.goldNugget) : (var2 == 4 ? new ItemStack(Item.bread) : (var2 == 5 ? new ItemStack(Item.carrot) : null)))));
    }
}
