package mods.castledefenders.common.worldgenerator;

import java.util.Random;

import mods.castledefenders.common.CastleDefendersTabs;
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
	
	
	private void generateSurface(World world, Random random, int wolrdX, int wolrdZ)
    {
		// Position initial de la génération en hauteur
        byte worldY = 64;
        int var6 = random.nextInt(3);
        
        // var6 == 0 supprime le switch case
        // Du coups on n'a que un seul type de batimant
        if (var6 == 0)
        {
            int ramdom8M8_X;
            int ramdom8M8_Y;
            int ramdom8M8_Z;
            int x;
            int y;
            int z;
            
            // Batiment 1 1 chance sur 3
            if (random.nextInt(ModCastleDefenders.mercenarySpawnRate) == 0 && var6 == 0)
            {
                ramdom8M8_X = wolrdX + random.nextInt(8) - random.nextInt(8);
                ramdom8M8_Y = worldY + random.nextInt(8) - random.nextInt(8);
                ramdom8M8_Z = wolrdZ + random.nextInt(8) - random.nextInt(8);
                
                
                //Test si on est sur de la terre
                if (world.getBlockId(ramdom8M8_X + 3, ramdom8M8_Y - 1, ramdom8M8_Z + 3) == Block.grass.blockID)
                {
                	// Vide tous les blocks du cube
                	//  - y : 0
                	//  - x : 0
                	//  - z : 0
                	//  - HauteurY  : 8
                	//  - largeurX  : 11
                	//  - longueurZ : 11
                    for (y = ramdom8M8_Y; y < ramdom8M8_Y + y; ++y)
                    {
                        for (x = 0; x< 11; ++x)
                        {
                            for (z = 0; z < 11; ++z)
                            {
                                world.setBlock(ramdom8M8_X + x, y, ramdom8M8_Z + z, 0, 0, 2);
                            }
                        }
                    }
                    
                    // Remplie de bois
                	//  - y : 0
                	//  - x : 0
                	//  - z : 0
                	//  - HauteurY  : 4
                	//  - largeurX  : 6
                	//  - longueurZ : 11
                    for (y = ramdom8M8_Y; y < ramdom8M8_Y + 4; ++y)
                    {
                        for (x = 0; x < 6; ++x)
                        {
                            for (z = 0; z < 11; ++z)
                            {
                                world.setBlock(ramdom8M8_X + x, z, ramdom8M8_Z + z, 5);
                            }
                        }
                    }
                    
                    // Vide un contenu surment mais plus petit ca fabrique un muret de 5 de haut :)
                	//  - y : 0
                	//  - x : 1
                	//  - z : 1
                	//  - HauteurY  : 3
                	//  - largeurX  : 6
                	//  - longueurZ : 11
                    for (y = ramdom8M8_Y; y < ramdom8M8_Y + 3; ++y)
                    {
                        for (x = 1; x < 5; ++x)
                        {
                            for (z = 1; z < 10; ++z)
                            {
                                world.setBlock(ramdom8M8_X + x, y, ramdom8M8_Z + z, 0);
                            }
                        }
                    }
                    
//                    net.minecraft.block.Block.chest;
//                    mods.castledefenders.common.ModCastleDefenders.blockKnight2
                    
                    // Vide des emplacement et pose des torches (id 50 = torche)
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y, ramdom8M8_Z + 5, 0);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y + 1, ramdom8M8_Z + 5, 0);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 1, ramdom8M8_Z + 4, 50);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 1, ramdom8M8_Z + 6, 50);

                    if (world.getBlockId(ramdom8M8_X + 3, ramdom8M8_Y + 1, ramdom8M8_Z - 1) == 0)
                    {
                        world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 1, ramdom8M8_Z, 0);
                    }
                    else
                    {
                        world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 1, ramdom8M8_Z, 50);
                    }
                    
                    
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y, ramdom8M8_Z + 1, 61); // Le four
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y, ramdom8M8_Z + 2, 54); // Le coffre
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y, ramdom8M8_Z + 3, 54); // Le coffre
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y, ramdom8M8_Z + 8, 26); // Le lit
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y, ramdom8M8_Z + 9, 26); // Le lit
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y, ramdom8M8_Z + 8, 26); // Le lit
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y, ramdom8M8_Z + 9, 26); // Le lit
                    
                    // Creer des barrier (id 85 = barrierre)
                    for (x = 6; x < 11; ++x)
                    {
                        for (y = 0; y < 11; ++y)
                        {
                            if (world.getBlockId(ramdom8M8_X + x, ramdom8M8_Y - 1, ramdom8M8_Z + y) != 0)
                            {
                                world.setBlock(ramdom8M8_X + x, ramdom8M8_Y, ramdom8M8_Z + y, 85);
                            }
                        }
                    }
                    
                    // Vide
                    for (x = 6; x < 10; ++x)
                    {
                        for (y = 1; y < 10; ++y)
                        {
                            world.setBlock(ramdom8M8_X + x, ramdom8M8_Y, ramdom8M8_Z + y, 0);
                        }
                    }
                    
                    // Ajoute le block mercenaire
                    world.setBlock(ramdom8M8_X + 10, ramdom8M8_Y, ramdom8M8_Z + 5, 0);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y - 1, ramdom8M8_Z + 2, ModCastleDefenders.blockMerc.blockID);
                    
                    
                    // Ajoute un block mercenaire a coté de al maison si la zone n'est pas vide
                    if (world.getBlockId(ramdom8M8_X + 8, ramdom8M8_Y - 1, ramdom8M8_Z + 2) != 0)
                    {
                        world.setBlock(ramdom8M8_X + 8, ramdom8M8_Y - 1, ramdom8M8_Z + 2, ModCastleDefenders.blockMerc.blockID);
                    }
                    
                    
                    // ajoute des tile entity
                    for (x = 0; x < 2; ++x)
                    {
                        TileEntityChest world5 = (TileEntityChest)world.getBlockTileEntity(ramdom8M8_X + 1, ramdom8M8_Y, ramdom8M8_Z + 3);
                        ItemStack world4 = this.pickCheckLootItem(random);

                        if (world4 != null)
                        {
                            world5.setInventorySlotContents(random.nextInt(world5.getSizeInventory()), world4);
                        }
                    }
                    
                    // Met des dale de bois et du bois (5 bois, 126 dalle de bois)
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 1, 126);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 2, 126);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 3, 126);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 4, 5);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 5, 5);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 6, 5);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 7, 126);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 8, 126);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 4, ramdom8M8_Z + 9, 126);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 4, ramdom8M8_Z + 1, 126);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 4, ramdom8M8_Z + 1, 126);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 1, 126);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 2, 126);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 3, 126);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 4, 5);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 5, 5);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 6, 5);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 7, 126);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 8, 126);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 4, ramdom8M8_Z + 9, 126);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 4, ramdom8M8_Z + 9, 126);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 4, ramdom8M8_Z + 9, 126);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 5, ramdom8M8_Z + 4, 5);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 5, ramdom8M8_Z + 5, 5);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 5, ramdom8M8_Z + 6, 5);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 5, ramdom8M8_Z + 4, 5);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 5, ramdom8M8_Z + 5, 5);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 5, ramdom8M8_Z + 6, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 4, ramdom8M8_Z + 6, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 4, ramdom8M8_Z + 4, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 5, ramdom8M8_Z + 6, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 5, ramdom8M8_Z + 4, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 5, ramdom8M8_Z + 4, 0);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 4, ramdom8M8_Z + 4, 0);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 5, ramdom8M8_Z + 6, 0);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 4, ramdom8M8_Z + 6, 0);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 2, ramdom8M8_Z + 5, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 1, ramdom8M8_Z + 5, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 0, ramdom8M8_Z + 5, 5);
                    world.setBlock(ramdom8M8_X, ramdom8M8_Y + 3, ramdom8M8_Z, 126);
                    world.setBlock(ramdom8M8_X, ramdom8M8_Y + 3, ramdom8M8_Z + 10, 126);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y + 3, ramdom8M8_Z, 126);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y + 3, ramdom8M8_Z + 10, 126);

                    for (x = ramdom8M8_Y + 6; x < ramdom8M8_Y + 7; ++x)
                    {
                        for (y = 1; y < 5; ++y)
                        {
                            for (z = 3; z < 8; ++z)
                            {
                                world.setBlock(ramdom8M8_X + y, x, ramdom8M8_Z + z, 5);
                            }
                        }
                    }

                    for (x = ramdom8M8_Y + 7; x < ramdom8M8_Y + 8; ++x)
                    {
                        for (y = 1; y < 5; ++y)
                        {
                            for (z = 3; z < 8; ++z)
                            {
                                world.setBlock(ramdom8M8_X + y, x, ramdom8M8_Z + z, 126);
                            }
                        }
                    }

                    for (x = ramdom8M8_Y + 7; x < ramdom8M8_Y + 8; ++x)
                    {
                        for (y = 2; y < 4; ++y)
                        {
                            for (z = 4; z < 7; ++z)
                            {
                                world.setBlock(ramdom8M8_X + y, x, ramdom8M8_Z + z, 0);
                            }
                        }
                    }

                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 7, ramdom8M8_Z + 3, 5);
                    world.setBlock(ramdom8M8_X + 1, ramdom8M8_Y + 7, ramdom8M8_Z + 7, 5);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 7, ramdom8M8_Z + 3, 5);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 7, ramdom8M8_Z + 7, 5);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y + 6, ramdom8M8_Z + 6, ModCastleDefenders.blockArcherM.blockID);
                    
                    // Pose les ecchlle
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 6, ramdom8M8_Z + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 5, ramdom8M8_Z + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 4, ramdom8M8_Z + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 3, ramdom8M8_Z + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 2, ramdom8M8_Z + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 1, ramdom8M8_Z + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                    world.setBlock(ramdom8M8_X + 3, ramdom8M8_Y + 0, ramdom8M8_Z + 5, Block.ladder.blockID, 1 << Direction.facingToDirection[Facing.oppositeSide[5]], 2);
                }
            }

            if (random.nextInt(ModCastleDefenders.mercenarySpawnRate) == 0 && (var6 == 1 || var6 == 2))
            {
                ramdom8M8_X = wolrdX + random.nextInt(8) - random.nextInt(8);
                ramdom8M8_Y = worldY + random.nextInt(8) - random.nextInt(8);
                ramdom8M8_Z = wolrdZ + random.nextInt(8) - random.nextInt(8);

                if (world.getBlockId(ramdom8M8_X, ramdom8M8_Y - 1, ramdom8M8_Z) == Block.grass.blockID && world.getBlockId(ramdom8M8_X + 7, ramdom8M8_Y - 1, ramdom8M8_Z + 1) != 0 && world.getBlockId(ramdom8M8_X + 7, ramdom8M8_Y, ramdom8M8_Z + 3) != 0)
                {
                    for (x = ramdom8M8_Y; x < ramdom8M8_Y + 8; ++x)
                    {
                        for (y = 0; y < 9; ++y)
                        {
                            for (z = 0; z < 4; ++z)
                            {
                                world.setBlock(ramdom8M8_X + y, x, ramdom8M8_Z + z, 0, 0, 2);
                            }
                        }
                    }

                    for (x = ramdom8M8_Y; x < ramdom8M8_Y + 1; ++x)
                    {
                        for (y = 4; y < 7; ++y)
                        {
                            for (z = 0; z < 5; ++z)
                            {
                                world.setBlock(ramdom8M8_X + y, x, ramdom8M8_Z + z, 35);
                            }
                        }
                    }

                    for (x = ramdom8M8_Y; x < ramdom8M8_Y + 1; ++x)
                    {
                        for (y = 4; y < 7; ++y)
                        {
                            for (z = 1; z < 4; ++z)
                            {
                                world.setBlock(ramdom8M8_X + y, x, ramdom8M8_Z + z, 0);
                            }
                        }
                    }

                    world.setBlock(ramdom8M8_X + 7, ramdom8M8_Y, ramdom8M8_Z + 1, 35);
                    world.setBlock(ramdom8M8_X + 7, ramdom8M8_Y, ramdom8M8_Z + 2, 35);
                    world.setBlock(ramdom8M8_X + 7, ramdom8M8_Y + 1, ramdom8M8_Z + 2, 35);
                    world.setBlock(ramdom8M8_X + 7, ramdom8M8_Y, ramdom8M8_Z + 3, 35);
                    world.setBlock(ramdom8M8_X + 8, ramdom8M8_Y, ramdom8M8_Z + 2, 35);
                    world.setBlock(ramdom8M8_X + 6, ramdom8M8_Y + 2, ramdom8M8_Z + 2, 35);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y + 2, ramdom8M8_Z + 2, 35);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 2, ramdom8M8_Z + 2, 35);
                    world.setBlock(ramdom8M8_X + 6, ramdom8M8_Y + 1, ramdom8M8_Z + 1, 35);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y + 1, ramdom8M8_Z + 1, 35);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 1, ramdom8M8_Z + 1, 35);
                    world.setBlock(ramdom8M8_X + 6, ramdom8M8_Y + 1, ramdom8M8_Z + 3, 35);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y + 1, ramdom8M8_Z + 3, 35);
                    world.setBlock(ramdom8M8_X + 4, ramdom8M8_Y + 1, ramdom8M8_Z + 3, 35);
                    world.setBlock(ramdom8M8_X + 6, ramdom8M8_Y + 1, ramdom8M8_Z + 2, 50);
                    world.setBlock(ramdom8M8_X + 7, ramdom8M8_Y, ramdom8M8_Z + 2, 85);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y, ramdom8M8_Z + 1, 85);
                    world.setBlock(ramdom8M8_X + 5, ramdom8M8_Y, ramdom8M8_Z + 3, 85);

                    for (x = 0; x < 4; ++x)
                    {
                        for (y = 0; y < 5; ++y)
                        {
                            world.setBlock(ramdom8M8_X + x, ramdom8M8_Y, ramdom8M8_Z + y, 85);
                        }
                    }

                    for (x = 1; x < 4; ++x)
                    {
                        for (y = 1; y < 4; ++y)
                        {
                            world.setBlock(ramdom8M8_X + x, ramdom8M8_Y, ramdom8M8_Z + y, 0);
                        }
                    }

                    world.setBlock(ramdom8M8_X, ramdom8M8_Y, ramdom8M8_Z + 2, 0);
                    world.setBlock(ramdom8M8_X + 2, ramdom8M8_Y - 1, ramdom8M8_Z + 2, ModCastleDefenders.blockMerc.blockID);
                }
            }
        }
    }


    private ItemStack pickCheckLootItem(Random world) {
        int random = world.nextInt(6);
        return random == 0 ? new ItemStack(Item.arrow, world.nextInt(30) + 10) : (random == 1 ? new ItemStack(Item.swordIron) : (random == 2 ? new ItemStack(Item.goldNugget) : (random == 3 ? new ItemStack(Item.goldNugget) : (random == 4 ? new ItemStack(Item.bread) : (random == 5 ? new ItemStack(Item.carrot) : null)))));
    }
}
