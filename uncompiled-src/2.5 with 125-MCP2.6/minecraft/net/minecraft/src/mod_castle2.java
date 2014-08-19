package net.minecraft.src;

import java.util.Random;
import net.minecraft.client.Minecraft;

public class mod_castle2 extends BaseMod
{
    int j;
    public static int MageblockID;
    public static int medallionID;
    public static int Castlespawnrate = 6;
    public static int Archer1blockID;
    public static int Archer2blockID;
    public static int Archer3blockID;
    public static int Knight1blockID;
    public static int Knight2blockID;
    public static int Knight3blockID;
    public static int Merc1BlockID;
    public static int Merc2BlockID;
    public static final Block guaSpawner;
    public static final Block gua2Spawner;
    public static int textureside = ModLoader.addOverride("/terrain.png", "/mercbench.png");
    public static int texturetop = ModLoader.addOverride("/terrain.png", "/mercbench1.png");
    public static final Block kniESpawner;
    public static final Block kni2Spawner;
    public static final Block kniSpawner;
    public static final Block arcESpawner;
    public static final Block arc2Spawner;
    public static final Block arcSpawner;
    public static final Block healSpawner;
    public static Item medallion;
    World world;

    public mod_castle2()
    {
        j = 64;
        ModLoader.addName(medallion, "Medallion");
        medallion.iconIndex = ModLoader.addOverride("/gui/items.png", "/Medallion.png");
        world = ModLoader.getMinecraftInstance().theWorld;
        ModLoader.registerTileEntity(net.minecraft.src.TileEntityhealSpawner.class, "Healer Post");
        ModLoader.registerBlock(healSpawner);
        ModLoader.addName(healSpawner, "Healer Post");
        ModLoader.addRecipe(new ItemStack(healSpawner, 1), new Object[]
                {
                    "YYY", "XXX", "XXX", 'X', Block.obsidian, 'Y', medallion
                });
        healSpawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/mageblock.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntityarcSpawner.class, "Archer Block");
        ModLoader.registerBlock(arcSpawner);
        ModLoader.addName(arcSpawner, "Archer Block");
        ModLoader.addRecipe(new ItemStack(arcSpawner, 1), new Object[]
                {
                    " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.bow
                });
        arcSpawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/archers.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntityarc2Spawner.class, "Archer Block");
        ModLoader.registerBlock(arc2Spawner);
        ModLoader.addName(arc2Spawner, "Diamond Archer Block");
        ModLoader.addRecipe(new ItemStack(arc2Spawner, 1), new Object[]
                {
                    "ZXZ", "XYX", "ZXZ", 'X', Item.ingotIron, 'Y', Item.bow, 'Z', Item.diamond
                });
        arc2Spawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/archers2.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntityarcESpawner.class, "Archer Block");
        ModLoader.registerBlock(arcESpawner);
        ModLoader.addName(arcESpawner, "Archer Block");
        arcESpawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/archersE.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntitykniSpawner.class, "Knight Block");
        ModLoader.registerBlock(kniSpawner);
        ModLoader.addName(kniSpawner, "Knight Block");
        ModLoader.addRecipe(new ItemStack(kniSpawner, 1), new Object[]
                {
                    " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordSteel
                });
        kniSpawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/soldiers.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntitykni2Spawner.class, "Diamond knight Block");
        ModLoader.registerBlock(kni2Spawner);
        ModLoader.addName(kni2Spawner, "Diamond knight Block");
        ModLoader.addRecipe(new ItemStack(kni2Spawner, 1), new Object[]
                {
                    " X ", "XYX", " X ", 'X', Item.ingotIron, 'Y', Item.swordDiamond
                });
        kni2Spawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/knights2.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntitykniESpawner.class, "Archer Block");
        ModLoader.registerBlock(kniESpawner);
        ModLoader.addName(kniESpawner, "Archer Block");
        kniESpawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/Knightpole.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntityguaSpawner.class, "Mercenary Post");
        ModLoader.registerBlock(guaSpawner);
        ModLoader.addName(guaSpawner, "Mercenary Post");
        ModLoader.addRecipe(new ItemStack(guaSpawner, 1), new Object[]
                {
                    "ZXZ", "XYX", "ZXZ", 'X', Block.planks, 'Y', Item.swordSteel, 'Z', Item.ingotGold
                });
        guaSpawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/healers.png");
        ModLoader.registerTileEntity(net.minecraft.src.TileEntitygua2Spawner.class, "Diamond Mercenary Post");
        ModLoader.registerBlock(gua2Spawner);
        ModLoader.addName(gua2Spawner, "Diamond Mercenary Post");
        ModLoader.addRecipe(new ItemStack(gua2Spawner, 1), new Object[]
                {
                    "KXK", "XYX", "KXK", 'X', Block.planks, 'Y', Item.swordDiamond, 'K', Item.ingotGold
                });
        gua2Spawner.blockIndexInTexture = ModLoader.addOverride("/terrain.png", "/healers.png");
    }

    public String getVersion()
    {
        return "1.0.0";
    }

    public void onBlockPlaced(World world1, int i, int k, int l, int i1)
    {
        int j1 = world1.getBlockMetadata(i, k, l);

        if ((j1 == 0 || i1 == 2) && world1.isBlockNormalCube(i, k, l + 1))
        {
            j1 = 2;
        }

        if ((j1 == 0 || i1 == 3) && world1.isBlockNormalCube(i, k, l - 1))
        {
            j1 = 3;
        }

        if ((j1 == 0 || i1 == 4) && world1.isBlockNormalCube(i + 1, k, l))
        {
            j1 = 4;
        }

        if ((j1 == 0 || i1 == 5) && world1.isBlockNormalCube(i - 1, k, l))
        {
            j1 = 5;
        }

        world1.setBlockMetadataWithNotify(i, k, l, j1);
    }

    public void generateSurface(World world1, Random random, int i, int k)
    {
        if (random.nextInt(Castlespawnrate) == 0)
        {
            for (int l = 0; l < 1; l++)
            {
                int i1 = (i + random.nextInt(8)) - random.nextInt(8);
                int j1 = (j + random.nextInt(8)) - random.nextInt(8);
                int k1 = (k + random.nextInt(8)) - random.nextInt(8);

                if (world1.getBlockId(i1 + 3, j1, k1 + 3) != Block.grass.blockID)
                {
                    continue;
                }

                int l1 = random.nextInt(2);

                for (int i2 = j1; i2 < j1 + 8; i2++)
                {
                    for (int j4 = 0; j4 < 14; j4++)
                    {
                        for (int k6 = 0; k6 < 14; k6++)
                        {
                            world1.setBlock(i1 + j4, i2, k1 + k6, Block.cobblestone.blockID);
                        }
                    }
                }

                for (int j2 = j1 + 1; j2 < j1 + 7; j2++)
                {
                    for (int k4 = 1; k4 < 13; k4++)
                    {
                        for (int l6 = 1; l6 < 13; l6++)
                        {
                            world1.setBlock(i1 + k4, j2, k1 + l6, 0);
                        }
                    }
                }

                for (int k2 = j1 + 7; k2 < j1 + 8; k2++)
                {
                    for (int l4 = 3; l4 < 11; l4++)
                    {
                        for (int i7 = 3; i7 < 11; i7++)
                        {
                            world1.setBlock(i1 + l4, k2, k1 + i7, 0);
                        }
                    }
                }

                for (int l2 = j1 + 8; l2 < j1 + 9; l2++)
                {
                    for (int i5 = 0; i5 < 14; i5++)
                    {
                        for (int j7 = 0; j7 < 14; j7++)
                        {
                            world1.setBlock(i1 + i5, l2, k1 + j7, Block.cobblestone.blockID);
                        }
                    }
                }

                for (int i3 = j1 + 8; i3 < j1 + 9; i3++)
                {
                    for (int j5 = 1; j5 < 13; j5++)
                    {
                        for (int k7 = 1; k7 < 13; k7++)
                        {
                            world1.setBlock(i1 + j5, i3, k1 + k7, 0);
                        }
                    }
                }

                world1.setBlock(i1, j1 + 9, k1, Block.cobblestone.blockID);
                world1.setBlock(i1 + 13, j1 + 9, k1, Block.cobblestone.blockID);
                world1.setBlock(i1, j1 + 9, k1 + 13, Block.cobblestone.blockID);
                world1.setBlock(i1 + 13, j1 + 9, k1 + 13, Block.cobblestone.blockID);
                world1.setBlock(i1 + 12, j1 + 7, k1 + 12, 232);
                world1.setBlock(i1 + 12, j1 + 7, k1 + 1, 232);
                world1.setBlock(i1 + 1, j1 + 7, k1 + 12, 232);
                world1.setBlock(i1 + 1, j1 + 7, k1 + 1, 232);

                for (int j3 = j1; j3 < j1 + 5; j3++)
                {
                    for (int k5 = 0; k5 < 1; k5++)
                    {
                        for (int l7 = 5; l7 < 9; l7++)
                        {
                            world1.setBlock(i1 + k5, j3, k1 + l7, 98);
                        }
                    }
                }

                for (int k3 = j1 + 1; k3 < j1 + 4; k3++)
                {
                    for (int l5 = 0; l5 < 1; l5++)
                    {
                        for (int i8 = 6; i8 < 8; i8++)
                        {
                            world1.setBlock(i1 + l5, k3, k1 + i8, 0);
                        }
                    }
                }

                world1.setBlock(i1, j1 + 3, k1 + 3, 0);
                world1.setBlock(i1, j1 + 3, k1 + 2, 0);
                world1.setBlock(i1, j1 + 3, k1 + 10, 0);
                world1.setBlock(i1, j1 + 3, k1 + 11, 0);
                world1.setBlock(i1 + 1, j1 + 1, k1 + 2, 232);
                world1.setBlock(i1 + 1, j1 + 1, k1 + 11, 232);
                world1.setBlock(i1 + 1, j1 + 1, k1 + 3, Block.cobblestone.blockID);
                world1.setBlock(i1 + 1, j1 + 1, k1 + 10, Block.cobblestone.blockID);

                for (int l3 = j1; l3 < j1 + 1; l3++)
                {
                    for (int i6 = 0; i6 < 14; i6++)
                    {
                        for (int j8 = 0; j8 < 14; j8++)
                        {
                            world1.setBlock(i1 + i6, l3, k1 + j8, 98);
                        }
                    }
                }

                world1.setBlock(i1 + 6, j1 + 1, k1 + 1, Block.ladder.blockID);
                Block.blocksList[world1.getBlockId(i1 + 6, j1 + 1, k1 + 1)].onBlockPlaced(world1, i1 + 6, j1 + 1, k1 + 1, 2);
                world1.setBlock(i1 + 6, j1 + 2, k1 + 1, Block.ladder.blockID);
                Block.blocksList[world1.getBlockId(i1 + 6, j1 + 2, k1 + 1)].onBlockPlaced(world1, i1 + 6, j1 + 2, k1 + 1, 2);
                world1.setBlock(i1 + 6, j1 + 3, k1 + 1, Block.ladder.blockID);
                Block.blocksList[world1.getBlockId(i1 + 6, j1 + 3, k1 + 1)].onBlockPlaced(world1, i1 + 6, j1 + 3, k1 + 1, 2);
                world1.setBlock(i1 + 6, j1 + 4, k1 + 1, Block.ladder.blockID);
                Block.blocksList[world1.getBlockId(i1 + 6, j1 + 4, k1 + 1)].onBlockPlaced(world1, i1 + 6, j1 + 4, k1 + 1, 2);
                world1.setBlock(i1 + 6, j1 + 5, k1 + 1, Block.ladder.blockID);
                Block.blocksList[world1.getBlockId(i1 + 6, j1 + 5, k1 + 1)].onBlockPlaced(world1, i1 + 6, j1 + 5, k1 + 1, 2);
                world1.setBlock(i1 + 6, j1 + 6, k1 + 1, Block.ladder.blockID);
                Block.blocksList[world1.getBlockId(i1 + 6, j1 + 6, k1 + 1)].onBlockPlaced(world1, i1 + 6, j1 + 6, k1 + 1, 2);
                world1.setBlock(i1 + 6, j1 + 7, k1 + 1, Block.ladder.blockID);
                Block.blocksList[world1.getBlockId(i1 + 6, j1 + 7, k1 + 1)].onBlockPlaced(world1, i1 + 6, j1 + 7, k1 + 1, 2);

                if (world1.getBlockId(i1 - 1, j1, k1 - 1) == Block.grass.blockID || world1.getBlockId(i1 - 1, j1, k1 - 1) == Block.dirt.blockID)
                {
                    world1.setBlock(i1 - 1, j1 + 1, k1 - 1, 231);
                }

                if (world1.getBlockId(i1 + 15, j1, k1 + 15) == Block.grass.blockID || world1.getBlockId(i1 + 15, j1, k1 + 15) == Block.dirt.blockID)
                {
                    world1.setBlock(i1 + 15, j1 + 1, k1 + 15, 231);
                }

                if (world1.getBlockId(i1 - 1, j1, k1 + 15) == Block.grass.blockID || world1.getBlockId(i1 - 1, j1, k1 + 15) == Block.dirt.blockID)
                {
                    world1.setBlock(i1 - 1, j1 + 1, k1 + 15, 231);
                }

                if (world1.getBlockId(i1 + 15, j1, k1 - 1) == Block.grass.blockID || world1.getBlockId(i1 + 15, j1, k1 - 1) == Block.dirt.blockID)
                {
                    world1.setBlock(i1 + 15, j1 + 1, k1 - 1, 231);
                }

                world1.setBlock(i1 + 11, j1 + 1, k1 + 4, 231);
                world1.setBlock(i1 + 11, j1 + 1, k1 + 9, 231);

                for (int i4 = j1 + 1; i4 < j1 + 2; i4++)
                {
                    for (int j6 = 12; j6 < 13; j6++)
                    {
                        for (int k8 = 1; k8 < 13; k8++)
                        {
                            world1.setBlock(i1 + j6, i4, k1 + k8, 5);
                        }
                    }
                }

                world1.setBlock(i1 + 12, j1 + 2, k1 + 4, Block.torchWood.blockID);
                world1.setBlock(i1 + 12, j1 + 2, k1 + 9, Block.torchWood.blockID);
                world1.setBlock(i1 + 12, j1 + 1, k1 + 6, 54);
                world1.setBlock(i1 + 12, j1 + 1, k1 + 7, 54);
                TileEntityChest tileentitychest = (TileEntityChest)world1.getBlockTileEntity(i1 + 12, j1 + 1, k1 + 7);
                ItemStack itemstack = pickCheckLootItem(random);
                ItemStack itemstack1 = pickCheckLootItem2(random);
                ItemStack itemstack2 = pickCheckLootItem3(random);

                if (itemstack != null)
                {
                    tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack);
                    tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack1);
                    tileentitychest.setInventorySlotContents(random.nextInt(tileentitychest.getSizeInventory()), itemstack2);
                }

                int l8 = random.nextInt(4);

                if (l8 == 1)
                {
                    world1.setBlock(i1 + 9, j1 + 1, k1 + 10, Block.tnt.blockID);
                    world1.setBlock(i1 + 9, j1 + 2, k1 + 10, Block.tnt.blockID);
                    world1.setBlock(i1 + 10, j1 + 1, k1 + 10, Block.tnt.blockID);
                }

                if (l8 == 2)
                {
                    world1.setBlock(i1 + 9, j1 + 1, k1 + 10, 42);
                    world1.setBlock(i1 + 9, j1 + 1, k1 + 9, 42);
                    world1.setBlock(i1 + 9, j1 + 2, k1 + 9, 42);
                }

                if (l8 == 3)
                {
                    world1.setBlock(i1 + 9, j1 + 1, k1 + 10, 41);
                    world1.setBlock(i1 + 9, j1 + 2, k1 + 10, 41);
                }

                if (l8 == 0)
                {
                    world1.setBlock(i1 + 9, j1 + 1, k1 + 10, 22);
                    world1.setBlock(i1 + 9, j1 + 1, k1 + 11, 22);
                    world1.setBlock(i1 + 9, j1 + 2, k1 + 11, 22);
                }
            }
        }
    }

    private ItemStack pickCheckLootItem(Random random)
    {
        return new ItemStack(medallion);
    }

    private ItemStack pickCheckLootItem3(Random random)
    {
        int i = random.nextInt(7);

        if (i == 0)
        {
            return new ItemStack(Item.arrow, random.nextInt(30) + 10);
        }

        if (i == 1)
        {
            return new ItemStack(Item.bread);
        }

        if (i == 2)
        {
            return new ItemStack(Item.swordSteel);
        }

        if (i == 3)
        {
            return new ItemStack(Item.swordDiamond);
        }

        if (i == 4)
        {
            return new ItemStack(Item.bowlSoup);
        }

        if (i == 5)
        {
            return new ItemStack(Item.diamond, random.nextInt(1) + 1);
        }

        if (i == 6)
        {
            return new ItemStack(Item.axeSteel);
        }
        else
        {
            return null;
        }
    }

    private ItemStack pickCheckLootItem2(Random random)
    {
        int i = random.nextInt(11);

        if (i == 0)
        {
            return new ItemStack(Item.ingotGold, random.nextInt(4) + 1);
        }

        if (i == 1)
        {
            return new ItemStack(Item.ingotIron, random.nextInt(4) + 1);
        }

        if (i == 2)
        {
            return new ItemStack(Item.ingotGold, random.nextInt(4) + 1);
        }

        if (i == 3)
        {
            return new ItemStack(Item.compass);
        }

        if (i == 4)
        {
            return new ItemStack(Item.gunpowder, random.nextInt(7) + 1);
        }

        if (i == 5)
        {
            return new ItemStack(Item.arrow, random.nextInt(22) + 8);
        }

        if (i == 6)
        {
            return new ItemStack(Item.bucketEmpty);
        }

        if (i == 7 && random.nextInt(10) == 0)
        {
            return new ItemStack(Item.appleGold);
        }

        if (i == 8 && random.nextInt(2) == 0)
        {
            return new ItemStack(Item.redstone, random.nextInt(9) + 1);
        }

        if (i == 9 && random.nextInt(10) == 0)
        {
            return new ItemStack(Item.diamond, random.nextInt(1) + 1);
        }

        if (i == 10)
        {
            return new ItemStack(Item.egg, random.nextInt(4) + 1);
        }
        else
        {
            return null;
        }
    }

    public void load()
    {
    }

    static
    {
        MageblockID = 230;
        medallionID = 1600;
        Archer1blockID = 239;
        Archer2blockID = 235;
        Archer3blockID = 232;
        Knight1blockID = 238;
        Knight2blockID = 236;
        Knight3blockID = 231;
        Merc1BlockID = 234;
        Merc2BlockID = 233;
        healSpawner = (new BlockhealSpawner(MageblockID, 0)).setHardness(1.0F).setBlockName("healSpawner").disableStats();
        medallion = (new Item(medallionID)).setItemName("Medallion");
        arcSpawner = (new BlockarcSpawner(Archer1blockID, 0)).setHardness(2.0F).setBlockName("arcSpawner").disableStats();
        arc2Spawner = (new Blockarc2Spawner(Archer2blockID, 0)).setHardness(2.0F).setBlockName("arc2Spawner").disableStats();
        arcESpawner = (new BlockarcESpawner(Archer3blockID, 0)).setHardness(2.0F).setBlockName("arcESpawner").disableStats();
        kniSpawner = (new BlockkniSpawner(Knight1blockID, 0)).setHardness(2.0F).setBlockName("kniSpawner").disableStats();
        kni2Spawner = (new Blockkni2Spawner(Knight2blockID, 0)).setHardness(2.0F).setBlockName("kni2Spawner").disableStats();
        kniESpawner = (new BlockkniESpawner(Knight3blockID, 0)).setHardness(1.0F).setBlockName("kniESpawner").disableStats();
        guaSpawner = (new BlockguaSpawner(Merc1BlockID, 0)).setHardness(0.5F).setBlockName("guaSpawner").disableStats();
        gua2Spawner = (new Blockgua2Spawner(Merc2BlockID, 0)).setHardness(0.5F).setBlockName("gua2Spawner").disableStats();
    }
}
