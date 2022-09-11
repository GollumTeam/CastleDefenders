package com.gollum.castledefenders.common.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.gollum.core.common.config.Config;
import com.gollum.core.common.config.ConfigProp;
import com.gollum.core.common.config.ConfigProp.Type;
import com.gollum.core.common.config.type.ItemStackConfigType;
import com.gollum.core.common.config.type.MobCapacitiesConfigType;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;

public class ConfigCastleDefender extends Config {
	
	// Config d'affichage
	@ConfigProp(group = "Display") public boolean displayMercenaryMessage = true;
	@ConfigProp(group = "Display") public boolean displayMercenaryLife = true;
	@ConfigProp(group = "Display") public double mercenaryLifeTop = 80.D;
	@ConfigProp(group = "Display") public double mercenaryLifeHeight = 3.D;
	@ConfigProp(group = "Display") public double mercenaryLifeWidth = 40.D;
	
	@ConfigProp(group = "Spawn count") public int maxSpawnKnight     = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnKnight2    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnEKnight    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnArcher     = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnArcher2    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnEArcher    = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnMage       = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnEMage      = 6;
	@ConfigProp(group = "Spawn count") public int maxSpawnMerc       = 3;
	@ConfigProp(group = "Spawn count") public int maxSpawnMercArcher = 3;
	@ConfigProp(group = "Spawn count") public int maxSpawnHealer     = 3;
	
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType knightCapacities     = new MobCapacitiesConfigType(0.55D, 20.D, 4.D , 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType knight2Capacities    = new MobCapacitiesConfigType(0.6D , 30.D, 8.D , 25.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eKnightCapacities    = new MobCapacitiesConfigType(0.55D, 25.D, 6.D , 16.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType archerCapacities     = new MobCapacitiesConfigType(0.1D , 15.D, 2.D , 25.D, 30.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType archer2Capacities    = new MobCapacitiesConfigType(0.1D , 25.D, 4.D , 30.D, 15.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eArcherCapacities    = new MobCapacitiesConfigType(0.1D , 20.D, 3.D , 18.D, 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mageCapacities       = new MobCapacitiesConfigType(0.1D , 25.D, 5.D , 15.D, 30.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType eMageCapacities      = new MobCapacitiesConfigType(0.1D , 30.D, 8.D , 15.D, 30.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mercCapacities       = new MobCapacitiesConfigType(0.6D , 20.D, 7.D , 20.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType mercArcherCapacities = new MobCapacitiesConfigType(0.6D , 20.D, 3.D , 25.D, 17.D);
	@ConfigProp(group = "Entity capacities") public MobCapacitiesConfigType healerCapacities     = new MobCapacitiesConfigType(0.6D , 15.D, 0.3D, 5.D , 100.D);
	

	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String knightBiomes    [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String knight2Biomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String eKnightBiomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String archerBiomes    [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String archer2Biomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String eArcherBiomes   [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String mageBiomes      [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String eMageBiomes     [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String mercBiomes      [];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String mercArcherBiomes[];
	@ConfigProp(group="Entity spawn biome", mcRestart=true, type=Type.BIOME, newValue="minecraft:desert") public String healerBiomes    [];
	
	// Config des mercenaire
	@ConfigProp(group = "Mercenary", info="[ItemID,metadata,number],...") 
	public ItemStackConfigType[] mercenaryCost  = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public ItemStackConfigType[] mercArcherCost = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public ItemStackConfigType[] healerCost     = {new ItemStackConfigType("minecraft:gold_ingot"), new ItemStackConfigType("minecraft:iron_ingot", 10)};
	@ConfigProp(group = "Mercenary")
	public float healPointByTimeRange = 1.5F;
	

	public ConfigCastleDefender() {
		List<Biome> biomes =  Arrays.asList(
			Biomes.DESERT,
			Biomes.DESERT_HILLS,
			Biomes.EXTREME_HILLS,
			Biomes.EXTREME_HILLS_EDGE,
			Biomes.FOREST,
			Biomes.FOREST_HILLS,
			Biomes.FROZEN_OCEAN,
			Biomes.FROZEN_RIVER,
			Biomes.HELL,
			Biomes.ICE_MOUNTAINS,
			Biomes.ICE_PLAINS,
			Biomes.JUNGLE,
			Biomes.JUNGLE_HILLS,
			Biomes.MUSHROOM_ISLAND,
			Biomes.MUSHROOM_ISLAND_SHORE,
			Biomes.OCEAN,
			Biomes.PLAINS,
			Biomes.RIVER,
			Biomes.SKY,
			Biomes.BIRCH_FOREST,
			Biomes.BIRCH_FOREST_HILLS,
			Biomes.SWAMPLAND,
			Biomes.TAIGA,
			Biomes.TAIGA_HILLS,
			Biomes.COLD_BEACH,
			Biomes.COLD_TAIGA,
			Biomes.SAVANNA,
			Biomes.SAVANNA_PLATEAU
		);
		
		List<String> biomesStr = biomes
			.stream()
			.map(b -> b.getRegistryName().toString())
			.collect(Collectors.toList())
		;
		
		this.knightBiomes     = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.knight2Biomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.eKnightBiomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.archerBiomes     = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.archer2Biomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.eArcherBiomes    = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.mageBiomes       = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.eMageBiomes      = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.mercBiomes       = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.mercArcherBiomes = (String[]) biomesStr.toArray(new String[biomes.size()]);
		this.healerBiomes     = (String[]) biomesStr.toArray(new String[biomes.size()]);
	}
}
