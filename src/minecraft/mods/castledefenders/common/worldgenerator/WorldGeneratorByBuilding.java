package mods.castledefenders.common.worldgenerator;

import java.util.ArrayList;
import java.util.Random;

import mods.castledefenders.common.ModCastleDefenders;
import mods.castledefenders.common.building.Building;
import mods.castledefenders.common.building.Building.Unity;
import mods.castledefenders.common.building.Building.Unity.Content;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockWall;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGeneratorByBuilding implements IWorldGenerator {

	public static final int DIMENSION_ID_NETHER = -1;
	public static final int DIMENSION_ID_SURFACE = 0;
	
	private class BuildingAndInfos {
		Building building;
		int spawnRate;
	}
	
	/**
	 * Spawn global de tous les batiment de cette instance de worldGenerator
	 */
	int globalSpawnRate = 0;

	private ArrayList<BuildingAndInfos> buildingsNether  = new ArrayList<BuildingAndInfos> ();
	private ArrayList<BuildingAndInfos> buildingsSurface = new ArrayList<BuildingAndInfos> ();
	
	
	
	public WorldGeneratorByBuilding(int globalSpawnRate) {
		this.globalSpawnRate = globalSpawnRate;
	}
	

	public void addbuilding(Building building, int buildingSpawnRate) {
		this.addbuilding(building, buildingSpawnRate, this.DIMENSION_ID_SURFACE);
	}
	
	/**
	 * Ajoute un batiment
	 * @param buildingMercenary1
	 * @param mercenaryBuilding1SpawnRate
	 * @param dimensionIdSurface
	 */
	public void addbuilding(Building building, int buildingSpawnRate, int dimensionId) {
		
		BuildingAndInfos buildingAndInfos = new BuildingAndInfos ();
		buildingAndInfos.building         = building;
		buildingAndInfos.spawnRate        = buildingSpawnRate;
		
		switch (dimensionId) {
			case WorldGeneratorByBuilding.DIMENSION_ID_NETHER:
				this.buildingsNether.add(buildingAndInfos);
				break;
				
			case WorldGeneratorByBuilding.DIMENSION_ID_SURFACE:
				this.buildingsSurface.add(buildingAndInfos);
				break;
			default:
		}
	}
	
	/**
	 * Methode de genera
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		// Generation diffenrente entre le nether et la surface
		switch (world.provider.dimensionId) {
			case WorldGeneratorByBuilding.DIMENSION_ID_NETHER:
				this.generateBuilding(world, random, chunkX, chunkZ, buildingsNether);
				break;
				
			case WorldGeneratorByBuilding.DIMENSION_ID_SURFACE:
				this.generateBuilding(world, random, chunkX, chunkZ, buildingsSurface);
				break;
			default:
		}
	}
	
	/**
	 * Renvoie le spawn total de tous une liste de batiment
	 * @param buildins
	 * @return
	 */
	private int totalRateSpawnByBuildingList (ArrayList<BuildingAndInfos> buildings) {
		int total = 0;
		for (BuildingAndInfos building : buildings) {
			total += building.spawnRate;
		}
		return total;
	}
	
	/**
	 * Charge le batiment de maniere aléatoire en fonction du ratio
	 * @param buildings
	 * @param totalRate
	 * @return
	 */
	private Building getBuildingInRate(ArrayList<BuildingAndInfos> buildings, Random random) {
		
		ArrayList<Building>buildingsForRate = new ArrayList<Building>();
		
		for (BuildingAndInfos buildingAndInfos : buildings) {
			for (int i = 0; i < buildingAndInfos.spawnRate; i++) {
				buildingsForRate.add(buildingAndInfos.building);
			}
		}
		
		return buildingsForRate.get(random.nextInt(this.totalRateSpawnByBuildingList (buildings)));
	}
	
	/**
	 * Genere le batiment dans le terrain correspondant
	 * @param world
	 * @param random
	 * @param wolrdX
	 * @param wolrdZ
	 * @param buildings
	 * @param random
	 */
	private void generateBuilding(World world, Random random, int chunkX, int chunkZ, ArrayList<BuildingAndInfos> buildings) {
		
		if (buildings.size() == 0) {
			return;
		}
		
		// test du Spawn global
		if (random.nextInt(50) < Math.min (this.globalSpawnRate, 50)) { // Pour test sur un superflat
//		if (random.nextInt(50) < Math.min (this.globalSpawnRate, 10)) {
			

			// Position initial de la génération en hauteur
			int worldY = 64;
			int rotate = random.nextInt(Building.ROTATED_180);
			Building building = this.getBuildingInRate (buildings, random).getRotatetedBuilding (rotate);
			
			// Position initiale du batiment
			int initX = chunkX * 16 + random.nextInt(8) - random.nextInt(8);
			int initY = worldY      + random.nextInt(8) - random.nextInt(8);
			int initZ = chunkZ * 16 + random.nextInt(8) - random.nextInt(8);
			
			// Pour test sur un superflat
			initY = 3;
			
			//Test si on est sur de la terre (faudrais aps que le batiment vol)
			if (world.getBlockId(initX + 3, initY, initZ + 3) == Block.grass.blockID) {
				
				ModCastleDefenders.log.info("Create building width matrix :"+initX+" "+initY+" "+initZ);
				
				// Parcours la matrice et ajoute les blocks
				for (int x= 0; x < building.maxX; x++) {
					for (int y= 0; y < building.maxY; y++) {
						for (int z= 0; z < building.maxZ; z++) {
							
							Unity unity = building.get(x, y, z);
							
							// Position réél dans le monde du block
							int finalX = initX + x;
							int finalY = initY + y;
							int finalZ = initZ + z;
							
							if (unity.block != null) {
								world.setBlock(finalX, finalY, finalZ, unity.block.blockID, unity.metadata, 2);
							} else {
								world.setBlock(finalX, finalY, finalZ, 0, 0, 2);
							}

							this.setOrientation (world, finalX, finalY, finalZ, unity.orientation, rotate);
							this.setContents    (world, random, finalX, finalY, finalZ, unity.contents);
						}
					}
				}
				
				
				/////////////////////////////////////////////////////////////
				// Rempli en dessous du batiment pour pas que ca sois vide //
				/////////////////////////////////////////////////////////////
				
				int maxYdown = 0;
				
				for (int x= 0; x < building.maxX; x++) {
					for (int z= 0; z < building.maxZ; z++) {
						
						int y = -1;
						int finalX = initX + x;
						int finalY = initY + y;
						int finalZ = initZ + z;
						// recherche la profondeur maxi de Y
						while (
							world.getBlockId(finalX, finalY, finalZ) != Block.grass.blockID &&
							world.getBlockId(finalX, finalY, finalZ) != Block.stone.blockID &&
							world.getBlockId(finalX, finalY, finalZ) != Block.dirt.blockID &&
							world.getBlockId(finalX, finalY, finalZ) != Block.bedrock.blockID &&
							y > 0
						){
							
							maxYdown = Math.min (maxYdown, y);
							
							y--;
							finalX = initX + x;
							finalY = initY + y;
							finalZ = initZ + z;
						}
					}
				}
				// Crée un escalier sur les block de remplissage pour que ca sois plus jolie
				for (int y = maxYdown; y < 0; y++) {
					
					for (int x = y; x < building.maxX + (-y); x++) {
						for (int z = y; z < building.maxZ + (-y); z++) {
							int finalX = initX + x;
							int finalY = initY + y;
							int finalZ = initZ + z;
							
							// TODO L'escalier est moche
							
							if (
								world.getBlockId(finalX, finalY, finalZ) != Block.grass.blockID &&
								world.getBlockId(finalX, finalY, finalZ) != Block.stone.blockID
							) {
								world.setBlock(finalX, finalY, finalZ, Block.grass.blockID, 0, 2);
							}
						}
					}
					
				}
			}
		}

	}
	
	/**
	 * Retourne l'orientation retourner en fonction de la rotation
	 * @param rotate
	 * @param orientation
	 * @return
	 */
	private int rotateOrientation(int rotate, int orientation) {
		if (rotate == Building.ROTATED_90) {
			
			switch (orientation) { 
				case Unity.ORIENTATION_UP:
					return Unity.ORIENTATION_RIGTH;
				case Unity.ORIENTATION_LEFT:
					return Unity.ORIENTATION_DOWN;
				case Unity.ORIENTATION_DOWN:
					return Unity.ORIENTATION_LEFT;
				case Unity.ORIENTATION_RIGTH:
					return Unity.ORIENTATION_UP;
				default:
					return Unity.ORIENTATION_NONE;
			}
		}
		if (rotate == Building.ROTATED_180) {
			return this.rotateOrientation(Building.ROTATED_90, this.rotateOrientation(Building.ROTATED_90, orientation));
		}
		if (rotate == Building.ROTATED_240) {
			return this.rotateOrientation(Building.ROTATED_180, this.rotateOrientation(Building.ROTATED_90, orientation));
		}
		return orientation;
	}


	/**
	 * Insert le contenu du block
	 * @param world
	 * @param random
	 * @param i
	 * @param j
	 * @param k
	 * @param contents
	 */
	private void setContents(World world, Random random, int x, int y, int z, ArrayList<ArrayList<Content>> contents) {
		
		Block block  = Block.blocksList [world.getBlockId (x, y, z)];
		
		if (block instanceof BlockChest) {

			TileEntity te  = world.getBlockTileEntity (x, y, z);
			if (te instanceof TileEntityChest) {
				
				for (int i = 0; i < contents.size(); i++) {
					
					ArrayList<Content> groupItem = contents.get(i);
					
					// Recupère un item aléatoirement
					Content content = groupItem.get(random.nextInt (groupItem.size()));
					// Calcule le nombre aléatoire d'item
					int diff = content.max - content.min;
					int nombre = content.min + ((diff > 0) ? random.nextInt (diff) : 0);
					
					((TileEntityChest) te).setInventorySlotContents (i, new ItemStack(Item.itemsList[content.id], nombre));
				}
			}
			
		}
		
	}
	
	/**
	 * Affecte l'orientation
	 * @param i
	 * @param j
	 * @param k
	 * @param orientation
	 */
	private void setOrientation(World world, int x, int y, int z, int orientation) {
		this.setOrientation(world, x, y, z, orientation, Building.ROTATED_0);
	}

	/**
	 * Affecte l'orientation
	 * @param i
	 * @param j
	 * @param k
	 * @param orientation
	 * @param rotation
	 */
	private void setOrientation(World world, int x, int y, int z, int orientation, int rotation) {
		
		Block block  = Block.blocksList [world.getBlockId (x, y, z)];
		int metadata = world.getBlockMetadata (x, y, z);
		
		orientation = this.rotateOrientation (rotation, orientation);
		
		if (block instanceof BlockTorch) {

			if (orientation == Unity.ORIENTATION_NONE)  { metadata = (metadata & 0x8) + 0; } else 
			if (orientation == Unity.ORIENTATION_UP)    { metadata = (metadata & 0x8) + 4; } else 
			if (orientation == Unity.ORIENTATION_DOWN)  { metadata = (metadata & 0x8) + 3; } else 
			if (orientation == Unity.ORIENTATION_LEFT)  { metadata = (metadata & 0x8) + 2; } else 
			if (orientation == Unity.ORIENTATION_RIGTH) { metadata = (metadata & 0x8) + 1; } else 
			{
				ModCastleDefenders.log.severe("Bad orientation : "+x+","+y+","+z);
			}
			
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
			return;
		}
		
		if (block instanceof BlockDirectional) {

			if (orientation == Unity.ORIENTATION_NONE)  { metadata = (metadata & 0x8) + 0; } else 
			if (orientation == Unity.ORIENTATION_UP)    { metadata = (metadata & 0x8) + 0; } else 
			if (orientation == Unity.ORIENTATION_DOWN)  { metadata = (metadata & 0x8) + 2; } else 
			if (orientation == Unity.ORIENTATION_LEFT)  { metadata = (metadata & 0x8) + 3; } else 
			if (orientation == Unity.ORIENTATION_RIGTH) { metadata = (metadata & 0x8) + 1; } else 
			{
				ModCastleDefenders.log.severe("Bad orientation : "+x+","+y+","+z);
			}
			
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
			return;
		}
		
		if (
			block instanceof BlockLadder ||
			block instanceof BlockWall ||
			block instanceof BlockFurnace ||
			block instanceof BlockChest
		) {
			
			if (orientation == Unity.ORIENTATION_NONE)  { metadata = (metadata & 0x8) + 2; } else 
			if (orientation == Unity.ORIENTATION_UP)    { metadata = (metadata & 0x8) + 2; } else 
			if (orientation == Unity.ORIENTATION_DOWN)  { metadata = (metadata & 0x8) + 3; } else 
			if (orientation == Unity.ORIENTATION_LEFT)  { metadata = (metadata & 0x8) + 4; } else 
			if (orientation == Unity.ORIENTATION_RIGTH) { metadata = (metadata & 0x8) + 5; } else 
			{
				ModCastleDefenders.log.severe("Bad orientation : "+x+","+y+","+z);
			}
			
			world.setBlockMetadataWithNotify(x, y, z, metadata, 2);
			return;
		}
		
	}

}
