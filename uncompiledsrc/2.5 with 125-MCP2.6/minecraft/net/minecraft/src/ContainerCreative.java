package net.minecraft.src;

import java.util.*;

class ContainerCreative extends Container
{
    /** the list of items in this container */
    public List itemList;

    public ContainerCreative(EntityPlayer par1EntityPlayer)
    {
        itemList = new ArrayList();
        Block ablock[] =
        {
            Block.cobblestone, Block.stone, Block.oreDiamond, Block.oreGold, Block.oreIron, Block.oreCoal, Block.oreLapis, Block.oreRedstone, Block.stoneBrick, Block.stoneBrick,
            Block.stoneBrick, Block.stoneBrick, Block.blockClay, Block.blockDiamond, Block.blockGold, Block.blockSteel, Block.bedrock, Block.blockLapis, Block.brick, Block.cobblestoneMossy,
            Block.stairSingle, Block.stairSingle, Block.stairSingle, Block.stairSingle, Block.stairSingle, Block.stairSingle, Block.obsidian, Block.netherrack, Block.slowSand, Block.glowStone,
            Block.wood, Block.wood, Block.wood, Block.wood, Block.leaves, Block.leaves, Block.leaves, Block.leaves, Block.dirt, Block.grass,
            Block.sand, Block.sandStone, Block.sandStone, Block.sandStone, Block.gravel, Block.web, Block.planks, Block.planks, Block.planks, Block.planks,
            Block.sapling, Block.sapling, Block.sapling, Block.sapling, Block.deadBush, Block.sponge, Block.ice, Block.blockSnow, Block.plantYellow, Block.plantRed,
            Block.mushroomBrown, Block.mushroomRed, Block.cactus, Block.melon, Block.pumpkin, Block.pumpkinLantern, Block.vine, Block.fenceIron, Block.thinGlass, Block.netherBrick,
            Block.netherFence, Block.stairsNetherBrick, Block.whiteStone, Block.mycelium, Block.waterlily, Block.tallGrass, Block.tallGrass, Block.chest, Block.workbench, Block.glass,
            Block.tnt, Block.bookShelf, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth,
            Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.cloth, Block.dispenser, Block.stoneOvenIdle,
            Block.music, Block.jukebox, Block.pistonStickyBase, Block.pistonBase, Block.fence, Block.fenceGate, Block.ladder, Block.rail, Block.railPowered, Block.railDetector,
            Block.torchWood, Block.stairCompactPlanks, Block.stairCompactCobblestone, Block.stairsBrick, Block.stairsStoneBrickSmooth, Block.lever, Block.pressurePlateStone, Block.pressurePlatePlanks, Block.torchRedstoneActive, Block.button,
            Block.trapdoor, Block.enchantmentTable, Block.redstoneLampIdle
        };
        int i = 0;
        int j = 0;
        int k = 0;
        int l = 0;
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        int l1 = 0;
        int i2 = 1;

        for (int j2 = 0; j2 < ablock.length; j2++)
        {
            int k2 = 0;

            if (ablock[j2] == Block.cloth)
            {
                k2 = i++;
            }
            else if (ablock[j2] == Block.stairSingle)
            {
                k2 = j++;
            }
            else if (ablock[j2] == Block.wood)
            {
                k2 = k++;
            }
            else if (ablock[j2] == Block.planks)
            {
                k2 = l++;
            }
            else if (ablock[j2] == Block.sapling)
            {
                k2 = i1++;
            }
            else if (ablock[j2] == Block.stoneBrick)
            {
                k2 = j1++;
            }
            else if (ablock[j2] == Block.sandStone)
            {
                k2 = k1++;
            }
            else if (ablock[j2] == Block.tallGrass)
            {
                k2 = i2++;
            }
            else if (ablock[j2] == Block.leaves)
            {
                k2 = l1++;
            }

            itemList.add(new ItemStack(ablock[j2], 1, k2));
        }

        Block ablock1[] = Block.blocksList;
        int k3 = ablock1.length;

        for (int l3 = 0; l3 < k3; l3++)
        {
            Block block = ablock1[l3];

            if (block != null)
            {
                block.addCreativeItems((ArrayList)itemList);
            }
        }

        int j3 = 0;
        Item aitem[] = Item.itemsList;
        int i4 = aitem.length;

        for (int j4 = 0; j4 < i4; j4++)
        {
            Item item = aitem[j4];

            if (j3++ >= 256 && item != null)
            {
                item.addCreativeItems((ArrayList)itemList);
            }
        }

        Integer integer;

        for (Iterator iterator = EntityList.entityEggs.keySet().iterator(); iterator.hasNext(); itemList.add(new ItemStack(Item.monsterPlacer.shiftedIndex, 1, integer.intValue())))
        {
            integer = (Integer)iterator.next();
        }

        InventoryPlayer inventoryplayer = par1EntityPlayer.inventory;

        for (int l2 = 0; l2 < 9; l2++)
        {
            for (int k4 = 0; k4 < 8; k4++)
            {
                addSlot(new Slot(GuiContainerCreative.getInventory(), k4 + l2 * 8, 8 + k4 * 18, 18 + l2 * 18));
            }
        }

        for (int i3 = 0; i3 < 9; i3++)
        {
            addSlot(new Slot(inventoryplayer, i3, 8 + i3 * 18, 184));
        }

        scrollTo(0.0F);
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return true;
    }

    /**
     * Updates the gui slots ItemStack's based on scroll position.
     */
    public void scrollTo(float par1)
    {
        int i = (itemList.size() / 8 - 8) + 1;
        int j = (int)((double)(par1 * (float)i) + 0.5D);

        if (j < 0)
        {
            j = 0;
        }

        for (int k = 0; k < 9; k++)
        {
            for (int l = 0; l < 8; l++)
            {
                int i1 = l + (k + j) * 8;

                if (i1 >= 0 && i1 < itemList.size())
                {
                    GuiContainerCreative.getInventory().setInventorySlotContents(l + k * 8, (ItemStack)itemList.get(i1));
                }
                else
                {
                    GuiContainerCreative.getInventory().setInventorySlotContents(l + k * 8, (ItemStack)null);
                }
            }
        }
    }

    protected void retrySlotClick(int i, int j, boolean flag, EntityPlayer entityplayer)
    {
    }
}
