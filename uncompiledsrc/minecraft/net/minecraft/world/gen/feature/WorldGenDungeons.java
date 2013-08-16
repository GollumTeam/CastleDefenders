package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;
import net.minecraftforge.common.MinecraftForge;

public class WorldGenDungeons extends WorldGenerator
{
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        byte b0 = 3;
        int l = par2Random.nextInt(2) + 2;
        int i1 = par2Random.nextInt(2) + 2;
        int j1 = 0;
        int k1;
        int l1;
        int i2;

        for (k1 = par3 - l - 1; k1 <= par3 + l + 1; ++k1)
        {
            for (l1 = par4 - 1; l1 <= par4 + b0 + 1; ++l1)
            {
                for (i2 = par5 - i1 - 1; i2 <= par5 + i1 + 1; ++i2)
                {
                    Material material = par1World.getBlockMaterial(k1, l1, i2);

                    if (l1 == par4 - 1 && !material.isSolid())
                    {
                        return false;
                    }

                    if (l1 == par4 + b0 + 1 && !material.isSolid())
                    {
                        return false;
                    }

                    if ((k1 == par3 - l - 1 || k1 == par3 + l + 1 || i2 == par5 - i1 - 1 || i2 == par5 + i1 + 1) && l1 == par4 && par1World.isAirBlock(k1, l1, i2) && par1World.isAirBlock(k1, l1 + 1, i2))
                    {
                        ++j1;
                    }
                }
            }
        }

        if (j1 >= 1 && j1 <= 5)
        {
            for (k1 = par3 - l - 1; k1 <= par3 + l + 1; ++k1)
            {
                for (l1 = par4 + b0; l1 >= par4 - 1; --l1)
                {
                    for (i2 = par5 - i1 - 1; i2 <= par5 + i1 + 1; ++i2)
                    {
                        if (k1 != par3 - l - 1 && l1 != par4 - 1 && i2 != par5 - i1 - 1 && k1 != par3 + l + 1 && l1 != par4 + b0 + 1 && i2 != par5 + i1 + 1)
                        {
                            par1World.setBlockToAir(k1, l1, i2);
                        }
                        else if (l1 >= 0 && !par1World.getBlockMaterial(k1, l1 - 1, i2).isSolid())
                        {
                            par1World.setBlockToAir(k1, l1, i2);
                        }
                        else if (par1World.getBlockMaterial(k1, l1, i2).isSolid())
                        {
                            if (l1 == par4 - 1 && par2Random.nextInt(4) != 0)
                            {
                                par1World.setBlock(k1, l1, i2, Block.cobblestoneMossy.blockID, 0, 2);
                            }
                            else
                            {
                                par1World.setBlock(k1, l1, i2, Block.cobblestone.blockID, 0, 2);
                            }
                        }
                    }
                }
            }

            k1 = 0;

            while (k1 < 2)
            {
                l1 = 0;

                while (true)
                {
                    if (l1 < 3)
                    {
                        label210:
                        {
                            i2 = par3 + par2Random.nextInt(l * 2 + 1) - l;
                            int j2 = par5 + par2Random.nextInt(i1 * 2 + 1) - i1;

                            if (par1World.isAirBlock(i2, par4, j2))
                            {
                                int k2 = 0;

                                if (par1World.getBlockMaterial(i2 - 1, par4, j2).isSolid())
                                {
                                    ++k2;
                                }

                                if (par1World.getBlockMaterial(i2 + 1, par4, j2).isSolid())
                                {
                                    ++k2;
                                }

                                if (par1World.getBlockMaterial(i2, par4, j2 - 1).isSolid())
                                {
                                    ++k2;
                                }

                                if (par1World.getBlockMaterial(i2, par4, j2 + 1).isSolid())
                                {
                                    ++k2;
                                }

                                if (k2 == 1)
                                {
                                    par1World.setBlock(i2, par4, j2, Block.chest.blockID, 0, 2);
                                    TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(i2, par4, j2);

                                    if (tileentitychest != null)
                                    {
                                        ChestGenHooks info = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
                                        WeightedRandomChestContent.generateChestContents(par2Random, info.getItems(par2Random), tileentitychest, info.getCount(par2Random));
                                    }

                                    break label210;
                                }
                            }

                            ++l1;
                            continue;
                        }
                    }

                    ++k1;
                    break;
                }
            }

            par1World.setBlock(par3, par4, par5, Block.mobSpawner.blockID, 0, 2);
            TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)par1World.getBlockTileEntity(par3, par4, par5);

            if (tileentitymobspawner != null)
            {
                tileentitymobspawner.func_98049_a().setMobID(this.pickMobSpawner(par2Random));
            }
            else
            {
                System.err.println("Failed to fetch mob spawner entity at (" + par3 + ", " + par4 + ", " + par5 + ")");
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Picks potentially a random item to add to a dungeon chest.
     */
    private ItemStack pickCheckLootItem(Random par1Random)
    {
        return ChestGenHooks.getOneItem(ChestGenHooks.DUNGEON_CHEST, par1Random);
    }

    /**
     * Randomly decides which spawner to use in a dungeon
     */
    private String pickMobSpawner(Random par1Random)
    {
        return DungeonHooks.getRandomDungeonMob(par1Random);
    }
}
