package mods.castledefenders.common.worldgenerator;

import java.util.ArrayList;
import java.util.Random;

import mods.castledefenders.common.ModCastleDefenders;
import mods.castledefenders.common.building.Building;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
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
				this.buildingsSurface.add(buildingAndInfos);
				break;
				
			case WorldGeneratorByBuilding.DIMENSION_ID_SURFACE:
				this.buildingsNether.add(buildingAndInfos);
				break;
			default:
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		// Generation diffenrente entre le nether et la surface
		switch (world.provider.dimensionId) {
			case WorldGeneratorByBuilding.DIMENSION_ID_NETHER:
				this.generateBuilding(world, random, chunkX * 16, chunkZ * 16, buildingsNether);
				break;
				
			case WorldGeneratorByBuilding.DIMENSION_ID_SURFACE:
				this.generateBuilding(world, random, chunkX * 16, chunkZ * 16, buildingsSurface);
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
	private Building getBuildingInRate(ArrayList<BuildingAndInfos> buildings, int totalRate, Random random) {
		
		ArrayList<Building>buildingsForRate = new ArrayList<Building>();
		
		for (BuildingAndInfos buildingAndInfos : buildings) {
			for (int i = 0; i < buildingAndInfos.spawnRate; i++) {
				buildingsForRate.add(buildingAndInfos.building);
			}
		}
		
		return buildingsForRate.get(random.nextInt(totalRate));
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
	private void generateBuilding(World world, Random random, int wolrdX, int wolrdZ, ArrayList<BuildingAndInfos> buildings) {
		
		// test du Spawn global
		if (random.nextInt(10) < this.globalSpawnRate) {
			

			// Position initial de la génération en hauteur
			int worldY = 64;
			Building building = this.getBuildingInRate (buildings, this.totalRateSpawnByBuildingList(buildings), random);
			
			
			
		}
	}
	
}
